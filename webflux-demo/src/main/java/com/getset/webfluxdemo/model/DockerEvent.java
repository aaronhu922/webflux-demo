package com.getset.webfluxdemo.model;

import com.github.dockerjava.api.model.EventType;
import com.github.dockerjava.api.model.Node;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "docker-event")
public class DockerEvent {

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getTimeNano() {
		return timeNano;
	}

	public void setTimeNano(Long timeNano) {
		this.timeNano = timeNano;
	}

	@Indexed
    private String status;

    @Id
    private String id;

    private String from;

    private Node node;

    private EventType type;

    private String action;

    private String actorId;

    private Long time;

    private Long timeNano;
}
