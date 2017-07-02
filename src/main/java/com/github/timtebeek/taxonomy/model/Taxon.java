package com.github.timtebeek.taxonomy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false, of = { "taxonid" })
@ToString(exclude = { "parent", "children" })
public class Taxon extends AbstractEntity {
	@Index(unique = true, primary = true)
	Integer		taxonid;

	String		rank;
	String		emblcode;
	String		comments;

	@Relationship(type = "HAS_PARENT", direction = Relationship.OUTGOING)
	@JsonIgnore
	Taxon		parent;
	@Relationship(type = "HAS_PARENT", direction = Relationship.INCOMING)
	@JsonIgnore
	Set<Taxon>	children	= new HashSet<>();

	@Relationship(type = "HAS_DIVISION")
	Division	division;

	@Relationship(type = "HAS_GENCODE")
	Gencode		gencode;
	@Relationship(type = "HAS_MITGENCODE")
	Gencode		mitgencode;

	@Relationship(type = "HAS_NAME")
	List<Name>	names		= new ArrayList<>();

	public boolean isLeaf() {
		return children.isEmpty();
	}

	@Override
	public Integer getId() {
		return taxonid;
	}

	// Lombok generated methods also need annotations
	@Relationship(type = "HAS_PARENT", direction = Relationship.OUTGOING)
	public void setParent(final Taxon parent) {
		this.parent = parent;
	}

	@Relationship(type = "HAS_PARENT", direction = Relationship.INCOMING)
	public void setChildren(final Set<Taxon> children) {
		this.children = children;
	}
}
