package com.github.timtebeek.taxonomy.model;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.*;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@NodeEntity
@Data
@EqualsAndHashCode(of = { "taxonid" })
public class Taxon {
	@GraphId
	@Setter(value = AccessLevel.PACKAGE)
	Long		id;
	@Property(name = "tax_id")
	@Index(unique = true, primary = true)
	long		taxonid;

	String		rank;
	String		emblcode;
	String		comments;

	@Relationship(type = "HAS_PARENT")
	Taxon		parent;

	@Relationship(type = "HAS_DIVISION")
	Division	division;

	@Relationship(type = "HAS_GENCODE")
	Gencode	gencode;
	@Relationship(type = "HAS_MITGENCODE")
	Gencode	mitgencode;

	@Relationship(type = "HAS_NAME")
	List<Name>	names	= new ArrayList<>();
}
