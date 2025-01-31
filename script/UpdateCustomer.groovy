import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityFind
import org.moqui.entity.EntityList
import org.moqui.entity.EntityValue

ExecutionContext ec = context.ec

ansEmail = ec.service.sync().name("PartyServices.find#FindCustomerView").parameters(["emailAddress": emailAddress]).call()

if (ansEmail.get("partyIdList").size()) {
    if(contactNumber || countryCode || areaCode) {
        partyIdList1 =  ansEmail.get("partyIdList")
        for (partyId in partyIdList1) {
            EntityFind ef = ec.entity.find("moqui.partycontactmech.PartyContactMech")
            ef.condition("contactMechPurposeId", "WORK")
            EntityList el = ef.list()
            for (EntityValue ev in el) {
                if(ev.isFieldSet("thruDate")) {
                    def currDate = new Date()
                    ev.setAll(context + ["thruDate": currDate]).update()
                }
            }
        }
        EntityValue contactMech = ec.entity.makeValue("moqui.contactmech.ContactMech").setAll(["infoString": emailAddress, "contactMechTypeEnumId": "TelecomNumber"]).setSequencedIdPrimary().create()
        ec.entity.makeValue("moqui.telecomnumber.TelecomNumber").setAll(["contactMechId": contactMech.contactMechId, "countryCode": countryCode, "areaCode": areaCode, "contactNumber": contactNumber]).create()

        def currDate = new Date()
        ec.entity.makeValue("moqui.partycontactmech.PartyContactMech").setAll(context + ["partyId" : ansEmail.get("partyIdList").get(0), "contactMechId": contactMech.contactMechId, "contactMechPurposeId" : "WORK", "fromDate": currDate]).create()
    }

    if(toName || attnName || address1 || address2 || city || postalCode) {
        partyIdList1 =  ansEmail.get("partyIdList")
        for (partyId in partyIdList1) {
            EntityFind ef = ec.entity.find("moqui.partycontactmech.PartyContactMech")
            ef.condition("contactMechPurposeId", "OFFICE")

            EntityList el = ef.list()
            for (EntityValue ev in el) {
                if(ev.isFieldSet("thruDate") ) {
                    def currDate = new Date()
                    ev.setAll(context + ["thruDate": currDate]).update()
                }
            }
        }
        EntityValue contactMech = ec.entity.makeValue("moqui.contactmech.ContactMech").setAll(["infoString": emailAddress, "contactMechTypeEnumId": "PostalAddress"]).setSequencedIdPrimary().create()

        ec.entity.makeValue("moqui.postaladdress.PostalAddress").setAll(["contactMechId": contactMech.contactMechId, "toName": toName, "attnName": attnName, "address1": address1, "address2": address2, "city": city, "postalCode": postalCode]).create()

        def currDate = new Date()
        ec.entity.makeValue("moqui.partycontactmech.PartyContactMech").setAll(context + ["partyId" : ansEmail.get("partyIdList").get(0), "contactMechId": contactMech.contactMechId, "contactMechPurposeId" : "OFFICE", "fromDate": currDate]).create()
    }
}