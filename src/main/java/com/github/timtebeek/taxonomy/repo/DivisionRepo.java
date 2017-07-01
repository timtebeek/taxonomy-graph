package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.github.timtebeek.taxonomy.model.Division;

public interface DivisionRepo extends Neo4jRepository<Division, Long> {
	// Extend only
}
