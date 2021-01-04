package com.github.timtebeek.taxonomy.repo;

import com.github.timtebeek.taxonomy.model.Division;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DivisionRepo extends Neo4jRepository<Division, Long> {
	// Extend only
}
