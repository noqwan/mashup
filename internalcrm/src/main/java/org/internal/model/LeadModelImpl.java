package org.internal.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeadModelImpl implements LeadModel {

  static List<Lead> leads;
  static int nextId = 1;

  static {
    leads = new ArrayList<Lead>();
  }


  @Override
  public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state) {
    return leads.stream().filter(l -> l.getAnnualRevenue() >= lowAnnualRevenue
            && l.getAnnualRevenue() <= highAnnualRevenue && l.getState().equals(state))
        .toList();
  }

  @Override
  public List<Lead> findLeadsByDate(Calendar startDate, Calendar endDate) {
    return leads.stream().filter(
            l -> l.getCreationDate().compareTo(startDate) >= 0
                && l.getCreationDate().compareTo(endDate) <= 0)
        .toList();
  }

  @Override
  public void addLead(Lead lead) {
    lead.setId(nextId++);
    leads.add(lead);
  }

  @Override
  public void removeLead(Lead lead) {
    leads.removeIf(l -> l.getId() == lead.getId());
  }
}
