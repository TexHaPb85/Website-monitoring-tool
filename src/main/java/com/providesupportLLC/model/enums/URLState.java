package com.providesupportLLC.model.enums;

public enum URLState {
    OK("OK"),
    WARNING("WARNING"),
    CRITICAL("CRITICAL");

    public String name;

    URLState(String name) {
        this.name = name;
    }
}
