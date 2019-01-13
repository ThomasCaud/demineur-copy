package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.util.Date;

public class Shot extends AbstractBean implements Serializable {
    private static final long serialVersionUID = 34567976432908765L;

    private SoftPlayer player;
    private Box box;
    private Date date;
    private Action action;

    public Shot() {
	this.setPlayer(new SoftPlayer());
	this.setBox(new Box());
	this.setDate(new Date());
	this.setAction(Action.FLAG);
    }

    public SoftPlayer getPlayer() {
	return player;
    }

    public void setPlayer(SoftPlayer player) {
	this.player = player;
    }

    public Box getBox() {
	return box;
    }

    public void setBox(Box box) {
	this.box = box;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Action getAction() {
	return action;
    }

    public void setAction(Action action) {
	this.action = action;
    }
}
