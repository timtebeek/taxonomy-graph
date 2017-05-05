package com.github.timtebeek.taxonomy.repo;

import java.util.List;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface TaxonRepo extends GraphRepository<Taxon> {
	Taxon findByTaxid(final Integer taxid);

	List<Taxon> findByRank(final String rank);
}
