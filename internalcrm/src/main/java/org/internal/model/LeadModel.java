package org.internal.model;

import java.util.Calendar;
import java.util.List;

public interface LeadModel {

    public List<Lead> findLeads(double lowAnnualRevenue,double highAnnualRevenue, String state);

    public List<Lead> findLeadsByDate(Calendar startDate, Calendar endDate);

    public void addLead(Lead lead);

    public void removeLead(Lead lead);
}
