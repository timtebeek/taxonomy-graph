package com.github.timtebeek.taxonomy;

import com.github.timtebeek.taxonomy.repo.TaxonRepo;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class SpringDataRestCustomization implements RepositoryRestConfigurer {
	@Override
	public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
		config.withEntityLookup()
			.forRepository(TaxonRepo.class)
			.withIdMapping(t -> t.getTaxonid() == null ? null : t.getTaxonid().toString())
			.withLookup((repo, id) -> repo.findByTaxonid(Integer.valueOf(id)));
	}
}
