package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.timtebeek.taxonomy.model.Division;

public interface DivisionRepo extends GraphRepository<Division> {
	// Extend only
}
