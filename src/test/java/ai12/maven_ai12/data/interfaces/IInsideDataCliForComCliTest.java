package ai12.maven_ai12.data.interfaces;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.Mockito;

import ai12.maven_ai12.beans.SavedPlayer;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.beans.SoftPlayer;
import ai12.maven_ai12.data.DataClientEngine;
import ai12.maven_ai12.game.GameClientEngine;
import ai12.maven_ai12.game.IInsideGameForDataCli;
import ai12.maven_ai12.game.InsideGameForDataCliImpl;

public class IInsideDataCliForComCliTest {

    @Test(expected = NullPointerException.class)
    public void sendNewResultTestWithNullParameters() {
        DataClientEngine dataClientEngine = mock(DataClientEngine.class);
        when(dataClientEngine.getInsideGameForDataCli()).thenReturn(new
                InsideGameForDataCliImpl(new GameClientEngine()));
        IInsideDataCliForComCli insideDataCliForComCli = new
                InsideDataCliForComCliImpl(dataClientEngine);
        SoftPlayer sp = new SoftPlayer();
		insideDataCliForComCli.sendNewResult(null, null, false, sp, null);
    }

    @Test
    public void sendNewResultTestWithNoErrorsYourTurn() {
        DataClientEngine dataClientEngine = mock(DataClientEngine.class);
        SoftPlayer softPlayer = new SoftPlayer();
        SavedPlayer savedPlayer = new SavedPlayer();
        savedPlayer.setIdPlayer(softPlayer.getIdPlayer());
        when(dataClientEngine.getCurrentPlayer()).thenReturn(savedPlayer);
        IInsideGameForDataCli iInsideGameForDataCli =
                mock(IInsideGameForDataCli.class);
        when(dataClientEngine.getInsideGameForDataCli()).thenReturn(iInsideGameForDataCli);
        IInsideDataCliForComCli insideDataCliForComCli = new
                InsideDataCliForComCliImpl(dataClientEngine);
		insideDataCliForComCli.sendNewResult(new Shot(), new ArrayList<>(), false, softPlayer, new ArrayList<>());
        verify(iInsideGameForDataCli, times(1)).notifyYourTurn();
    }

    @Test
    public void sendNewResultTestWithNoErrorsNOTYourTurn() {
        DataClientEngine dataClientEngine = mock(DataClientEngine.class);
        SoftPlayer softPlayer = new SoftPlayer();
        SavedPlayer savedPlayer = new SavedPlayer();
        when(dataClientEngine.getCurrentPlayer()).thenReturn(savedPlayer);
        IInsideGameForDataCli iInsideGameForDataCli =
                mock(IInsideGameForDataCli.class);
        when(dataClientEngine.getInsideGameForDataCli()).thenReturn(iInsideGameForDataCli);
        IInsideDataCliForComCli insideDataCliForComCli = new
                InsideDataCliForComCliImpl(dataClientEngine);
		insideDataCliForComCli.sendNewResult(new Shot(), new ArrayList<>(), false, softPlayer, new ArrayList<>());
        verify(iInsideGameForDataCli, times(1)).notifyNextTurn(Mockito.any());
    }
}
