package ai12.maven_ai12.beans;

import java.io.Serializable;

public class Picture extends AbstractBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String raw;

    public Picture() {
	this.setRaw("fakePicture");
    }

    public String getRaw() {
	return this.raw;
    }

    public void setRaw(String raw) {
	this.raw = raw;
    }

}
