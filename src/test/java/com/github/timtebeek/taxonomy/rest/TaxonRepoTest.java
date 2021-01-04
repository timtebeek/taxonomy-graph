package com.github.timtebeek.taxonomy.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxonRepoTest {
    @Autowired
    private MockMvc mockmvc;

    @Test
    public void testCustomEntityLookup() throws Exception {
        mockmvc.perform(get("/taxons/1")).andExpect(status().isOk());
    }
}
