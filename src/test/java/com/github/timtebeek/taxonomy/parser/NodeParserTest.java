package com.github.timtebeek.taxonomy.parser;

import java.io.IOException;
import java.nio.file.Path;

import com.github.timtebeek.taxonomy.download.Downloader;
import com.github.timtebeek.taxonomy.repo.TaxonRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NodeParserTest {
	@Autowired
	private NodeParser parser;
	@Autowired
	private TaxonRepo repo;

	@Test
	public void testParse() throws IOException {
		Path tardump = Downloader.getTardump();
		parser.parse(tardump, true);

		//
		Assert.assertNotNull("First element not found", repo.findByTaxid(1));
		Assert.assertNotNull("Second element not found", repo.findByTaxid(2));
	}
}
