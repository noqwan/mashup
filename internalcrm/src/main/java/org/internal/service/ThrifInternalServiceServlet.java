package org.internal.service;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.internal.thrift.InternalService;

public class ThrifInternalServiceServlet extends ThriftHttpServletTemplate {


  public ThrifInternalServiceServlet() {
    super(createProcessor(), createProtocolFactory());
  }

  private static TProcessor createProcessor() {
    return new InternalService.Processor<InternalService.Iface>(new InternalServiceImpl());
  }

  private static TProtocolFactory createProtocolFactory() {
    return new TBinaryProtocol.Factory();
  }

}
