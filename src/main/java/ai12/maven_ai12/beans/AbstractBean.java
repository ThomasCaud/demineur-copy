package ai12.maven_ai12.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractBean {

    public String toString() {
	try {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.writeValueAsString(this);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	    return "";
	}
    }

}
