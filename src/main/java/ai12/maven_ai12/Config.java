package ai12.maven_ai12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai12.maven_ai12.data.exceptions.BadIPAddressException;

public class Config {
	public static final String PATH_DATA = "Saves/Data/";
	public static final String PATH_DATA_GAMES = "Saves/Data/Games/";
	public static final String PATH_LOGIN = "Saves/Login/";
	public static final String SECRET_KEY_ENCRYPTION = "gC2jVsIYyBpoMGaxXmcsTg==";
	public static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final int LISTENING_PORT_CLIENT = 60200;
	

	public static void validateIPAddress(final String pIp) throws BadIPAddressException {
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(pIp);
		if(!matcher.matches()){
			throw new BadIPAddressException();
		};
	}

}
