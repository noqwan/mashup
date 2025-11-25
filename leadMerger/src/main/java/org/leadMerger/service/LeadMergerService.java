package org.leadMerger.service;

import org.leadMerger.client.InternalClient;
import org.leadMerger.client.SalesForceClient;
import org.leadMerger.dto.SaleForceLeadDTO;
import org.leadMerger.dto.SaleForceToInternalDtoConverter;
import org.leadMerger.thrift.InternalLeadDto;

import java.util.List;

public class LeadMergerService {
    public void leadMerge(){
        System.out.println("== TOOL TO MERGE SALESFORCE INTO INTERNAL ==");

        System.out.println("Initializing clients");
        SalesForceClient salesforce = new SalesForceClient();
        InternalClient internal = new InternalClient();

        System.out.println("Fetching all leads from SalesForce");
        List<SaleForceLeadDTO> leads = salesforce.findAllLeads();
        System.out.println("Found " + leads.size() + " leads in SalesForce");

        System.out.println("Converting all SalesForceLeadDto to InternalLeadDto");
        List<InternalLeadDto> convertedLeads = leads.stream()
                .map(SaleForceToInternalDtoConverter::toInternalLeadDto)
                .toList();

        System.out.println("Adding all converted leads to internalCrm");
        for (InternalLeadDto lead : convertedLeads) {
            internal.addLead(lead);
        }

        System.out.println("Merging finished");
    }
}
