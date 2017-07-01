package com.github.timtebeek.taxonomy.model;

import org.neo4j.ogm.annotation.GraphId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Setter;

public abstract class AbstractEntity {
	@GraphId(name = "id")
	@Setter(value = AccessLevel.PACKAGE)
	@JsonIgnore
	Long graphid;

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	public abstract Long getId();
}
