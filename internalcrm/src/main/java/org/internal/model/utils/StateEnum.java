package org.internal.model.utils;

public enum StateEnum {
  OPEN("open"),
  CONTACTED("contacted"),
  MEETING_SET("meeting_set"),
  QUALIFIED("qualified"),
  CUSTOMER("customer"),
  OPPORTUNITY_LOST("opportunity_lost"),
  UNQUALIFIED("unqualified"),
  INACTIVE("inactive");

  private final String state;

  StateEnum(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }
}
