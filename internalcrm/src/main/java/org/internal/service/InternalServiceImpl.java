package org.internal.service;

import java.util.Calendar;
import java.util.List;
import org.apache.thrift.TException;
import org.internal.model.Lead;
import org.internal.model.LeadModelFactory;
import org.internal.model.exception.NoSuchLeadException;
import org.internal.model.exception.WrongDateFormatException;
import org.internal.model.exception.WrongOrderForDateException;
import org.internal.model.exception.WrongOrderForRevenueException;
import org.internal.thrift.InternalLeadDto;
import org.internal.thrift.InternalService;
import org.internal.thrift.ThriftNoSuchLead;
import org.internal.thrift.ThriftWrongOrderForDate;
import org.internal.thrift.ThriftWrongOrderForRevenue;

public class InternalServiceImpl implements InternalService.Iface {

  @Override
  public List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws TException {

    List<InternalLeadDto> ret;
    try {
      List<Lead> leads = LeadModelFactory.getLeadModel()
          .findLeads(lowAnnualRevenue, highAnnualRevenue, state);

      ret = leads.stream().map(lead -> {
        try {
          return ConverterUtils.convertLeadToLeadDto(lead);
        } catch (IllegalArgumentException | NullPointerException e) {
          e.printStackTrace();
          throw new RuntimeException("Error converting Lead to DTO: " + lead, e);
        }
      }).toList();
    } catch (WrongOrderForRevenueException e) {
      e.printStackTrace();
      throw new ThriftWrongOrderForRevenue(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      throw new TException(e);
    }

    return ret;
  }

  @Override
  public List<InternalLeadDto> findLeadsByDate(String startDate, String endDate)
      throws TException {
    List<InternalLeadDto> ret;

    try {
      Calendar calStart = ConverterUtils.toCalendarFromString(startDate);
      Calendar calEnd = ConverterUtils.toCalendarFromString(endDate);

      ret = LeadModelFactory.getLeadModel().findLeadsByDate(calStart, calEnd).stream()
          .map(ConverterUtils::convertLeadToLeadDto).toList();
    } catch (WrongOrderForDateException e) {
      e.printStackTrace();
      throw new ThriftWrongOrderForDate(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      throw new TException(e);
    }
    return ret;
  }

  @Override
  public void addLead(InternalLeadDto lead) throws TException {
    try {
      LeadModelFactory.getLeadModel().addLead(ConverterUtils.convertLeadDtoToLead(lead));
    } catch (WrongDateFormatException e) {
      e.printStackTrace();
      throw new ThriftWrongOrderForDate();
    } catch (Exception e) {
      e.printStackTrace();
      throw new TException(e);
    }
  }

  @Override
  public void deleteLead(InternalLeadDto lead) throws TException {
    try {
      LeadModelFactory.getLeadModel().removeLead(ConverterUtils.convertLeadDtoToLead(lead));
    } catch (NoSuchLeadException e) {
      e.printStackTrace();
      throw new ThriftNoSuchLead(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      throw new TException(e);
    }
  }
}
