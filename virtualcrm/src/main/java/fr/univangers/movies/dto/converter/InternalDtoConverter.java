package fr.univangers.movies.dto.converter;

import fr.univangers.movies.dto.InternalLeadDTO;
import fr.univangers.movies.dto.VirtualLeadDTO;

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
