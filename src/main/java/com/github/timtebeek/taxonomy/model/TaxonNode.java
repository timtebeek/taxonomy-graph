package com.github.timtebeek.taxonomy.model;

import java.util.Set;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@Data
public class TaxonNode {
	@GraphId
	Long			id;

	Integer			taxid;

	String			rank;

	@Relationship(type = "parent")
	TaxonNode		parent;

	@Relationship(direction = Relationship.INCOMING, type = "child")
	Set<TaxonNode>	children;
}
