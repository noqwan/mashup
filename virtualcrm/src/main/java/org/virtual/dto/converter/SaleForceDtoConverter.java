package org.virtual.dto.converter;

import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.PositionDTO;

public class SaleForceDtoConverter implements DtoConverter<SaleForceLeadDTO> {

  @Override
  public VirtualLeadDTO convertToVirtual(SaleForceLeadDTO dto) {
    return new VirtualLeadDTO(
        dto.getId(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getAnnualRevenue(),
        dto.getPhone(),
        dto.getCreationDate(),
        dto.getCompany(),
        dto.getState(),
        dto.getStreet(),
        dto.getPostalCode(),
        dto.getCity(),
        dto.getCountry(),
        null
    );
  }

  @Override
  public SaleForceLeadDTO convertFromVirtual(VirtualLeadDTO dto) {
    return new SaleForceLeadDTO(
        dto.getId(),
        dto.getFirstName(),
        dto.getLastName(),
        dto.getAnnualRevenue(),
        dto.getPhone(),
        dto.getCreationDate(),
        dto.getCompany(),
        dto.getState(),
        dto.getStreet(),
        dto.getPostalCode(),
        dto.getCity(),
        dto.getCountry()
    );
  }
}
