package org.virtual.service.crm;

import org.virtual.dto.InternalLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.converter.InternalDtoConverter;
import java.util.List;

public class InternalClient extends CRMClient<InternalLeadDTO> {

  public InternalClient() {
    this.converter = new InternalDtoConverter();
  }

  @Override
  protected List<InternalLeadDTO> findLeadsSpecific() {
    return List.of();
  }

  @Override
  protected List<InternalLeadDTO> findLeadsByDateSpecific() {
    return List.of();
  }

  @Override
  protected void addSpecific(InternalLeadDTO lead) {
    return;
  }

  @Override
  protected void deleteSpecific(InternalLeadDTO lead) {
    return;
  }

  @Override
  protected VirtualLeadDTO convertToVirtual(InternalLeadDTO specificLead) {
    return this.converter.convertToVirtual(specificLead);
  }

  @Override
  protected InternalLeadDTO convertFromVirtual(VirtualLeadDTO virtualLead) {
    return this.converter.convertFromVirtual(virtualLead);
  }
}
