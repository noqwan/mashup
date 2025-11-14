package org.virtual.service.crm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.virtual.dto.InternalLeadDtoWrapper;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.InternalDtoConverter;
import org.virtual.service.exceptions.NoSuchLeadException;
import org.virtual.service.exceptions.WrongDateFormatException;
import org.virtual.service.exceptions.WrongOrderForDate;
import org.virtual.service.exceptions.WrongOrderForRevenue;
import org.virtual.service.exceptions.WrongState;
import org.virtual.thrift.InternalLeadDto;
import org.virtual.thrift.InternalService;
import org.virtual.thrift.ThriftNoSuchLead;
import org.virtual.thrift.ThriftWrongDateFormat;
import org.virtual.thrift.ThriftWrongOrderForDate;
import org.virtual.thrift.ThriftWrongOrderForRevenue;
import org.virtual.thrift.ThriftWrongState;

public class InternalClient extends CRMClient<InternalLeadDtoWrapper> {

  public InternalClient() {
    this.converter = new InternalDtoConverter();
  }

  private static InternalService.Client getClient() {
    try {
      TTransport transport = new THttpClient(
          "http://localhost:8080/internalcrm/");
      TProtocol protocol = new TBinaryProtocol(transport);
      return new InternalService.Client(protocol);
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected List<InternalLeadDtoWrapper> findLeadsSpecific(double lowAnnualRevenue,
      double highAnnualRevenue,
      String state) throws WrongOrderForRevenue, WrongState {

    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {
      List<InternalLeadDto> thritInternalLeads = client.findLeads(lowAnnualRevenue,
          highAnnualRevenue, state);

      return thritInternalLeads.stream()
          .map(l -> new InternalLeadDtoWrapper(l))
          .toList();

    } catch (ThriftWrongOrderForRevenue e) {
      throw new WrongOrderForRevenue(e);
    } catch (ThriftWrongState e) {
      throw new WrongState(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected List<InternalLeadDtoWrapper> findLeadsByDateSpecific(Calendar startDate,
      Calendar endDate)
      throws WrongOrderForDate {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    String StringStartDate = sdf.format(startDate.getTime());
    String StringEndDate = sdf.format(endDate.getTime());

    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {
      List<InternalLeadDto> thritInternalLeads = client.findLeadsByDate(StringStartDate,
          StringEndDate);

      return thritInternalLeads.stream()
          .map(l -> new InternalLeadDtoWrapper(l))
          .toList();

    } catch (ThriftWrongOrderForDate e) {
      throw new WrongOrderForDate(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected void addSpecific(InternalLeadDtoWrapper lead) throws WrongDateFormatException {
    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {

      InternalLeadDto internalLead = lead.getInternalLeadDto();
      client.addLead(internalLead);

    } catch (ThriftWrongDateFormat e) {
      throw new WrongDateFormatException(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected void deleteSpecific(InternalLeadDtoWrapper lead)
      throws WrongDateFormatException, NoSuchLeadException {
    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {

      InternalLeadDto internalLead = lead.getInternalLeadDto();
      client.deleteLead(internalLead);

    } catch (ThriftWrongDateFormat e) {
      throw new WrongDateFormatException(e);
    } catch (ThriftNoSuchLead e) {
      throw new NoSuchLeadException(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected VirtualLeadDTO convertToVirtual(InternalLeadDtoWrapper specificLead) {
    return this.converter.convertToVirtual(specificLead);
  }

  @Override
  protected InternalLeadDtoWrapper convertFromVirtual(VirtualLeadDTO virtualLead) {
    return this.converter.convertFromVirtual(virtualLead);
  }
}
