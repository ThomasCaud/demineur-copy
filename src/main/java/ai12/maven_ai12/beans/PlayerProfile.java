package ai12.maven_ai12.beans;

import java.util.Date;

public class PlayerProfile extends SoftPlayer {

	private static final long serialVersionUID = 1L;

	public String firstname;
	public String lastname;
	public Date dateOfBirth;
	public int nbWin;
	public int nbForfeit;
	public int nbPlay;

	public PlayerProfile() {
		super();
		this.setFirstname("null");
		this.setLastname("null");
		this.setDateOfBirth(new Date());
		this.setNbForfeit(0);
		this.setNbPlay(0);
		this.setNbWin(0);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getNbWin() {
		return nbWin;
	}

	public void setNbWin(int nbWin) {
		this.nbWin = nbWin;
	}

	public int getNbForfeit() {
		return nbForfeit;
	}

	public void setNbForfeit(int nbForfeit) {
		this.nbForfeit = nbForfeit;
	}

	public int getNbPlay() {
		return nbPlay;
	}

	public void setNbPlay(int nbPlay) {
		this.nbPlay = nbPlay;
	}
}
