package org.virtual.service.crm;

import java.util.Calendar;
import java.util.List;
import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.SaleForceDtoConverter;

public class SalesForceClient extends CRMClient<SaleForceLeadDTO> {

  public SalesForceClient() {
    this.converter = new SaleForceDtoConverter();
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsSpecific(double lowAnnualRevenue,
      double highAnnualRevenue,
      String state) {
    return List.of();
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific(Calendar startDate, Calendar endDate) {
    return List.of();
  }

  @Override
  protected void addSpecific(SaleForceLeadDTO lead) {
    return;
  }

  @Override
  protected void deleteSpecific(SaleForceLeadDTO lead) {
    return;
  }

  @Override
  protected VirtualLeadDTO convertToVirtual(SaleForceLeadDTO specificLead) {
    return this.converter.convertToVirtual(specificLead);
  }

  @Override
  protected SaleForceLeadDTO convertFromVirtual(VirtualLeadDTO virtualLead) {
    return this.converter.convertFromVirtual(virtualLead);
  }
}
