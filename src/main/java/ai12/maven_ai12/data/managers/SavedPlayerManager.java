package ai12.maven_ai12.data.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ai12.maven_ai12.Config;
import ai12.maven_ai12.beans.SavedPlayer;
import ai12.maven_ai12.data.exceptions.BadCredentialsException;
import ai12.maven_ai12.data.exceptions.LoginNotFoundException;

public class SavedPlayerManager {
	private static SavedPlayerManager INSTANCE = null;
	private static Cipher mCipher;
	private static Logger logger = Logger.getLogger(SavedPlayerManager.class);

	public static SavedPlayerManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SavedPlayerManager();
		}
		return INSTANCE;
	}

	private SavedPlayerManager() {
		try {
			mCipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new InternalError(e.getMessage());
		}
	}

	public void save(SavedPlayer pSp, String pPassword) {
		logger.info("Player save launched");
		String vSpecificLoginDirectoryName = Config.PATH_LOGIN + pSp.getLogin() + "/";
		createSavesFolders(Config.PATH_DATA, Config.PATH_LOGIN, vSpecificLoginDirectoryName);
		logger.info("Saving folder created");
		createSerializedFile(Config.PATH_DATA + pSp.getIdPlayer(), pSp);
		createPlayerFile(vSpecificLoginDirectoryName, pPassword, pSp.getIdPlayer().toString());
		logger.info("Player saved");
	}

	public String createEncryptedPassword(String pPassword) {
		String vEncryptedText = "";

		try {
			vEncryptedText = encrypt(pPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vEncryptedText;
	}

	public String encrypt(String pPlainText) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(Config.SECRET_KEY_ENCRYPTION);
		SecretKey vSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

		byte[] vPlainTextByte = pPlainText.getBytes();
		mCipher.init(Cipher.ENCRYPT_MODE, vSecretKey);
		byte[] vEncryptedByte = mCipher.doFinal(vPlainTextByte);
		Base64.Encoder vEncoder = Base64.getEncoder();
		String vEncryptedText = vEncoder.encodeToString(vEncryptedByte);
		return vEncryptedText;
	}

	public String decrypt(String pEncryptedText) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(Config.SECRET_KEY_ENCRYPTION);
		SecretKey vSecretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

		Base64.Decoder pDecoder = Base64.getDecoder();
		byte[] vEncryptedTextByte = pDecoder.decode(pEncryptedText);
		mCipher.init(Cipher.DECRYPT_MODE, vSecretKey);

		byte[] vDecryptedByte;
		vDecryptedByte = mCipher.doFinal(vEncryptedTextByte);
		String vDecryptedText = new String(vDecryptedByte);
		return vDecryptedText;
	}

	private void createSavesFolders(String pDataDirectoryName, String pLoginDirectoryName,
			String pSpecificLoginDirectoryName) {
		try {
			// Create Saves, Data directories if needed
			File vDirectory = new File(pDataDirectoryName);
			if (!vDirectory.exists()) {
				vDirectory.mkdirs();

				// Create Login directory if needed
				File vLoginDirectory = new File(pLoginDirectoryName);
				if (!vLoginDirectory.exists()) {
					vLoginDirectory.mkdir();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Create specific directory in Login if needed
			File vSpecificLoginDirectory = new File(pSpecificLoginDirectoryName);
			if (!vSpecificLoginDirectory.exists()) {
				vSpecificLoginDirectory.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createSerializedFile(String pFilePath, SavedPlayer pSp) {
		File vSerFilePath = new File(pFilePath + ".ser");
		try {
			// Create serialized file
			ObjectOutputStream vOos = new ObjectOutputStream(new FileOutputStream(vSerFilePath));
			// Insert serialized Saved Player object in .ser file
			vOos.writeObject(pSp);
			vOos.flush();
			vOos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private SavedPlayer createPlayerFromSerializedFile(String pFilePath) throws InternalError {
		try {
			ObjectInputStream vOis;
			vOis = new ObjectInputStream(new FileInputStream(pFilePath));
			SavedPlayer player = (SavedPlayer) vOis.readObject();
			vOis.close();
			return player;
		} catch (IOException | ClassNotFoundException e) {
			throw new InternalError(e.getMessage());
		}
	}

	private void createPlayerFile(String pSpecificLoginDirectoryName, String pPassword, String pUuid) {
		// Define player profile file name by counting existing files
		File vSpecificLoginFolder = new File(pSpecificLoginDirectoryName);

		if (vSpecificLoginFolder != null) {
			int vNumberOfExistingFiles = vSpecificLoginFolder.listFiles().length;
			String vFileName = "file" + (vNumberOfExistingFiles + 1) + ".txt";
			File vFile = new File(pSpecificLoginDirectoryName + vFileName);
			try {
				FileWriter vFw = new FileWriter(vFile.getAbsoluteFile());
				BufferedWriter vBw = new BufferedWriter(vFw);

				// Insert encrypted password and serialized file name in .txt
				// file
				vBw.write(createEncryptedPassword(pPassword) + "\n" + pUuid);
				vBw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public SavedPlayer load(String pLogin, String pPassword)
			throws InternalError, LoginNotFoundException, BadCredentialsException {

		String vSpecificLoginDirectoryName = Config.PATH_LOGIN + pLogin + "/";
		File vSpecificLoginDirectory = new File(vSpecificLoginDirectoryName);

		if (vSpecificLoginDirectory.exists() && vSpecificLoginDirectory.isDirectory()) {
			File[] vFilesInDirectory = vSpecificLoginDirectory.listFiles();

			if (vFilesInDirectory.length != 0) {

				int vIndexFileToLoad = -1;
				int i = 0;
				for (File vProfile : vFilesInDirectory) {

					ArrayList<String> vFileContent;
					try {
						vFileContent = readFileContent(vProfile);
					} catch (IOException e) {
						throw new InternalError(e.getMessage());
					}
					if (vFileContent.size() == 2) {

						String vEncryptedPassword = vFileContent.get(0);
						String vDecryptedPassword = "";
						try {
							vDecryptedPassword = decrypt(vEncryptedPassword);
						} catch (Exception e) {
							throw new InternalError(e.getMessage());
						}

						if (vDecryptedPassword.equals(pPassword)) {
							vIndexFileToLoad = i;
						}
					} else {
						throw new InternalError("Le fichier de profil est au mauvais format.");
					}

					i++;
				}

				if (vIndexFileToLoad != -1) {
					File vFileToLoad = vFilesInDirectory[vIndexFileToLoad];
					ArrayList<String> vFileToLoadContent;
					try {
						vFileToLoadContent = readFileContent(vFileToLoad);
					} catch (IOException e) {
						throw new InternalError(e.getMessage());
					}
					String vDataFileName = vFileToLoadContent.get(1);
					vDataFileName = Config.PATH_DATA + vDataFileName + ".ser";

					File vDataFile = new File(vDataFileName);
					if (vDataFile.exists() && vDataFile.isFile()) {
						try {
							return createPlayerFromSerializedFile(vDataFileName);
						} catch (InternalError e) {
							throw e;
						}
					} else {
						throw new InternalError("Erreur lors de la récupération du fichier de données de ce profil");
					}
				} else {
					throw new BadCredentialsException();
				}
			} else {
				throw new InternalError("Aucun fichier de profil n'est présent pour cet utilisateur.");
			}
		} else {
			throw new LoginNotFoundException();
		}
	}

	public ArrayList<String> readFileContent(File pFile) throws IOException {
		BufferedReader vBr;
		ArrayList<String> vFileContent = new ArrayList<String>();
		String vSt;
		try {
			vBr = new BufferedReader(new FileReader(pFile));
			while ((vSt = vBr.readLine()) != null)
				vFileContent.add(vSt);
			vBr.close();
			return vFileContent;
		} catch (IOException e) {
			throw e;
		}
	}

	protected void deleteFiles(String pLogin, String pPassword) {
		String vSpecificLoginDirectoryName = Config.PATH_LOGIN + pLogin + "/";
		File vSpecificLoginDirectory = new File(vSpecificLoginDirectoryName);

		if (vSpecificLoginDirectory.exists() && vSpecificLoginDirectory.isDirectory()) {
			// The folder named by pLogin exists
			File[] vFilesInDirectory = vSpecificLoginDirectory.listFiles();

			ArrayList<String> vDataFilenames = new ArrayList<String>();
			for (File vProfile : vFilesInDirectory) {
				// Foreach matching files
				ArrayList<String> vFileContent;
				try {
					vFileContent = readFileContent(vProfile);
				} catch (IOException e) {
					throw new InternalError(e.getMessage());
				}

				String vEncryptedPassword = vFileContent.get(0);
				String vDecryptedPassword = "";
				try {
					vDecryptedPassword = decrypt(vEncryptedPassword);
				} catch (Exception e) {
					throw new InternalError(e.getMessage());
				}

				if (vDecryptedPassword.equals(pPassword)) {
					// We get the data filename
					vDataFilenames.add(Config.PATH_DATA + vFileContent.get(1) + ".ser");
					// and remove the Login specific file
					vProfile.delete();
				}
			}

			if (vSpecificLoginDirectory.length() == 0) {
				// Delete the folder if he is now empty
				vSpecificLoginDirectory.delete();
			}

			for (String vDataFilename : vDataFilenames) {
				File vDataFile = new File(vDataFilename);
				if (vDataFile.exists() && vDataFile.isFile()) {
					try {
						vDataFile.delete();
					} catch (InternalError e) {
						throw e;
					}
				}
			}
		} else {
			throw new InternalError("Impossible de supprimer le profil associé");
		}
	}

	public ArrayList<String> getLocalLogins() {
		ArrayList<String> result = new ArrayList<String>();
		File vLoginDirectory = new File(Config.PATH_LOGIN);

		if (vLoginDirectory.exists() && vLoginDirectory.isDirectory()) {
			File[] vLoginsFiles = vLoginDirectory.listFiles();

			for (File vLoginFile : vLoginsFiles)
				if (!vLoginFile.getName().equals(".DS_Store"))
					result.add(vLoginFile.getName());
		}

		return result;
	}
}