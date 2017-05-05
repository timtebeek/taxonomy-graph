package com.github.timtebeek.taxonomy.parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.springframework.stereotype.Service;

@Service
public class NodeParser {
	public void parse(final Path taxdump) throws IOException {
		String vfsnodesdmp = String.format("tar:gz:file://%s!/%s!/nodes.dmp", taxdump.toAbsolutePath(), taxdump.getFileName());
		try (FileObject nodesdmp = VFS.getManager().resolveFile(vfsnodesdmp);
				Scanner scanner = new Scanner(new BufferedInputStream(nodesdmp.getContent().getInputStream()))) {
			System.out.println(scanner.nextLine());
			System.out.println(scanner.nextLine());
			System.out.println(scanner.nextLine());
		}
	}
}
