package com.github.timtebeek.taxonomy.repo;

import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Set;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TaxonRepoTest {
	@Autowired
	private TaxonRepo repo;

	@Test
	public void testFindByTaxonidRoot() {
		// Retrieve & assert direct properties
		Taxon root = repo.findByTaxonid(1);
		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getTaxonid().longValue());
		Assert.assertEquals("no rank", root.getRank());
		// Assert parent
		Assert.assertNotNull("Parent should not be null", root.getParent());
		Assert.assertEquals(root, root.getParent());
		// Assert children
		Assert.assertThat(root.getChildren(), hasSize(2));
		Assert.assertThat(root.getChildren(), everyItem(hasProperty("parent", equalTo(root))));
	}

	@Test
	public void testTaxonGetChildren() {
		Taxon taxon = repo.findByTaxonid(1269);
		Set<Taxon> children = taxon.getChildren();
		Assert.assertThat(children, hasSize(1280));
		Assert.assertThat(children, not(hasItem(taxon)));
	}

	@Test
	public void testFindByRank() {
		List<Taxon> taxa = repo.findByRank("species");
		Assert.assertThat(taxa, hasSize(1279));
		Assert.assertThat(taxa, everyItem(hasProperty("rank", equalTo("species"))));
	}

	@Test
	public void testGetLineage() {
		List<Taxon> lineage = repo.getLineage(1270);
		Assert.assertEquals(1269, lineage.get(0).getTaxonid().intValue());
		Assert.assertEquals(1, lineage.get(1).getTaxonid().intValue());
		Assert.assertThat(lineage, hasSize(2));
	}
}
