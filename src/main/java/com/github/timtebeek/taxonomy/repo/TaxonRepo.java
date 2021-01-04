package com.github.timtebeek.taxonomy.repo;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface TaxonRepo extends Neo4jRepository<Taxon, Long> {
	Taxon findByTaxonid(Integer taxonid);

	List<Taxon> findByRank(final String rank);

	@Query("match (n:Taxon)-[:HAS_PARENT*]->(p:Taxon) where n.taxonid = {taxonid} return p")
	List<Taxon> getLineage(int taxonid);
}
