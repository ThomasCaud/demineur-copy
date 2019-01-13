package ai12.maven_ai12.com.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ai12.maven_ai12.beans.Box;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;

public class MsgSpreadResult extends MessageCommunicationSrv implements Serializable {

	private static final long serialVersionUID = 1L;

	private Shot mShot;
	private List<Box> mBoxes;
	private boolean mGameIsOver;
	private SoftPlayer mNextPlayer;
	private List<SoftPlayer> mWinners;

	public MsgSpreadResult(Shot pShot, List<Box> pBoxes, boolean pGameIsOver, SoftPlayer pNextPlayer,
			List<SoftPlayer> pWinners) {
		mShot = pShot;
		mBoxes = pBoxes;
		mGameIsOver = pGameIsOver;
		mNextPlayer = pNextPlayer;
		mWinners = pWinners;
	}

	@Override
	public void process() {
		mComClientEngine.getInsideDataCliForComCli().sendNewResult(mShot, new ArrayList<>(mBoxes), mGameIsOver,
				mNextPlayer, new ArrayList<>(mWinners));
	}

}
