package com.github.timtebeek.taxonomy.repo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.timtebeek.taxonomy.model.Division;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class DivisionRepoTest {
	@Autowired
	private DivisionRepo repo;

	@Test
	public void testFindByDivisionidRoot() {
		Division division = repo.findByDivisionid(8L);
		Assert.assertNotNull(division);
	}
}
