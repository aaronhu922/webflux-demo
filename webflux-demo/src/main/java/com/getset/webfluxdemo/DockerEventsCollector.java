package com.getset.webfluxdemo;

import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.getset.webfluxdemo.model.DockerEvent;
import com.getset.webfluxdemo.repository.DockerEventMongoRepository;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Event;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.EventsResultCallback;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;


@Component
public class DockerEventsCollector implements CommandLineRunner {
	private final Logger log = LoggerFactory.getLogger(getClass());
	

    private DockerEventMongoRepository dockerEventMongoRepository;
    private MongoTemplate mongo;

    public DockerEventsCollector(DockerEventMongoRepository dockerEventMongoRepository, MongoTemplate mongo) {
        this.dockerEventMongoRepository = dockerEventMongoRepository;
        this.mongo= mongo;
    }

    @Override
    public void run(String... args) {

        mongo.dropCollection(DockerEvent.class);
        mongo.createCollection(DockerEvent.class, CollectionOptions.empty().maxDocuments(200).size(100000).capped());

        dockerEventMongoRepository.saveAll(collect()).subscribe();
    }

    private Flux<DockerEvent> collect() {
        DockerClient docker = DockerClientBuilder.getInstance().build();

        return Flux.create((FluxSink<Event> sink) -> {
            EventsResultCallback callback = new EventsResultCallback() {
                @Override
                public void onNext(Event event) {
                    sink.next(event);
                }
            };
            docker.eventsCmd().exec(callback);
        })
                .map(this::trans)
                .doOnNext(e -> log.info(e.toString()));
    }

    private DockerEvent trans(Event event) {
        DockerEvent dockerEvent = new DockerEvent();
        dockerEvent.setAction(event.getAction());
        dockerEvent.setActorId(Objects.requireNonNull(event.getActor()).getId());
        dockerEvent.setFrom(event.getFrom() == null ? null : event.getFrom().replace("//", "_"));
        dockerEvent.setId(UUID.randomUUID().toString());
        dockerEvent.setNode(event.getNode());
        dockerEvent.setStatus(event.getStatus());
        dockerEvent.setTime(event.getTime());
        dockerEvent.setTimeNano(event.getTimeNano());
        dockerEvent.setType(event.getType());
        return dockerEvent;
    }
}
