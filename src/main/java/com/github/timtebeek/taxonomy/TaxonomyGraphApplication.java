package com.github.timtebeek.taxonomy;

import java.nio.file.Path;

import com.github.timtebeek.taxonomy.download.Downloader;
import com.github.timtebeek.taxonomy.parser.NodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaxonomyGraphApplication implements ApplicationRunner {
	public static void main(final String[] args) {
		SpringApplication.run(TaxonomyGraphApplication.class, args);
	}

	@Autowired
	private NodeParser parser;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		if (args.containsOption("parse")) {
			Path tardump = Downloader.getTardump();
			parser.parse(tardump);
		}
	}
}
