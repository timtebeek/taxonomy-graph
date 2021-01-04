package com.github.timtebeek.taxonomy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

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
