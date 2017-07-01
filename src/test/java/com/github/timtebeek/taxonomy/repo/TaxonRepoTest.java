package com.github.timtebeek.taxonomy.repo;

import static org.hamcrest.Matchers.hasSize;

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
		Assert.assertEquals(1269, child.getTaxonid().longValue());
	}
}
