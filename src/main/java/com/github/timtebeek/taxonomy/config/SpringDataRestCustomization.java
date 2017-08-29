package com.github.timtebeek.taxonomy.config;

import com.github.timtebeek.taxonomy.model.Taxon;
import com.github.timtebeek.taxonomy.repo.TaxonRepo;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {
	@Override
	public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
		config.withEntityLookup().// FIXME java.lang.String cannot be cast to java.lang.Integer
				forRepository(TaxonRepo.class, Taxon::getTaxonid, TaxonRepo::findByTaxonid);
	}
}
