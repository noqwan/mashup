package org.virtual.service.exceptions;

public class SalesForceLeadNotFoundException extends RuntimeException {

  public SalesForceLeadNotFoundException(String message) {
    super(message);
  }
}
