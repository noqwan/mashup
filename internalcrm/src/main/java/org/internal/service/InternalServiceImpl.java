package org.internal.service;

import java.util.Calendar;
import java.util.List;
import org.apache.thrift.TException;
import org.internal.model.LeadModelFactory;
import org.internal.model.exception.NoSuchLeadException;
import org.internal.model.exception.WrongDateFormatException;
import org.internal.model.exception.WrongOrderForDateException;
import org.internal.model.exception.WrongOrderForRevenueException;
import org.internal.model.exception.WrongStateException;
import org.internal.thrift.InternalLeadDto;
import org.internal.thrift.InternalService;
import org.internal.thrift.ThriftNoSuchLead;
import org.internal.thrift.ThriftWrongOrderForDate;
import org.internal.thrift.ThriftWrongOrderForRevenue;
import org.internal.thrift.ThriftWrongState;

public class InternalServiceImpl implements InternalService.Iface {

  @Override
  public List<InternalLeadDto> findLeads(double lowAnnualRevenue, double highAnnualRevenue,
      String state) throws ThriftWrongOrderForRevenue, ThriftWrongState, TException {

    List<InternalLeadDto> ret;

    try {
      ret = LeadModelFactory.getLeadModel()
          .findLeads(lowAnnualRevenue, highAnnualRevenue, state).stream()
          .map(ConverterUtils::convertLeadToLeadDto).toList();
    } catch (WrongOrderForRevenueException e) {
      throw new ThriftWrongOrderForRevenue(e.getMessage());
    } catch (WrongStateException e) {
      throw new ThriftWrongState(e.getMessage());
    } catch (Exception e) {
      throw new TException(e);
    }

    return ret;
  }

  @Override
  public List<InternalLeadDto> findLeadsByDate(String startDate, String endDate)
      throws ThriftWrongOrderForDate, TException {
    List<InternalLeadDto> ret;

    try {
      Calendar calStart = ConverterUtils.toCalendarFromString(startDate);
      Calendar calEnd = ConverterUtils.toCalendarFromString(endDate);

      ret = LeadModelFactory.getLeadModel().findLeadsByDate(calStart, calEnd).stream()
          .map(ConverterUtils::convertLeadToLeadDto).toList();
    } catch (WrongOrderForDateException e) {
      throw new ThriftWrongOrderForDate(e.getMessage());
    } catch (WrongStateException e) {
      throw new WrongDateFormatException(e.getMessage());
    } catch (Exception e) {
      throw new TException(e);
    }
    return ret;
  }

  @Override
  public void addLead(InternalLeadDto lead) throws TException {
    try {
      LeadModelFactory.getLeadModel().addLead(ConverterUtils.convertLeadDtoToLead(lead));
    } catch (Exception e) {
      throw new TException(e);
    }
  }

  @Override
  public void deleteLead(InternalLeadDto lead) throws ThriftNoSuchLead, TException {
    try {
      LeadModelFactory.getLeadModel().removeLead(ConverterUtils.convertLeadDtoToLead(lead));
    } catch (NoSuchLeadException e) {
      throw new ThriftNoSuchLead(e.getMessage());
    } catch (Exception e) {
      throw new TException(e);
    }
  }
}
