
namespace java org.virtual.thrift


typedef i32 int

struct InternalLeadDto
{
    1: int id
    2: string lastNamefirstName
    3: double annualRevenue
    4: string phone
    5: string creationDate
    6: string company
    7: string state
    8: string street
    9: string postalCode
    10: string city
    11: string country

}

exception ThriftWrongOrderForDate
{
    1: string message
}

exception ThriftWrongOrderForRevenue
{
    1: string message
}

exception ThriftNoSuchLead
{
    1: string message
}

exception ThriftWrongDateFormat
{
    1: string message
}



service InternalService
{
  list<InternalLeadDto> findLeads(1:double lowAnnualRevenue
                                  2:double highAnnualRevenue
                                  3:string state)
                                  throws(1: ThriftWrongOrderForRevenue e)
  list<InternalLeadDto> findLeadsByDate(1: string startDate
                                        2: string endDate)
                                        throws(1: ThriftWrongOrderForDate e)

  void addLead(1: InternalLeadDto lead)
                  throws (1: ThriftWrongDateFormat e)

  void deleteLead(1: InternalLeadDto lead)
                 throws (1: ThriftNoSuchLead e 2: ThriftWrongDateFormat ee)

}