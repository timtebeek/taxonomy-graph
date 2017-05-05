package com.github.timtebeek.taxonomy.model;

import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
@EqualsAndHashCode(of = { "taxid" })
@ToString(exclude = { "children" })
public class Taxon {
	@GraphId
	@Setter(value = AccessLevel.PACKAGE)
	Long			id;

	int				taxid;

	String			rank;

	@Relationship(type = "CHILD_OF")
	Taxon		parent;

	@Relationship(type = "PARENT_OF")
	Set<Taxon>	children = new HashSet<>();
}
