package com.group6.hms.app.managers.auth;

/**
 * The {@code LoginManagerHolder} class implements the Singleton pattern to manage a single instance
 * of {@link LoginManager}. It provides a thread-safe, lazy-initialized way to access the
 * {@code LoginManager} instance.
 *
 * <p>This class uses double-checked locking to ensure that only one instance of
 * {@code LoginManager} is created, even in a multithreaded environment.</p>
 */
public class LoginManagerHolder {

    private static volatile LoginManager instance;

    /**
     * Private constructor to prevent instantiation of the {@code LoginManagerHolder} class.
     */
    private LoginManagerHolder() {
    }

    /**
     * Returns the singleton instance of {@code LoginManager}. If the instance is null, it
     * initializes it in a thread-safe manner using double-checked locking.
     *
     * @return the singleton instance of {@code LoginManager}
     */
    public static LoginManager getLoginManager() {
        if (instance == null) {
            synchronized (LoginManagerHolder.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

}
