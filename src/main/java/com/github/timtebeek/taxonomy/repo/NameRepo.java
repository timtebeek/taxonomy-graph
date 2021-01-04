package com.github.timtebeek.taxonomy.repo;

import com.github.timtebeek.taxonomy.model.Name;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface NameRepo extends Neo4jRepository<Name, Long> {
	// Extend only
}
