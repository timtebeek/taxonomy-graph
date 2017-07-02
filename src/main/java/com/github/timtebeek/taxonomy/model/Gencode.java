package com.github.timtebeek.taxonomy.model;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false, of = { "gencodeid" })
public class Gencode extends AbstractEntity {
	@Index(unique = true, primary = true)
	Integer	gencodeid;

	String	abbreviation, name, cde, starts;

	@Override
	public Integer getId() {
		return gencodeid;
	}
}
