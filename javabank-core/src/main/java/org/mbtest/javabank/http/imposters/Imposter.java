package org.mbtest.javabank.http.imposters;

import org.checkerframework.checker.units.qual.A;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.mbtest.javabank.http.core.Stub;
import org.mbtest.javabank.http.requests.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class Imposter extends HashMap {
    private static final String PORT = "port";
    private static final String PROTOCOL = "protocol";
    private static final String STUBS = "stubs";

    public Imposter() {
        this.put(PROTOCOL, "http");
        this.put(STUBS, newArrayList());
    }

    public static Imposter fromJSON(JSONObject json) {
        Imposter imposter = new Imposter();
        imposter.putAll(json);

        return imposter;
    }

    public static Imposter anImposter() {
        return new Imposter();
    }

    public Imposter onPort(int port) {
        this.put(PORT, port);
        return this;
    }

    public Imposter addStub(Stub stub) {
        getStubs().add(stub);
        return this;
    }

    public Imposter withStub(Stub stub) {
        this.remove(STUBS);
        this.put(STUBS, newArrayList());
        addStub(stub);
        return this;
    }

    public List<Request> getRequests() {
        JSONArray req = (JSONArray) this.get("requests");
        return (List<Request>) req.stream()
                .flatMap(reqObj -> {
                    if (reqObj instanceof JSONObject) {
                        Request next = new Request();
                        next.putAll((JSONObject) reqObj);
                        return Stream.of(next);
                    }

                    return Stream.empty();
                })
                .collect(Collectors.toList());
    }

    public Imposter withRequestsRecorded(boolean doRecord) {
        this.put("recordRequests", doRecord);
        return this;
    }

    public List<Stub> getStubs() {
        if (containsKey(STUBS)) {
            Object stubs = get(STUBS);
            if (stubs instanceof List) {
                List parsed =  doParseStubsList((List) stubs);
                put(STUBS, parsed);
                return parsed;
            } else if (stubs instanceof JSONArray) {
                JSONArray arrayStubs = (JSONArray) stubs;
                List parsed = doParseStubsList((List) arrayStubs.stream().collect(Collectors.toList()));
                put(STUBS, parsed);
                return parsed;
            } else {
                throw new RuntimeException(stubs + " is not a list");
            }
        } else {
            put(STUBS, new ArrayList<>());
            return (List<Stub>) get(STUBS);
        }
    }

    private static List<Stub> doParseStubsList(List<Object> stubs) {
        return stubs.stream()
                .flatMap(s -> {
                    if (s instanceof Stub) {
                        return Stream.of((Stub) s);
                    } else if (s instanceof JSONObject) {
                        JSONObject stubJson = (JSONObject) s;
                        Stub stubCreated = new Stub();
                        stubCreated.putAll(stubJson);
                        return Stream.of(stubCreated);
                    }

                    return Stream.empty();
                })
                .collect(Collectors.toList());
    }

    public Stub getStub(int index) {
        return getStubs().get(index);
    }

    public JSONObject toJSON() {
        return new JSONObject(this);
    }

    public String toString() {
        return toJSON().toJSONString();
    }

    public int getPort() {
        return Integer.valueOf(String.valueOf(get(PORT)));
    }
}
