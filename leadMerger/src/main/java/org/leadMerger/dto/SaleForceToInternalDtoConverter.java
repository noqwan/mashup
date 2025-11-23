package org.leadMerger.dto;

import java.text.SimpleDateFormat;
import org.leadMerger.thrift.InternalLeadDto;

public class SaleForceToInternalDtoConverter {

  public static InternalLeadDto toInternalLeadDto(SaleForceLeadDTO saleForceLeadDTO) {
    InternalLeadDto internalLeadDto = new InternalLeadDto();

    String lastNamefirstName = saleForceLeadDTO.getLastName() + "," + saleForceLeadDTO.getFirstName();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String creationDate = sdf.format(saleForceLeadDTO.getCreationDate());

    // Id will be defined by the internalCrm
    internalLeadDto.setId(-1);
    internalLeadDto.setLastNamefirstName(lastNamefirstName);
    internalLeadDto.setAnnualRevenue(saleForceLeadDTO.getAnnualRevenue());
    internalLeadDto.setPhone(saleForceLeadDTO.getPhone());
    internalLeadDto.setCreationDate(creationDate);
    internalLeadDto.setCompany(saleForceLeadDTO.getCompany());
    internalLeadDto.setState(saleForceLeadDTO.getState());
    internalLeadDto.setStreet(saleForceLeadDTO.getStreet());
    internalLeadDto.setPostalCode(saleForceLeadDTO.getPostalCode());
    internalLeadDto.setCity(saleForceLeadDTO.getCity());
    internalLeadDto.setCountry(saleForceLeadDTO.getCountry());

    return internalLeadDto;
  }

}
