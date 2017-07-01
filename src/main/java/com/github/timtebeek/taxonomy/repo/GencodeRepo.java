package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.github.timtebeek.taxonomy.model.Gencode;

public interface GencodeRepo extends Neo4jRepository<Gencode, Long> {
	// Extend only
}
