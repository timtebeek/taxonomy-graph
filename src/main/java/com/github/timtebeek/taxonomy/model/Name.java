package com.github.timtebeek.taxonomy.model;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import lombok.Data;
import lombok.EqualsAndHashCode;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false, of = { "nameid" })
public class Name extends AbstractEntity {
	@Index(unique = true, primary = true)
	Integer	nameid;

	String	name;
	@Property(name = "unique_name")
	String	uniqueName;
	@Property(name = "name_class")
	String	nameClass;

	@Override
	public Integer getId() {
		return nameid;
	}
}
