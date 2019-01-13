package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.UUID;

public class SoftPlayer extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -512572260823621742L;

	private UUID idPlayer;
	private String login;
	private Picture avatar;

	public SoftPlayer() {
		this.setIdPlayer(UUID.randomUUID());
		this.setLogin("Undefined login");
		this.setAvatar(new Picture());
	}

	public SoftPlayer(SavedPlayer player) {
		setIdPlayer(player.getIdPlayer());
		setLogin(player.getLogin());
		setAvatar(player.getAvatar());
	}

	public UUID getIdPlayer() {
		return this.idPlayer;
	}

	public void setIdPlayer(UUID idPlayer) {
		this.idPlayer = idPlayer;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Picture getAvatar() {
		return this.avatar;
	}

	public void setAvatar(Picture avatar) {
		this.avatar = avatar;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		SoftPlayer softPlayer = (SoftPlayer) obj;
		return idPlayer.compareTo(softPlayer.idPlayer) == 0;
	}

	@Override
	public String toString() {
		return "SoftPlayer [idPlayer=" + idPlayer + ", login=" + login + ", avatar=" + avatar + "]";
	}

}
