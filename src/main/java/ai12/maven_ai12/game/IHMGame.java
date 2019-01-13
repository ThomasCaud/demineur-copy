package ai12.maven_ai12.game;
	
import java.util.ArrayList;

import ai12.maven_ai12.beans.Game;
import ai12.maven_ai12.beans.SoftPlayer;
import javafx.application.Application;
import javafx.stage.Stage;


public class IHMGame extends Application {
	
	private static GameClientEngine mManager;
	
	@Override
	public void start(Stage primaryStage) {
		mManager = new GameClientEngine();
		
		
		// vGameController.setIInsideDataCliForGame(IInsideDataCliForGame vIInsideDataCliForGame);
		
		Game vGame = new Game();
		ArrayList<SoftPlayer> mplayers = new ArrayList<SoftPlayer>();
		SoftPlayer p = new SoftPlayer();
		p.setLogin("toto");
		mplayers.add(p);
		p = new SoftPlayer();
		p.setLogin("titi");
		mplayers.add(p);
		p = new SoftPlayer();
		p.setLogin("tata");
		mplayers.add(p);
		p = new SoftPlayer();
		p.setLogin("tete");
		mplayers.add(p);
		p = new SoftPlayer();
		p.setLogin("tutu");
		mplayers.add(p);
		p = new SoftPlayer();
		p.setLogin("tyty");
		mplayers.add(p);
		vGame.setPlayers(mplayers);
		ArrayList<SoftPlayer> mviewers = new ArrayList<SoftPlayer>();
		p = new SoftPlayer();
		p.setLogin("tutu");
		mviewers.add(p);
		p = new SoftPlayer();
		p.setLogin("tyty");
		mviewers.add(p);
		vGame.setViewers(mviewers);
		mManager.startAGame(vGame, primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}


