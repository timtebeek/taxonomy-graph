package com.github.timtebeek.taxonomy.repo;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() {
		Taxon root = new Taxon();
		root.setTaxid(1);
		root.setRank("no rank");
		Taxon middle = new Taxon();
		middle.setTaxid(2);
		middle.setRank("superkingdom");
		root.getChildren().add(middle);
		Taxon leaf = new Taxon();
		leaf.setTaxid(3);
		leaf.setRank("species");
		middle.getChildren().add(leaf);
		repo.save(root); // Cascades save
	}

	@Test
	public void testFindByTaxid() {
		Taxon root = repo.findByTaxid(1);
		Assert.assertNotNull(root);
		Assert.assertEquals(1, root.getTaxid());
		Assert.assertEquals("no rank", root.getRank());

		Taxon leaf = repo.findByTaxid(3);
		Assert.assertNotNull(leaf);
		Assert.assertEquals(3, leaf.getTaxid());
		Assert.assertEquals("species", leaf.getRank());
	}

	@Test
	public void testFindByRank() {
		List<Taxon> taxa = repo.findByRank("no rank");
		Assert.assertThat(taxa, hasSize(1));
		Assert.assertEquals(1, taxa.get(0).getTaxid());
	}
}
