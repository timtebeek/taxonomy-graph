package com.github.timtebeek.taxonomy.repo;

import static org.hamcrest.Matchers.*;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.timtebeek.taxonomy.model.Taxon;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TaxonRepoTest {
	@Autowired
	private TaxonRepo repo;

	@Test
	public void testFindByTaxonidRoot() {
		// Retrieve & assert direct properties
		Taxon root = repo.findByTaxonid(1L);
		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getTaxonid().longValue());
		Assert.assertEquals("no rank", root.getRank());
		// Assert parent
		Assert.assertNotNull("Parent should not be null", root.getParent());
		Assert.assertEquals(root, root.getParent());
		// Assert children
		Assert.assertThat(root.getChildren(), hasSize(1));
		Taxon child = root.getChildren().iterator().next();
		Assert.assertEquals(root, child.getParent());
		Assert.assertEquals(1269, child.getTaxonid().longValue()); // FIXME fails to pickup taxonid:1269 as child
	}

	@Test
	public void testTaxonGetChildren() {
		Taxon taxon = repo.findByTaxonid(1269L);
		Set<Taxon> children = taxon.getChildren();
		Assert.assertThat(children, hasSize(1280)); // FIXME 1278
		Assert.assertThat(children, not(hasItem(taxon))); // FIXME includes itself
	}

	@Test
	public void testRepoGetChildren() {
		Taxon taxon = repo.findByTaxonid(1269L);
		List<Taxon> children = repo.getChildren(1269L);
		Assert.assertThat(children, hasSize(1280));
		Assert.assertThat(children, not(hasItem(taxon)));
	}

	@Test
	public void testFindByRank() {
		List<Taxon> taxa = repo.findByRank("species");
		Assert.assertThat(taxa, hasSize(1279));
		Assert.assertThat(taxa, everyItem(hasProperty("rank", equalTo("species")))); // FIXME includes itself
	}

	@Test
	public void testGetByRank() {
		List<Taxon> taxa = repo.getByRank("species");
		Assert.assertThat(taxa, hasSize(1279)); // FIXME 1276
		Assert.assertThat(taxa, everyItem(hasProperty("rank", equalTo("species"))));
	}

	@Autowired
	private Session session;

	@Test
	public void testSessionFindByRankCount() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("rank", "species");
		Result result = session.query("match (n:Taxon) where n.rank = {rank} return count(n) as count", params);
		Assert.assertEquals(1279L, result.iterator().next().get("count"));
	}

	@Test
	public void testSessionFindByRankMapped() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("rank", "species");
		Iterable<Taxon> iterable = session.query(Taxon.class, "match (n:Taxon) where n.rank = {rank} return n", params);
		List<Taxon> list = new ArrayList<>();
		iterable.forEach(list::add);
		Assert.assertEquals(1279L, list.size()); // FIXME 1276
	}
}
