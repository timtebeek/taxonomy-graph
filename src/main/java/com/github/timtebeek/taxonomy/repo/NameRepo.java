package com.github.timtebeek.taxonomy.repo;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.timtebeek.taxonomy.model.Name;

public interface NameRepo extends GraphRepository<Name> {
	// Extend only
}
