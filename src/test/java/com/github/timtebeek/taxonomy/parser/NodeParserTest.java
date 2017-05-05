package com.github.timtebeek.taxonomy.parser;

import java.io.IOException;
import java.nio.file.Path;

import com.github.timtebeek.taxonomy.download.Downloader;
import org.junit.Test;

public class NodeParserTest {
	NodeParser parser = new NodeParser();

	@Test
	public void testParse() throws IOException {
		Path tardump = Downloader.getTardump();
		parser.parse(tardump);
	}
}
