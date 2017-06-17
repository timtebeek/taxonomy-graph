package com.github.timtebeek.taxonomy.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@NodeEntity
@Data
@EqualsAndHashCode(of = { "gencodeid" })
public class Gencode {
	@GraphId
	@Setter(value = AccessLevel.PACKAGE)
	Long	id;
	@Property(name = "gencode_id")
	@Index(unique = true, primary = true)
	long	gencodeid;

	String	abbreviation, name, cde, starts;
}
