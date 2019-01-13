package ai12.maven_ai12.game;
import ai12.maven_ai12.beans.SoftPlayer;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class SoftPlayerCellFactory implements Callback<ListView<SoftPlayer>, ListCell<SoftPlayer>>
{
    @Override
    public ListCell<SoftPlayer> call(ListView<SoftPlayer> listview)
    {
        return new SoftPlayerCell();
    }
}
