package com.github.timtebeek.taxonomy.model;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false, of = { "divisionid" })
public class Division extends AbstractEntity {
	@Index(unique = true, primary = true)
	Integer	divisionid;

	String	code, name, comments;

	@Override
	public Integer getId() {
		return divisionid;
	}
}
