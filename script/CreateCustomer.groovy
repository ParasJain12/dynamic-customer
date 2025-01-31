import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

ansMap = ec.service.sync().name("PartyServices.find#FindCustomerView").parameters(["emailAddress": emailAddress]).call()

if (!ansMap.get("partyIdList").size()) {
    EntityValue party =  ec.entity.makeValue("moqui.party.Party").setAll(["partyTypeEnumId": "Person"]).setSequencedIdPrimary().create()
    ec.entity.makeValue("moqui.person.Person").setAll(context + ["partyId" : party.partyId]).create()
    ec.entity.makeValue("moqui.partyrole.PartyRole").setAll(["partyId" : party.partyId, "roleTypeId": "CUSTOMER"]).create()

    EntityValue contactMech = ec.entity.makeValue("moqui.contactmech.ContactMech").setAll(["infoString": emailAddress]).setSequencedIdPrimary().create()

    def currDate = new Date()
    ec.entity.makeValue("moqui.partycontactmech.PartyContactMech").setAll(context + ["partyId" : party.partyId, "contactMechId": contactMech.contactMechId, "contactMechPurposeId" : "EmailPrimary", "fromDate": currDate]).create()
}