package org.eligosource.eventsourced.guide.firststeps;

import akka.actor.*;

import org.eligosource.eventsourced.core.*;

public class Processor extends UntypedEventsourcedActor {

    private ActorRef destination;
    private int counter = 0;

    public Processor(ActorRef destination) {
        this.destination = destination;
    }

    @Override
    public int id() {
        return 1;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message) {
            Message msg = (Message)message;
            counter = counter + 1;
            System.out.println(String.format("[processor] event = %s (%d)", msg.event(), counter));
            destination.tell(msg.withEvent(String.format("processed %d event messages so far", counter)), getSelf());
        } else {
            unhandled(message);
        }
    }

}
