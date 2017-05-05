package com.github.timtebeek.taxonomy.repo;

import java.util.List;

import com.github.timtebeek.taxonomy.model.TaxonNode;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface TaxonRepo extends GraphRepository<TaxonNode> {
	TaxonNode findByTaxid(final Integer taxid);

	List<TaxonNode> findByRank(final String rank);
}
