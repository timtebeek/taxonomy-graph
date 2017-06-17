package com.github.timtebeek.taxonomy.repo;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
		Taxon root = repo.findByTaxonid(1);
		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getTaxonid());
		Assert.assertEquals("no rank", root.getRank());
		// Assert parent
		Assert.assertNotNull("Parent should not be null", root.getParent());
		Assert.assertEquals(root, root.getParent());
		// Assert division
		Assert.assertNotNull("Division should not be null", root.getDivision());
		Assert.assertEquals("Unassigned", root.getDivision().getName());
		// Assert genetic code
		Assert.assertNotNull("Genetic code should not be null", root.getGencode());
		Assert.assertEquals("Standard", root.getGencode().getName());
		Assert.assertNotNull("Mitochondrial geneticcode should not be null", root.getMitgencode());
		Assert.assertEquals("Unspecified", root.getMitgencode().getName());
		// Assert names
		Assert.assertThat(root.getNames(),
				hasItems(hasProperty("name", equalTo("root")), hasProperty("name", equalTo("all"))));
	}

	@Test
	public void testFindByRankSuperkingdom() {
		List<Taxon> taxa = repo.findByRank("superkingdom");
		Assert.assertThat(taxa, hasSize(5));
		Assert.assertEquals(2, taxa.get(0).getTaxonid());
	}
}
