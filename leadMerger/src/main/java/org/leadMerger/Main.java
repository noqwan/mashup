package org.leadMerger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.leadMerger.client.InternalClient;
import org.leadMerger.client.SalesForceClient;
import org.leadMerger.dto.SaleForceLeadDTO;
import org.leadMerger.dto.SaleForceToInternalDtoConverter;
import org.leadMerger.exception.ClientException;
import org.leadMerger.service.LeadMergerService;
import org.leadMerger.thrift.InternalLeadDto;

public class Main {

  public static void main(String[] args) {
      LeadMergerService leadMerger = new LeadMergerService();
      leadMerger.leadMerge();
  }

}
