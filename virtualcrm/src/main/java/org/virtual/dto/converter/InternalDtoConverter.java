package org.virtual.dto.converter;

import org.virtual.dto.InternalLeadDTO;
import org.virtual.dto.VirtualLeadDTO;
import org.virtual.dto.PositionDTO;

public class InternalDtoConverter implements DtoConverter<InternalLeadDTO> {

  @Override
  public VirtualLeadDTO convertToVirtual(InternalLeadDTO dto) {
    String[] names = dto.getLastNameFirstName().split(", ", 2);
    String lastName = names.length > 0 ? names[0] : "";
    String firstName = names.length > 1 ? names[1] : "";

    return new VirtualLeadDTO(
        dto.getId(),
        firstName,
        lastName,
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
  public InternalLeadDTO convertFromVirtual(VirtualLeadDTO dto) {
    String lastNameFirstName = dto.getLastName() + ", " + dto.getFirstName();

    return new InternalLeadDTO(
        dto.getId(),
        lastNameFirstName,
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
