package org.internal.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeadModelImpl implements LeadModel {

  static List<Lead> leads;

  static {
    leads = new ArrayList<Lead>();
  }


  @Override
  public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) {
    return List.of();
  }

  @Override
  public List<Lead> findLeadsByDate(Calendar startDate, Calendar endDate) {
    return List.of();
  }

  @Override
  public void addLead(Lead lead) {

  }

  @Override
  public void removeLead(Lead lead) {

  }
}
