package org.mbtest.javabank.http.requests;

import junit.framework.TestCase;
import org.assertj.core.util.Maps;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.Map;

public class RequestTest {

    @Test
    public void shouldCreateRequest() {
        Request request = new Request();
        request.put("path", "hello");
        request.put("method", "GET");
        request.put("headers", new JSONObject(Maps.newHashMap("hello", "goodbye")));
        request.put("query", new JSONObject(Maps.newHashMap("hello", "goodbye")));

//        request = request.withInjectedCode("function helo()")

        request.getPath();
        request.getHeaders();
        request.getMethod();
        request.getQuery();
    }

    @Test
    public void shouldCreateRequestNull() {
        Request request = new Request();
        request.getHeaders();
        request.getQuery();
        request.getJSON();
    }

}