package com.github.timtebeek.taxonomy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public abstract class AbstractEntity {
	@Id @GeneratedValue
	@JsonIgnore
	Long graphid;

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	public abstract Integer getId();
}
