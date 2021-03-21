package org.geekcamp.http.client;

import java.util.Map;

public class HttpResponse extends Http {
    private String statusCode;
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String v) {
        this.statusCode = v;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String v) {
        this.message = v;
    }
}
