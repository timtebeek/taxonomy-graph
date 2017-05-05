package com.github.timtebeek.taxonomy.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.github.timtebeek.taxonomy.model.TaxonNode;
import com.github.timtebeek.taxonomy.repo.TaxonNodeRepo;
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

	private TaxonNodeRepo		repo;

	@Transactional
	public void parse(final Path taxdump) throws IOException {
		repo.deleteAll();

		String vfsnodesdmp = String.format(NODES_DMP, taxdump.toAbsolutePath(), taxdump.getFileName());
		try (FileObject nodesdmp = VFS.getManager().resolveFile(vfsnodesdmp);
				LineNumberReader reader = new LineNumberReader(new InputStreamReader(nodesdmp.getContent().getInputStream()))) {
			String line;

			Map<Integer, TaxonNode> cache = new HashMap<>();
			while ((line = reader.readLine()) != null) {
				// Show progress
				int currentLine = reader.getLineNumber();
				if (currentLine % 1000 == 0)
					log.info("Processing line #{}", currentLine);
				if (currentLine % 20000 == 0)
					break;

				String[] row = line.split(SEPARATOR, -1);
				Integer taxonid = Integer.valueOf(row[0]);
				Integer parentid = Integer.valueOf(row[1]);
				String rankstr = row[2];
//				String emblcode = row[3];
//				Integer divisionid = Integer.valueOf(row[4]);
//				String comments = row[12];

				// Find parent
				final TaxonNode parent;
				if(cache.containsKey(parentid))
					;

				// Create taxon
				TaxonNode taxon = new TaxonNode();
				taxon.setTaxid(taxonid);
				taxon.setRank(rankstr);
				TaxonNode saved = repo.save(taxon);



				// Store in poor man's cache for easy by taxid retrieval
				cache.put(taxonid, saved);
			}
		}
	}
}
