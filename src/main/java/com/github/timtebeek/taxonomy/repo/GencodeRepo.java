package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.timtebeek.taxonomy.model.Gencode;

public interface GencodeRepo extends GraphRepository<Gencode> {
	// Extend only
}
