package com.github.timtebeek.taxonomy.parser;

import java.io.IOException;
import java.nio.file.Path;

import com.github.timtebeek.taxonomy.download.Downloader;
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

	@Test
	public void testParse() throws IOException {
		Path tardump = Downloader.getTardump();
		parser.parse(tardump);
	}
}
