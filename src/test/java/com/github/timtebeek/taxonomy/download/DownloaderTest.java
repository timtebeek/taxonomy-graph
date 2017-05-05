package com.github.timtebeek.taxonomy.download;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class DownloaderTest {
	@Test
	public void testDownload() throws IOException {
		Downloader.verifyDownload(Downloader.SOURCE, Downloader.DEST);
		Assert.assertTrue(Downloader.DEST.toFile().exists());
	}
}
