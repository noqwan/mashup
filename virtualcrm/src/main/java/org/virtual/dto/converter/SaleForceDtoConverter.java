package org.virtual.dto.converter;

import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;

public class SaleForceDtoConverter implements DtoConverter<SaleForceLeadDTO> {

  @Override
  public VirtualLeadDTO convertToVirtual(SaleForceLeadDTO dto) {
    return null;
  }

  @Override
  public SaleForceLeadDTO convertFromVirtual(VirtualLeadDTO dto) {
    return null;
  }
}