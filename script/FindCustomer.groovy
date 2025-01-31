import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityCondition
import org.moqui.entity.EntityFind
import org.moqui.entity.EntityList
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec
EntityFind ef = ec.entity.find("moqui.customerview.FindCustomerView")

ef.selectFields()

if (firstName) { ef.condition('firstName', EntityCondition.LIKE, '%' + firstNameName + '%') }
if (lastName) { ef.condition('lastName', EntityCondition.LIKE, '%' + lastName + '%') }
if (contactNumber){ ef.condition('contactNumber', EntityCondition.EQUALS, contactNumber)}
if (address1){ ef.condition('address1', EntityCondition.LIKE, '%' + address1 + '%')}
if (address2){ ef.condition('address2', EntityCondition.LIKE, '%' + address2 + '%')}
if (city){ ef.condition('city', EntityCondition.LIKE, '%' + city + '%')}
if (postalCode){ ef.condition('postalCode', EntityCondition.EQUALS,  postalCode)}
if (emailAddress){ ef.condition('infoString',EntityCondition.EQUALS,emailAddress)}

ef.orderBy(firstName + " " + lastName)

if (!pageNoLimit) { ef.offset(pageIndex as int, pageSize as int); ef.limit(pageSize as int) }

partyIdList = []
EntityList el = ef.list()

for(EntityValue ev in el){
    partyIdList.add(ev.partyId)
}

partyIdListCount = ef.count()
partyIdListPageIndex = ef.pageIndex
partyIdListPageSize = ef.pageSize
partyIdListPageMaxIndex = ((BigDecimal) (partyIdListCount - 1)).divide(partyIdListPageSize, 0, BigDecimal.ROUND_DOWN) as int
partyIdListPageRangeLow = partyIdListPageIndex * partyIdListPageSize + 1
partyIdListPageRangeHigh = (partyIdListPageIndex * partyIdListPageSize) + partyIdListPageSize
if (partyIdListPageRangeHigh > partyIdListCount) partyIdListPageRangeHigh = partyIdListCount