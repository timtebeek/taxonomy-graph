package com.github.timtebeek.taxonomy.repo;

import com.github.timtebeek.taxonomy.model.Gencode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GencodeRepo extends Neo4jRepository<Gencode, Long> {
	// Extend only
}
