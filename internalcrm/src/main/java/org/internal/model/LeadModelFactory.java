package org.internal.model;

public class LeadModelFactory {

  private static LeadModel leadModel;

  public static LeadModel getLeadModel() {
    return (leadModel == null) ? new LeadModelImpl() : leadModel;
  }
}
