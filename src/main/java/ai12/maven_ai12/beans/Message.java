package ai12.maven_ai12.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message extends AbstractBean implements Serializable {

    private static final long serialVersionUID = -914313974304129655L;
    private Timestamp timestamp;
    private String text;
    private SoftPlayer author;

    public Message() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.text = "";
        this.author = new SoftPlayer();
    }

    public Message(Timestamp timestamp, String text, SoftPlayer author) {
        this.timestamp = timestamp;
        this.text = text;
        this.author = author;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp ts) {
        this.timestamp = ts;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String txt) {
        this.text = txt;
    }

    public SoftPlayer getAuthor() {
        return this.author;
    }

    public void setAuthor(SoftPlayer a) {
        this.author = a;
    }

}
