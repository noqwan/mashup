package fr.univangers.movies.dto.converter;

import fr.univangers.movies.dto.ConvertibleDTO;
import fr.univangers.movies.dto.VirtualLeadDTO;

public interface DtoConverter<T extends ConvertibleDTO> {

  VirtualLeadDTO convertToVirtual(T dto);

  T convertFromVirtual(VirtualLeadDTO dto);

}

