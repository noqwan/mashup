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

public class InternalClient extends CRMClient<InternalLeadDto> {

  public InternalClient() {
    this.converter = new InternalDtoConverter();
  }

  private static InternalService.Client getClient() {
    try {
      TTransport transport = new THttpClient(
          "http://localhost/internalcrm/");
      TProtocol protocol = new TBinaryProtocol(transport);
      return new InternalService.Client(protocol);
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected List<InternalLeadDto> findLeadsSpecific(double lowAnnualRevenue,
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
      return client.findLeads(lowAnnualRevenue, highAnnualRevenue, state);
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
  protected List<InternalLeadDto> findLeadsByDateSpecific(Calendar startDate, Calendar endDate)
      throws WrongOrderForDate {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    String StringStartDate = sdf.format(startDate);
    String StringEndDate = sdf.format(endDate);

    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {
      return client.findLeadsByDate(StringStartDate, StringEndDate);
    } catch (ThriftWrongOrderForDate e) {
      throw new WrongOrderForDate(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected void addSpecific(InternalLeadDto lead) throws WrongDateFormatException {
    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {
      client.addLead(lead);
    } catch (ThriftWrongDateFormat e) {
      throw new WrongDateFormatException(e);
    } catch (TException e) {
      throw new RuntimeException(e);
    } finally {
      transport.close();
    }
  }

  @Override
  protected void deleteSpecific(InternalLeadDto lead)
      throws WrongDateFormatException, NoSuchLeadException {
    InternalService.Client client = getClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException(e);
    }

    try {
      client.addLead(lead);
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
  protected VirtualLeadDTO convertToVirtual(InternalLeadDto specificLead) {
    return this.converter.convertToVirtual(specificLead);
  }

  @Override
  protected InternalLeadDto convertFromVirtual(VirtualLeadDTO virtualLead) {
    return this.converter.convertFromVirtual(virtualLead);
  }
}
