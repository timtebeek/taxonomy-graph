package com.github.timtebeek.taxonomy.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.github.timtebeek.taxonomy.model.Taxon;

public interface TaxonRepo extends Neo4jRepository<Taxon, Long> {
	Taxon findByTaxonid(Long taxonid);

	// FIXME Returns too few taxa for argument rank
	@Query("match (t:Taxon) where t.rank = {0} return t")
	List<Taxon> getByRank(final String rank);

	// FIXME Also returns taxa with a different rank
	@Deprecated
	List<Taxon> findByRank(final String rank);

	@Query("match (n:Taxon)-[:HAS_PARENT]->(t:Taxon) where t.taxonid={0} return n")
	List<Taxon> getChildren(long taxonid);

	@Query("match (n:Taxon)-[:HAS_PARENT*]->(p:Taxon) where n.taxonid = {0} return p")
	Collection<Taxon> getLineage(long taxonid);
}
