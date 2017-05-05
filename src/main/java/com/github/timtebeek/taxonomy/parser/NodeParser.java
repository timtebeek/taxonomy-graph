package com.github.timtebeek.taxonomy.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Path;

import com.github.timtebeek.taxonomy.model.Taxon;
import com.github.timtebeek.taxonomy.repo.TaxonRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class NodeParser {
	private static final String	NODES_DMP	= "tar:gz:file://%s!/%s!/nodes.dmp";
	private static final String	SEPARATOR	= "\t\\|\t?";

	private TaxonRepo		taxonrepo;

	@Transactional
	public void parse(final Path taxdump) throws IOException {
		taxonrepo.deleteAll();

		String vfsnodesdmp = String.format(NODES_DMP, taxdump.toAbsolutePath(), taxdump.getFileName());
		try (FileObject nodesdmp = VFS.getManager().resolveFile(vfsnodesdmp);
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(nodesdmp.getContent().getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Show progress
				int currentLine = reader.getLineNumber();
				if (currentLine % 1000 == 0)
					log.info("Processing line #{}", currentLine);
				if (currentLine % 20000 == 0)
					break;

				String[] row = line.split(SEPARATOR, -1);
				int taxonid = Integer.parseInt(row[0]);
				String rankstr = row[2];
//				String emblcode = row[3];
//				Integer divisionid = Integer.valueOf(row[4]);
//				String comments = row[12];

				// Create taxon
				Taxon taxon = new Taxon();
				taxon.setTaxid(taxonid);
				taxon.setRank(rankstr);
				taxonrepo.save(taxon);
			}
		}
	}
}
