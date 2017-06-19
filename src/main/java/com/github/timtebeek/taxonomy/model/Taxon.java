package com.github.timtebeek.taxonomy.model;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import org.neo4j.ogm.annotation.*;

@NodeEntity
@Data
@EqualsAndHashCode(of = { "taxonid" })
@ToString(exclude = { "parent", "children" })
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
	@Relationship(type = "HAS_PARENT", direction = Relationship.INCOMING)
	List<Taxon>	children	= new ArrayList<>();

	@Relationship(type = "HAS_DIVISION")
	Division	division;

	@Relationship(type = "HAS_GENCODE")
	Gencode		gencode;
	@Relationship(type = "HAS_MITGENCODE")
	Gencode		mitgencode;

	@Relationship(type = "HAS_NAME")
	List<Name>	names		= new ArrayList<>();
}
