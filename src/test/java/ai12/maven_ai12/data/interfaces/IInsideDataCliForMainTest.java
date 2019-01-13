package ai12.maven_ai12.data.interfaces;

import ai12.maven_ai12.beans.PlayerProfile;
import ai12.maven_ai12.data.DataClientEngine;
import ai12.maven_ai12.data.interfaces.IInsideDataCliForMain.InvalidLoginException;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IInsideDataCliForMainTest extends TestCase {

	private IInsideDataCliForMain mInterface;

	public IInsideDataCliForMainTest(String testName) {
		super(testName);
		this.mInterface = new InsideDataCliForMainImpl(new DataClientEngine());
	}

	public void testRegisterWithNullPlayer() {
		try {
			mInterface.register(null, "passwordOk");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (true);
		} catch (IInsideDataCliForMain.InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithNullPassword() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			mInterface.register(vPlayer, null);
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (true);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithWrongPassword() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "password !*");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (true);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithEmptyPassword() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (true);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithWrongLogin() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("Login");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "correctPassword");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (true);
		}
	}

	public void testRegisterWithEmptyLogin() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "correctPassword");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (true);
		}
	}

	public void testRegisterWithWrongFirstName() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			vPlayer.setFirstname("firstname;");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "correctPassword");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (true);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithWrongLastName() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			vPlayer.setLastname("lastname;");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "correctPassword");
			assert (false);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (true);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testRegisterWithNoError() {
		try {
			PlayerProfile vPlayer = new PlayerProfile();
			vPlayer.setLogin("mygreatlogin");
			vPlayer.setLastname("Lastname");
			vPlayer.setFirstname("Firstname");
			IInsideDataCliForMain vInterfaceDataCliForMain = new InsideDataCliForMainImpl(new DataClientEngine());
			vInterfaceDataCliForMain.register(vPlayer, "correctPassword");
			assert (true);
		} catch (IInsideDataCliForMain.InvalidFieldException e) {
			assert (false);
		} catch (IInsideDataCliForMain.EmptyFieldException e) {
			assert (false);
		} catch (InvalidLoginException e) {
			assert (false);
		}
	}

	public void testSave() {
		TestSuite suite = new TestSuite("IInsideDataCliForMainTest");
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithNullPlayer"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithNullPassword"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithWrongPassword"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithWrongLogin"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithEmptyLogin"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithWrongFirstName"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithWrongLastName"));
		suite.addTest(new IInsideDataCliForMainTest("testRegisterWithNoError"));
	}
}
