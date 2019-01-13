package ai12.maven_ai12.data.interfaces;

import ai12.maven_ai12.beans.Coordinates;
import ai12.maven_ai12.beans.Shot;
import ai12.maven_ai12.com.client.ComClientEngine;
import ai12.maven_ai12.com.interfaces.IInsideComCliForDataCli;
import ai12.maven_ai12.com.interfaces.InsideComCliForDataCliImpl;
import ai12.maven_ai12.data.DataClientEngine;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


public class IInsideDataCliForGameTest {

    @Test
    public void testSendMessageWithoutError() {
        DataClientEngine dataClientEngine = mock(DataClientEngine.class);
        when(dataClientEngine.getInsideComCliForDataCli()).thenReturn(new InsideComCliForDataCliImpl(new ComClientEngine()));
        IInsideDataCliForGame dataCliForGame = new InsideDataCliForGameImpl(dataClientEngine);
        dataCliForGame.sendMessage("A perfect message");
        assert (true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageWithEmptyMessage() {
        IInsideDataCliForGame dataCliForGame = new InsideDataCliForGameImpl(new DataClientEngine());
        dataCliForGame.sendMessage("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageWithNullMessage() {
        IInsideDataCliForGame dataCliForGame = new InsideDataCliForGameImpl(new DataClientEngine());
        dataCliForGame.sendMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void playShotTestWithNullParameters() {
        IInsideDataCliForGame iInsideDataCliForGame = new InsideDataCliForGameImpl(new DataClientEngine());
        iInsideDataCliForGame.playShot(null);
    }

    @Test
    public void playShotTestWithNoErrors() {
        DataClientEngine dataClientEngine = mock(DataClientEngine.class);
        IInsideComCliForDataCli iInsideComCliForDataCli = mock(IInsideComCliForDataCli.class);
        when(dataClientEngine.getInsideComCliForDataCli()).thenReturn(iInsideComCliForDataCli);
        Coordinates coordinates = new Coordinates();
        IInsideDataCliForGame iInsideDataCliForGame = new InsideDataCliForGameImpl(dataClientEngine);
        iInsideDataCliForGame.playShot(coordinates);
        verify(iInsideComCliForDataCli, times(1)).sendShot(Mockito.any(Shot.class));
    }
}
