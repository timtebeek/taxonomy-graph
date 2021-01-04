package com.github.timtebeek.taxonomy.repo;

import com.github.timtebeek.taxonomy.model.Taxon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TaxonRepoTest {
    @Autowired
    private TaxonRepo repo;

    @Test
    public void testFindByTaxonidRoot() {
        // Retrieve & assert direct properties
        Taxon root = repo.findByTaxonid(1);
        assertNotNull(root);
        assertEquals(1, root.getTaxonid().longValue());
        assertEquals("no rank", root.getRank());
        // Assert parent
        assertNotNull(root.getParent(), "Parent should not be null");
        assertEquals(root, root.getParent());
        // Assert children
        assertThat(root.getChildren(), hasSize(5));
        assertThat(root.getChildren(), everyItem(hasProperty("parent", equalTo(root))));
    }

    @Test
    public void testTaxonGetChildren() {
        Taxon taxon = repo.findByTaxonid(1269);
        Set<Taxon> children = taxon.getChildren();
        assertThat(children, hasSize(17));
        assertThat(children, not(hasItem(taxon)));
    }

    @Test
    public void testFindByRank() {
        List<Taxon> taxa = repo.findByRank("family");
        assertThat(taxa, hasSize(9481));
        assertThat(taxa, everyItem(hasProperty("rank", equalTo("species"))));
    }

    @Test
    public void testGetLineage() {
        List<Taxon> lineage = repo.getLineage(1270);
        assertEquals(1269, lineage.get(0).getTaxonid());
        assertEquals(1268, lineage.get(1).getTaxonid());
        assertThat(lineage, hasSize(9));
    }
}
