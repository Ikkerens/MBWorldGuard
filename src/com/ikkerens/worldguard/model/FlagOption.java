package com.ikkerens.worldguard.model;

public class FlagOption {
    public final static String ALLOW   = "allow";
    public final static String DENY    = "deny";
    public final static String OWNERS  = "owners";
    public final static String MEMBERS = "members";

    private FlagOption() {
        throw new UnsupportedOperationException();
    }
}
