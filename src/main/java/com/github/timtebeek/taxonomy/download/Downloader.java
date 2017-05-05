package com.github.timtebeek.taxonomy.download;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;

@Slf4j
public class Downloader {
	static final String	SOURCE	= "ftp://ftp.ncbi.nih.gov/pub/taxonomy/taxdump.tar.gz";
	static final Path	DEST	= Paths.get("taxdump.tar.gz");

	public static Path getTardump() throws IOException {
		verifyDownload(SOURCE, DEST);
		return DEST;
	}

	static void verifyDownload(final String sourceUrl, final Path path) throws IOException {
		boolean fileExists = path.toFile().isFile();
		// Only download once a day, as even getting md5sum from FTP takes two seconds
		if (fileExists && path.toFile().lastModified() > System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1))
			return;

		// Download file if remote md5sum has changed
		String remoteMd5 = readUriToString(URI.create(sourceUrl + ".md5"));
		if (!fileExists || !remoteMd5.equals(md5sum(path))) {
			log.info("Downloading {} to {}", sourceUrl, path);
			try (InputStream in = URI.create(sourceUrl).toURL().openStream()) {
				Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		Assert.isTrue(remoteMd5.equals(md5sum(path)), "Remote md5sum does not match local");
	}

	static String md5sum(final Path path) throws IOException, FileNotFoundException {
		try (InputStream is = new FileInputStream(path.toFile())) {
			return DigestUtils.md5DigestAsHex(is);
		}
	}

	static String readUriToString(final URI uri) throws IOException {
		try (Scanner scanner = new Scanner(uri.toURL().openStream(), "UTF-8")) {
			return scanner.useDelimiter("\\A").next().trim().split("\\s")[0];
		}
	}
}
