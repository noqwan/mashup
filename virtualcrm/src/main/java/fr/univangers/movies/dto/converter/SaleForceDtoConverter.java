package fr.univangers.movies.dto.converter;

import fr.univangers.movies.dto.SaleForceLeadDTO;
import fr.univangers.movies.dto.VirtualLeadDTO;

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