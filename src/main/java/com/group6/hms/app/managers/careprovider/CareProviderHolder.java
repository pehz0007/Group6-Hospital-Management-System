package com.group6.hms.app.managers.careprovider;

public class CareProviderHolder {

    private static volatile CareProvider instance;

    // Private constructor to prevent instantiation
    private CareProviderHolder() {
    }

    public static CareProvider getCareProvider() {
        if (instance == null) {
            synchronized (CareProviderHolder.class) {
                if (instance == null) {
                    instance = new CareProvider();
                }
            }
        }
        return instance;
    }

}
