package org.internal.model;

import java.util.Calendar;
import java.util.List;
import org.internal.model.exception.NoSuchLeadException;
import org.internal.model.exception.WrongOrderForDateException;
import org.internal.model.exception.WrongOrderForRevenueException;
import org.internal.model.exception.WrongStateException;

public interface LeadModel {

  List<Lead> findLeads(double lowAnnualRevenue, double highAnnualRevenue, String state)
      throws WrongOrderForRevenueException, WrongStateException;

  List<Lead> findLeadsByDate(Calendar startDate, Calendar endDate)
      throws WrongOrderForDateException;

  void addLead(Lead lead);

  void removeLead(Lead lead) throws NoSuchLeadException;
}
