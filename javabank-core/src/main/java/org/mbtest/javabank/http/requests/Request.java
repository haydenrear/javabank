package org.mbtest.javabank.http.requests;

import org.intellij.lang.annotations.Language;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request extends HashMap {

    public Request() {}

    public Request(Map r) {
        this.putAll(r);
    }

    public Request withInjectedCode(@Language("javascript") String injectedCode) {
        this.put("inject", new Inject(injectedCode));
        return this;
    }

     public String getPath() {
        return (String) get("path");
    }

    public Map<String, Object> getHeaders() {
        return (JSONObject) get("headers");
    }

    public String getTimestamp() {
        return (String) get("timestamp");
    }

    public String getMethod() {
        return (String) get("method");
    }

    public String getBody() {
        return (String) get("body");
    }

    public Map<String, Object> getQuery() {
        return (JSONObject) get("query");
    }

    public JSONObject getJSON() {
        return new JSONObject(this);
    }

    public String toString() {
        return getJSON().toJSONString();
    }
}
