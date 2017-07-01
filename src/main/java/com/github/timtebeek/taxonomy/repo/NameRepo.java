package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.github.timtebeek.taxonomy.model.Name;

public interface NameRepo extends Neo4jRepository<Name, Long> {
	// Extend only
}
