package com.github.timtebeek.taxonomy.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.github.timtebeek.taxonomy.model.Taxon;

public interface TaxonRepo extends Neo4jRepository<Taxon, Long> {
	Taxon findByTaxonid(Long taxonid);

	@Query("match (t:Taxon) where t.rank = {rank} return t")
	List<Taxon> getByRank(final String rank);

	// XXX Mysteriously a generated findByRank also returned rank=family records, possibly because of ID(n)
	@Deprecated
	List<Taxon> findByRank(final String rank);


	@Query("match (n:Taxon)-[:HAS_PARENT]->(t:Taxon) where t.taxonid={taxonid} return n")
	List<Taxon> getChildren(long taxonid);

	/**
	 * Find taxa with for instance a partial scientific name.
	 *
	 * @param nameClass
	 * @param name
	 * @return
	 */
	@Query("match (t:Taxon)-[:HAS_NAME]->(n:Name) where n.name_class = {nameClass} and toLower(n.name) contains toLower({name}) return t")
	List<Taxon> getForClassName(String nameClass, String name);

	/**
	 * Find child taxa of argument rank.
	 *
	 * @param taxonid
	 * @param rank
	 * @return
	 */
	@Query("match (n:Taxon)-[:HAS_PARENT*]->(t:Taxon) where n.rank={rank} and t.taxonid={taxonid} return n")
	Collection<Taxon> getChildrenWithRank(long taxonid, String rank);

	/**
	 * Find leafs starting from taxon.
	 *
	 * @param taxonid
	 * @return
	 */
	@Query("match (n:Taxon)-[:HAS_PARENT*]->(t:Taxon)" + //
			" where t.taxonid = {taxonid}" + //
			" and not (:Taxon)-[:HAS_PARENT]->(n:Taxon)" + //
			" return distinct n")
	Collection<Taxon> getLeafs(long taxonid);

	/**
	 * Trace lineage of taxon by returning all parents.
	 *
	 * @param taxonid
	 * @return
	 */
	@Query("match (n:Taxon)-[:HAS_PARENT*]->(p:Taxon) where n.taxonid = {taxonid} return p")
	Collection<Taxon> getLineage(long taxonid);

	/**
	 * Get taxonids for all nodes and leafs below the current taxonid.
	 *
	 * @param taxonid
	 * @return
	 */
	@Query("match (n:Taxon)-[:HAS_PARENT*]->(t:Taxon)" + //
			" where t.taxonid = {taxonid}" + //
			" return distinct n.taxonid")
	List<Long> getSubtreeTaxonids(long taxonid);
}
