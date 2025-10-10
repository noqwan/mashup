package org.virtual.dto.converter;

import org.virtual.dto.InternalLeadDTO;
import org.virtual.dto.VirtualLeadDTO;

public class InternalDtoConverter implements DtoConverter<InternalLeadDTO> {

  @Override
  public VirtualLeadDTO convertToVirtual(InternalLeadDTO dto) {
    return null;
  }

  @Override
  public InternalLeadDTO convertFromVirtual(VirtualLeadDTO dto) {
    return null;
  }
}
