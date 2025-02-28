package org.mbtest.javabank.fluent;

import org.intellij.lang.annotations.Language;
import org.mbtest.javabank.http.responses.Inject;

public class InjectBuilder extends ResponseTypeBuilder {
    @Language("javascript")
    private String function = "";

    public InjectBuilder(ResponseBuilder responseBuilder) {
        super(responseBuilder);
    }

    public InjectBuilder function(@Language("javascript") String function) {
        this.function = function;
        return this;
    }

    @Override
    protected Inject build() {
        return new Inject().withFunction(function);
    }
}
