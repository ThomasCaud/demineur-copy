package ai12.maven_ai12.data.managers;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.SavedPlayer;
import ai12.maven_ai12.data.exceptions.BadCredentialsException;
import ai12.maven_ai12.data.exceptions.LoginNotFoundException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SavedPlayerManagerTest {
    public static final String login = "loginForTesting";
    public static final String decryptedPassword = "passwordForTesting";

    @Test
    public void testGetInstance() {
        SavedPlayerManager spm1 = SavedPlayerManager.getInstance();
        assert (spm1 != null);
        SavedPlayerManager spm2 = SavedPlayerManager.getInstance();
        assert (spm1 == spm2);
    }

    private SavedPlayer getSavedPlayer() {
        SavedPlayer sp = new SavedPlayer();
        sp.setLogin(login);
        ArrayList<Game> savedGames = new ArrayList<Game>();
        savedGames.add(new Game());
        sp.setSavedGames(savedGames);

        return sp;
    }

    @Test
    public void testIsSerializable() {
        boolean success = false;
        try {
            new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(getSavedPlayer());
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert (success);
    }

    @Test
    public void testSaveWithoutErrorCaught() {
        boolean success = false;
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        try {
            manager.save(getSavedPlayer(), decryptedPassword);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        manager.deleteFiles(login, decryptedPassword);
        assert (success);
    }

    @Test
    public void testFoldersAndFilesAreCreated() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        SavedPlayer sp = getSavedPlayer();
        manager.save(sp, decryptedPassword);

        File savesFolder = new File("Saves");
        assert (savesFolder.exists() && savesFolder.isDirectory());

        File dataFolder = new File(Config.PATH_DATA);
        assert (dataFolder.exists() && dataFolder.isDirectory());

        File loginFolder = new File(Config.PATH_LOGIN);
        assert (loginFolder.exists() && loginFolder.isDirectory());

        File specificLoginFolder = new File(Config.PATH_LOGIN + sp.getLogin());
        assert (specificLoginFolder.exists() && specificLoginFolder.isDirectory());

        File loginFile = new File("Saves/Login/" + sp.getLogin() + "/file1.txt");
        assert (loginFile.exists() && loginFile.isFile());

        // check if login file have been created
        int numberOfExistingFileBeforeCreation = specificLoginFolder.listFiles().length;
        manager.save(sp, decryptedPassword);
        int numberOfExistingFileAfterCreation = specificLoginFolder.listFiles().length;
        assert ((numberOfExistingFileBeforeCreation + 1) == numberOfExistingFileAfterCreation);

        Path path = Paths.get("Saves/Login/" + sp.getLogin() + "/file" + numberOfExistingFileAfterCreation + ".txt");
        List<Object> list = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path)) {
            list = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check number of line in the created file
        assert (list.size() == 2);

        // Check data file have been created
        File dataFile = new File(Config.PATH_DATA + list.get(1) + ".ser");
        assert (dataFile.exists() && dataFile.isFile());
        manager.deleteFiles(login, decryptedPassword);
    }

    @Test
    public void testCreateEncryptedPassword() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();

        final String encryptedPassword = manager.createEncryptedPassword("myPassword");
        assert (encryptedPassword.length() > 5);
    }

    @Test
    public void testEncryptAndDecrypt() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        String vPlainText = "pPlainText";

        try {
            String vEncryptedText = manager.encrypt(vPlainText);
            String decrypted = manager.decrypt(vEncryptedText);
            assert (decrypted.equals(vPlainText));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadWithoutError() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        SavedPlayer player = new SavedPlayer();
        player.setLogin("loginTestLoadWithoutError");
        player.setFirstname("firstNameTest");
        player.setLastname("lastNameTest");
        manager.save(player, "passwordTest");

        boolean success = false;
        SavedPlayer playerLoaded = new SavedPlayer();
        try {
            playerLoaded = manager.load("loginTestLoadWithoutError", "passwordTest");
            assert (playerLoaded.getFirstname().equals("firstNameTest"));
            assert (playerLoaded.getLastname().equals("lastNameTest"));
            assert (playerLoaded.getLogin().equals("loginTestLoadWithoutError"));
            assert (playerLoaded.getIdPlayer().equals(player.getIdPlayer()));
            success = true;
        } catch (InternalError | LoginNotFoundException | BadCredentialsException e) {
            e.printStackTrace();
        }

        manager.deleteFiles("loginTestLoadWithoutError", "passwordTest");
        assert (success);
    }

    @Test
    public void testLoadWithBadPassword() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        SavedPlayer player = new SavedPlayer();
        player.setLogin("loginTestLoadWithBadPassword");
        player.setFirstname("firstNameTest");
        player.setLastname("lastNameTest");
        manager.save(player, "passwordTest");

        boolean success = false;
        try {
            manager.load("loginTestLoadWithBadPassword", "wrongPasswordTest");
        } catch (BadCredentialsException e) {
            success = true;
        } catch (InternalError | LoginNotFoundException e) {
            e.printStackTrace();
        }
        manager.deleteFiles("loginTestLoadWithBadPassword", "passwordTest");
        assert (success);
    }

    @Test
    public void testLoadWithBadLogin() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        SavedPlayer player = new SavedPlayer();
        player.setLogin("loginTestLoadWithBadLogin");
        player.setFirstname("firstNameTest");
        player.setLastname("lastNameTest");
        manager.save(player, "passwordTest");

        boolean success = false;
        try {
            manager.load("wrongLoginTest", "passwordTest");
        } catch (LoginNotFoundException e) {
            success = true;
        } catch (InternalError | BadCredentialsException e) {
            e.printStackTrace();
        }
        manager.deleteFiles("loginTestLoadWithBadLogin", "passwordTest");
        assert (success);
    }

    @Test
    public void testLoadCorruptedProfile() {
        SavedPlayerManager manager = SavedPlayerManager.getInstance();
        SavedPlayer player = new SavedPlayer();
        player.setLogin("loginTestLoadCorruptedProfile");
        player.setFirstname("firstNameTest");
        player.setLastname("lastNameTest");

        File vSaveDirectory = new File(Config.PATH_LOGIN);
        if (!vSaveDirectory.exists()) {
            vSaveDirectory.mkdir();
        }

        File vSpecificLoginDirectory = new File(Config.PATH_LOGIN + "loginTestLoadCorruptedProfile/");
        if (!vSpecificLoginDirectory.exists()) {
            vSpecificLoginDirectory.mkdir();
        }

        boolean success = false;
        try {
            manager.load("loginTestLoadCorruptedProfile", "passwordTest");
        } catch (InternalError e) {
            success = true;
        } catch (LoginNotFoundException | BadCredentialsException e) {
            e.printStackTrace();
        }
        manager.deleteFiles("loginTestLoadCorruptedProfile", "passwordTest");
        assert (success);
    }

    @Test
    @Ignore
    public void testFetchLocalLoginsWhenNoLogin() {
        SavedPlayerManager spm = SavedPlayerManager.getInstance();
        ArrayList<String> localLogins = spm.getLocalLogins();
        assert (localLogins.size() == 0);
    }

    @Test
    public void testFetchLocalLoginsWithOneLogin() {
        SavedPlayerManager spm = SavedPlayerManager.getInstance();

        try {
            spm.deleteFiles("testFetchLocalLoginsWithOneLogin", "password");
        } catch (InternalError e) {
            // do nothing ; the login just didn't exist
        }
        int localLoginSizeBefore = spm.getLocalLogins().size();
        SavedPlayer sp = new SavedPlayer();
        sp.setLogin("testFetchLocalLoginsWithOneLogin");
        spm.save(sp, "password");

        ArrayList<String> localLogins = spm.getLocalLogins();
        assert (localLoginSizeBefore + 1 == localLogins.size());
        assert (localLogins.contains("testFetchLocalLoginsWithOneLogin"));

        spm.deleteFiles("testFetchLocalLoginsWithOneLogin", "password");
    }

    @Test
    public void testExclusionOfDsStoreFile() {
        SavedPlayerManager spm = SavedPlayerManager.getInstance();
        ArrayList<String> localLoginsBefore = spm.getLocalLogins();
        SavedPlayer sp = new SavedPlayer();
        sp.setLogin(".DS_Store");
        spm.save(sp, "testExclusionOfDsStoreFile");
        ArrayList<String> localLoginsAfter = spm.getLocalLogins();
        assert (localLoginsAfter.size() == localLoginsBefore.size());
        spm.deleteFiles(".DS_Store", "testExclusionOfDsStoreFile");
    }
}
