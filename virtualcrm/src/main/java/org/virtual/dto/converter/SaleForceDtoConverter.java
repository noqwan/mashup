package org.virtual.dto.converter;

import org.virtual.dto.SaleForceLeadDTO;
import org.virtual.dto.VirtualLeadDTO;

public class SaleForceDtoConverter implements DtoConverter<SaleForceLeadDTO> {

  @Override
  public VirtualLeadDTO convertToVirtual(SaleForceLeadDTO dto) {

    return new VirtualLeadDTO(
        "S" + dto.getId(),
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

    // Remove the 'S' prefix
    String id = dto.getId();
    if (id != null && id.startsWith("S")) {
      id = id.substring(1);
    }

    return new SaleForceLeadDTO(
        id,
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
