package org.virtual.dto.converter;

import org.virtual.dto.ConvertibleDTO;
import org.virtual.dto.VirtualLeadDTO;

public interface DtoConverter<T extends ConvertibleDTO> {

  VirtualLeadDTO convertToVirtual(T dto);

  T convertFromVirtual(VirtualLeadDTO dto);

}

