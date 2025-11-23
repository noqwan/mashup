package org.leadMerger.exception;

public class ClientException extends RuntimeException {
  public ClientException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public ClientException(String msg) {
    super(msg);
  }
}
