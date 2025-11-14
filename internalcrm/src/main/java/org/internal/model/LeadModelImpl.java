package org.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.internal.model.exception.NoSuchLeadException;
import org.internal.model.exception.WrongOrderForDateException;
import org.internal.model.exception.WrongOrderForRevenueException;
import org.internal.model.exception.WrongStateException;
import org.internal.model.utils.StateEnum;

public class LeadModelImpl implements LeadModel {

  static List<Lead> leads;
  static int nextId = 1;

  static {
    leads = new ArrayList<Lead>();
  }


  @Override
  public List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state)
      throws WrongOrderForRevenueException {

    if (lowAnnualRevenue > highAnnualRevenue) {
      throw new WrongOrderForRevenueException(
          "Low annual revenue must be less than or equal to high annual revenue");
    }

    if (state == null || Arrays.stream(StateEnum.values())
        .noneMatch(s -> s.getState().equals(state))) {
      throw new WrongStateException("State must be one of the following :"
          + Arrays.toString(Arrays.stream(StateEnum.values()).map(StateEnum::getState).toArray()));
    }

    List<Lead> res = leads.stream().filter(l -> l.getAnnualRevenue() >= lowAnnualRevenue
            && l.getAnnualRevenue() <= highAnnualRevenue && l.getState().equals(state))
        .toList();
    
    return res;
  }

  @Override
  public List<Lead> findLeadsByDate(Calendar startDate, Calendar endDate)
      throws WrongOrderForDateException {

    if (startDate.compareTo(endDate) > 0) {
      throw new WrongOrderForDateException("Start date must be before end date");
    }

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
  public void removeLead(Lead lead) throws NoSuchLeadException {
    if (leads.stream().noneMatch(l -> l.getId() == lead.getId())) {
      throw new NoSuchLeadException("No Lead with this id");
    }
    leads.removeIf(l -> l.getId() == lead.getId());
  }
}
