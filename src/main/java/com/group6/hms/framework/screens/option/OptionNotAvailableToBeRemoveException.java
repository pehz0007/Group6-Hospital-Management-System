package com.group6.hms.framework.screens.option;

/**
 * Exception thrown when an attempt is made to remove an option that is not available.
 */
public class OptionNotAvailableToBeRemoveException extends RuntimeException {
  public OptionNotAvailableToBeRemoveException(String message) {
    super(message);
  }
}
