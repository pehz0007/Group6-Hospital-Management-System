package com.group6.hms.app.auth;

public class UserInvalidPasswordException extends RuntimeException {
  private String reason;

  public UserInvalidPasswordException(String reason) {
    super(reason);
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
