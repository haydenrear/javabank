package org.mbtest.javabank.http.requests;

import org.intellij.lang.annotations.Language;

import java.util.HashMap;

public class Inject extends HashMap {

    public Inject(@Language("javascript") String code) {
        this.withCode(code);
    }

    public Inject withCode(@Language("javascript") String code) {
        put("code", code);
        return this;
    }

}
