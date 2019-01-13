package ai12.maven_ai12.game;

import ai12.maven_ai12.beans.SoftPlayer;
import javafx.scene.control.ListCell;

public class SoftPlayerCell  extends ListCell<SoftPlayer>
{
	@Override
	public void updateItem(SoftPlayer item, boolean empty) 
	{
		super.updateItem(item, empty);
		String vString = "";
		//int index = this.getIndex();
		if (!empty){
			vString = item.getLogin();
		}
		
		
		
		this.setText(vString);
		setGraphic(null);
	}
}