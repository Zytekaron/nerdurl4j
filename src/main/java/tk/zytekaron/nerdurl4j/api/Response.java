package tk.zytekaron.nerdurl4j.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    public boolean success;
    public String data;
    
    public Response(@JsonProperty("success") boolean success,
                    @JsonProperty("data") String data) {
        this.success = success;
        this.data = data;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getData() {
        return data;
    }
}