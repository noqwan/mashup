package org.virtual.service;

import java.util.Calendar;
import java.util.List;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.service.exceptions.NoSuchLeadException;
import org.virtual.service.exceptions.WrongDateFormatException;
import org.virtual.service.exceptions.WrongOrderForDate;
import org.virtual.service.exceptions.WrongOrderForRevenue;
import org.virtual.service.exceptions.WrongState;

public interface VirtualCRMService {

  public List<VirtualLeadDTO> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws WrongOrderForRevenue, WrongState;

  public List<VirtualLeadDTO> findLeadsByDate(Calendar startDate, Calendar endDate)
      throws WrongOrderForDate;

  public void deleteLead(VirtualLeadDTO lead) throws WrongDateFormatException, NoSuchLeadException;

  public void addLead(VirtualLeadDTO lead) throws WrongDateFormatException;

}

