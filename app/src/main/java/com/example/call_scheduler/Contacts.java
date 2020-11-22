package com.example.call_scheduler;
import java.util.HashMap;

final public class Contacts {
    final static HashMap<String, CallRequest> contacts = new HashMap<String, CallRequest>();
    public Contacts() {
        contacts.put("limor", new CallRequest(8, 0,10, 0,
                22, 11,2020, "0548343700"));
        contacts.put("mor", new CallRequest(11, 30,17, 40,
                21, 11,2020, "0522253170"));
        contacts.put("bar", new CallRequest(19, 50,21, 10,
                23, 11,2020, "0526379155"));
    }
    public HashMap<String, CallRequest> getContacts() {
        HashMap<String, CallRequest> deepCopyContacts = new HashMap<String, CallRequest>();
        for (String name: contacts.keySet()) {
            deepCopyContacts.put(name, new CallRequest(contacts.get(name)));
        }
        return deepCopyContacts;
    }
}
