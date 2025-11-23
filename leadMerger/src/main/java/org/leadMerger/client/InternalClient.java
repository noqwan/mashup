package org.leadMerger.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import org.leadMerger.exception.ClientException;
import org.leadMerger.thrift.InternalLeadDto;
import org.leadMerger.thrift.InternalService;

import java.util.List;

public class InternalClient {

  private InternalService.Client createClient() {
    try {
      TTransport transport = new THttpClient("http://localhost:8080/internalcrm/");
      TProtocol protocol = new TBinaryProtocol(transport);
      return new InternalService.Client(protocol);
    } catch (TTransportException e) {
      throw new ClientException("Unable to create Thrift client", e);
    }
  }

  public List<InternalLeadDto> findLeads(double minRevenue, double maxRevenue) {
    InternalService.Client client = createClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
      return client.findLeads(minRevenue, maxRevenue, "");
    } catch (TException e) {
      throw new ClientException("Error calling InternalCRM", e);
    } finally {
      transport.close();
    }
  }

  public void addLead(InternalLeadDto lead) throws ClientException{
    InternalService.Client client = createClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
      client.addLead(lead);
    } catch (TException e) {
      throw new ClientException("Error adding lead", e);
    } finally {
      transport.close();
    }
  }

  public void deleteLead(InternalLeadDto lead) {
    InternalService.Client client = createClient();
    TTransport transport = client.getInputProtocol().getTransport();

    try {
      transport.open();
      client.deleteLead(lead);
    } catch (TException e) {
      throw new ClientException("Error deleting lead", e);
    } finally {
      transport.close();
    }
  }
}
