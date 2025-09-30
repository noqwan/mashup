package org.internal.service;

import java.util.List;
import org.apache.thrift.TException;
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
    return List.of();
  }

  @Override
  public List<InternalLeadDto> findLeadsByDate(String startDate, String endDate)
      throws ThriftWrongOrderForDate, TException {
    return List.of();
  }

  @Override
  public void addLead(InternalLeadDto lead) throws TException {

  }

  @Override
  public void deleteLead(InternalLeadDto lead) throws ThriftNoSuchLead, TException {

  }
}
