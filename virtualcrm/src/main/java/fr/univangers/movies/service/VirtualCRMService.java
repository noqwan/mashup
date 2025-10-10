package fr.univangers.movies.service;

import fr.univangers.movies.dto.VirtualLeadDTO;
import java.util.Calendar;
import java.util.List;

public interface VirtualCRMService {

  public List<VirtualLeadDTO> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state);

  public List<VirtualLeadDTO> findLeadsByDate(Calendar startDate, Calendar endDate);

  public void deleteLead(VirtualLeadDTO lead);

  public void addLead(VirtualLeadDTO lead);

}

