package ai12.maven_ai12.game;

import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import ai12.maven_ai12.beans.SoftPlayer;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flag {
	private int mTileSize;
	private ImageView mFlagImageView;
	private Label mFlagNumberLabel;
	private ArrayList<SoftPlayer> mSoftPlayers;

	Flag(int pTileSize) {
		this.mTileSize = pTileSize;
		this.setFlagImageView(this.mTileSize, this.mTileSize / 3);
		
		this.mFlagNumberLabel = new Label();
		this.mFlagNumberLabel.getStyleClass().add("FlagLabel");
		
		this.mSoftPlayers = new ArrayList<SoftPlayer>();
	}
	
	public void setFlagImageView(int pTileSize, int pSubtractedSize) {
		URL vPath = getClass().getResource("views" + FileSystems.getDefault().getSeparator() + "frenchFlag.png");
		Image vImage = new Image(vPath.toString(), pTileSize - pSubtractedSize, pTileSize - pSubtractedSize, false, false);
		this.mFlagImageView = new ImageView(vImage);
		this.mFlagImageView.getStyleClass().add("Flag");
	}
	
	public void setFlagNumberLabel(String pText) {
		this.mFlagNumberLabel.setText(pText);
	}
	
	public Node[] getFlags() {
		this.setFlagTooltip();
		
		if(this.mSoftPlayers.size() == 1) { 
			return new Node[] { this.mFlagImageView, this.mFlagNumberLabel };
		} else if(this.mSoftPlayers.size() > 1) {
			this.setFlagNumberLabel( Integer.toString(this.mSoftPlayers.size()) );
			
			this.translateNode(this.mFlagImageView, -5, -this.mTileSize / 6);
			this.translateNode(this.mFlagNumberLabel, 5, this.mTileSize / 4);
			
			return new Node[] { this.mFlagImageView, this.mFlagNumberLabel };
		}
		
		return new Node[] { };
	}
	
	public void setSoftPlayers(ArrayList<SoftPlayer> pSoftPlayers) {
		this.mSoftPlayers = pSoftPlayers;
	}
	
	public void toggleSoftPlayer(SoftPlayer pSoftPlayer) {
		if(this.mSoftPlayers.contains(pSoftPlayer)) { 
			this.removeSoftPlayer(pSoftPlayer);
		} else {
			this.addSoftPlayer(pSoftPlayer);
		}
	}
	
	public void addSoftPlayer(SoftPlayer pSoftPlayer) {
		this.mSoftPlayers.add(pSoftPlayer);
	}
	
	public void removeSoftPlayer(SoftPlayer pSoftPlayer) {
		this.mSoftPlayers.remove( this.mSoftPlayers.indexOf(pSoftPlayer) );
	}
	
	public String[] getSoftPlayersLogins() {
		String[] vSoftPlayersLogins = new String[this.mSoftPlayers.size()];
		
		int i = 0;
		for (SoftPlayer vSoftPlayer: this.mSoftPlayers) {
			vSoftPlayersLogins[i++] = vSoftPlayer.getLogin();
		}
		
		return vSoftPlayersLogins;
	}
	
	public void setFlagTooltip() {
		String vTooltipFormatedLabel;
		String[] vSoftPlayersLogins = this.getSoftPlayersLogins();
		
		vTooltipFormatedLabel = vSoftPlayersLogins.length > 0
				? String.join("\n", vSoftPlayersLogins)
				: null;
		
		Tooltip.install(
				this.mFlagImageView,
			    new Tooltip(vTooltipFormatedLabel)
			);
	}
	
	public void translateNode(Node pNode, int pX, int pY) {
		pNode.setTranslateX(pX);
		pNode.setTranslateY(pY);
	}
}
