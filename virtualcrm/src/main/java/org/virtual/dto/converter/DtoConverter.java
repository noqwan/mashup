package org.virtual.dto.converter;

import org.virtual.dto.ConvertibleDTO;
import org.virtual.dto.VirtualLeadDTO;

public interface DtoConverter<T> {

  VirtualLeadDTO convertToVirtual(T dto);

  T convertFromVirtual(VirtualLeadDTO dto);

}

