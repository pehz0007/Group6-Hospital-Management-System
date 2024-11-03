package com.group6.hms.app.auth;

/**
 * The {@code UserInvalidPasswordException} class is a custom exception that is thrown
 * when a user's password does not meet the required complexity criteria, such as length
 * and character requirements.
 */
public class UserInvalidPasswordException extends RuntimeException {
  private String reason;

  /**
   * Constructs a new {@code UserInvalidPasswordException} with the specified reason.
   *
   * @param reason the reason for the exception, describing why the password is invalid
   */
  public UserInvalidPasswordException(String reason) {
    super(reason);
    this.reason = reason;
  }

  /**
   * Returns the reason why the password is considered invalid.
   *
   * @return a {@code String} explaining the reason for the exception
   */
  public String getReason() {
    return reason;
  }
}
