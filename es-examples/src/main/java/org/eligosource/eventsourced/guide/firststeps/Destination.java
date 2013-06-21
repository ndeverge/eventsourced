package org.eligosource.eventsourced.guide.firststeps;

import akka.actor.UntypedActor;

import org.eligosource.eventsourced.core.Message;

public class Destination extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message) {
            Message msg = (Message)message;
            System.out.println(String.format("[destination] event = %s", msg.event()));
            msg.confirm(true);
        } else {
            unhandled(message);
        }
    }

}
