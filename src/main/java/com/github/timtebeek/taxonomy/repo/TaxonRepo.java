package com.github.timtebeek.taxonomy.repo;

import java.util.List;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.timtebeek.taxonomy.model.Taxon;

public interface TaxonRepo extends GraphRepository<Taxon> {
	Taxon findByTaxonid(int taxonid);

	List<Taxon> findByRank(final String rank);
}
