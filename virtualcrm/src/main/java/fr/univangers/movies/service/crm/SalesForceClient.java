package fr.univangers.movies.service.crm;

import fr.univangers.movies.dto.SaleForceLeadDTO;
import fr.univangers.movies.dto.VirtualLeadDTO;
import fr.univangers.movies.dto.converter.SaleForceDtoConverter;
import java.util.List;

public class SalesForceClient extends CRMClient<SaleForceLeadDTO> {

  public SalesForceClient() {
    this.converter = new SaleForceDtoConverter();
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsSpecific() {
    return List.of();
  }

  @Override
  protected List<SaleForceLeadDTO> findLeadsByDateSpecific() {
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
