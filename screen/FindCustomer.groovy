import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityCondition
import org.moqui.entity.EntityFind
import org.moqui.entity.EntityList
import org.moqui.entity.EntityValue

ExecutionContext ec = context.e
EntityFind ef = ec.entity.find("moqui.customerview.FindCustomerView").distinct(true)

ef.selectField("partyId")
if (partyId) { ef.condition(ec.entity.conditionFactory.makeCondition("partyId", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + partyId + "%").ignoreCase()) }
if (firstName) { ef.condition(ec.entity.conditionFactory.makeCondition("firstName", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + firstName + "%").ignoreCase()) }
if (lastName) { ef.condition(ec.entity.conditionFactory.makeCondition("lastName", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + lastName + "%").ignoreCase()) }
if (infoString) { ef.condition(ec.entity.conditionFactory.makeCondition("infoString", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + infoString + "%").ignoreCase()) }
if (countryCode) { ef.condition(ec.entity.conditionFactory.makeCondition("countryCode", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + countryCode + "%").ignoreCase()) }
if (areaCode) { ef.condition(ec.entity.conditionFactory.makeCondition("areaCode", EntityCondition.LIKE, (leadingWildcard ? "%" : "") + areaCode + "%").ignoreCase()) }
if (address1){ ef.condition(ec.entity.conditionFactory.makeCondition("address1", EntityCondition.LIKE, (leadingWildcard ? "%":"") + address1 + "%").ignoreCase())}
if (address2){ ef.condition(ec.entity.conditionFactory.makeCondition("address2", EntityCondition.LIKE, (leadingWildcard ? "%":"") + address2 + "%").ignoreCase())}
if (city){ ef.condition(ec.entity.conditionFactory.makeCondition("city", EntityCondition.LIKE, (leadingWildcard ? "%":"") + city + "%").ignoreCase())}
if (postalCode){ ef.condition(ec.entity.conditionFactory.makeCondition("postalCode", EntityCondition.LIKE, (leadingWildcard ? "%":"") + postalCode + "%").ignoreCase())}

EntityList el = ef.list()
partyIdList = []

for(EntityValue ev in el){
    partyIdList.add(ev.partyId)
}
