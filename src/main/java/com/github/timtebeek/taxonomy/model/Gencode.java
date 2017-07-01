package com.github.timtebeek.taxonomy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false, of = { "gencodeid" })
public class Gencode extends AbstractEntity {
	@Index(unique = true, primary = true)
	Long	gencodeid;

	String	abbreviation, name, cde, starts;

	@Override
	public Long getId() {
		return gencodeid;
	}
}
