package org.eligosource.eventsourced.guide.firststeps;

import java.io.File;

import scala.concurrent.duration.Duration;

import akka.actor.*;
import akka.japi.Option;

import org.eligosource.eventsourced.core.*;
import org.eligosource.eventsourced.journal.leveldb.LeveldbJournalProps;

public class FirstSteps {

    public static void main(String... args) throws Exception {
        final ActorSystem system = ActorSystem.create("example");

        final ActorRef journal = LeveldbJournalProps.create(new File("target/guide-1-java")).createJournal(system);
        final EventsourcingExtension extension = EventsourcingExtension.create(system, journal);

        final ActorRef destination = system.actorOf(Props.create(Destination.class));
        final ActorRef channel = extension.channelOf(DefaultChannelProps.create(1, destination), system);
        final ActorRef processor = extension.processorOfs(Props.create(Processor.class, channel), Option.<String>none(), system);

        extension.recover(Duration.create(10, "seconds"));

        processor.tell(Message.create("foo"), null);

        Thread.sleep(1000);
        system.shutdown();
    }

}
