package com.nttdata.agni.test;
/**
 * @author Harendra
 */


import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.agni.Application;
import com.nttdata.agni.domain.MappingList;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test2")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MappingControllerTest {

    private static final String BASE_URL = "/fhirtranslator/v1/";

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;
    
    private String mapName;
    private int mapSize =0 ;
    List<MappingList> mapping;

    @Before
    public void initTests() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mapName = "test1";
    }

    @Test
    public void AcreateMapping() throws Exception {
    	 mapping = mockMappings();
    	 mapSize = mapping.size();
        byte[] r1Json = toJson(mapping);

        //CREATE
        MvcResult result = mvc.perform(post("/fhirtranslator/v1/mappings/create/"+ mapName)
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(print())
                //.andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
       // long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());



    }

    private List<MappingList> mockMappings() {
        	List<MappingList> mapping =  new ArrayList<MappingList>();
        	mapping.add(new MappingList("patient.identifier.value","PID-3"));
        	mapping.add(new MappingList("patient.name.given","PID-5-2"));
        	return mapping;
    	}

	@Test
    public void BgetMappings() throws Exception {
    	// mapping = GenericResourceTest.mockMappings();
        //byte[] r1Json = toJson(mapping);
		System.out.print("BgetMappings called ");
        //RETRIEVE
    	mvc.perform(get("/fhirtranslator/v1/mappings/view/" + mapName)
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(jsonPath("$", hasSize(2)))
       // .andExpect(jsonPath("$.[*].mapname", hasItems("test1")))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.mapname", is(mapName)));
    	//result.
    }
 
    
    
    public void DtestDeleteByMapName() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/fhirtranslator/v1/mappings/deleteByName/"+mapName)
                .contentType(MediaType.APPLICATION_JSON))
               // .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        System.out.print("DtestDeleteByMapName called ");
        MvcResult result2 = mvc.perform(get("/fhirtranslator/v1/mappings/view/" + mapName )
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(jsonPath("$", hasSize(0)))
       // .andExpect(jsonPath("$.[*].mapname", hasItems("test1")))
                .andExpect(status().isOk()).andReturn();
                //.andExpect(jsonPath("$.mapname", is(mapName)));
    	
    	
    }
    @Test
    public void CtestGetDeleteById() throws Exception {
    	long id = 1;
		//RETRIEVE
    	ResultActions result = mvc.perform(get("/fhirtranslator/v1/mappings/getById/" + id )
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
       // .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$.[*].mapname", hasSize(0)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.mapname", is(mapName)));
    	MvcResult res= result.andReturn();
    	System.out.print("testGetDeleteById Called getById");
    	
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/fhirtranslator/v1/mappings/deleteById/"+id)
                .contentType(MediaType.APPLICATION_JSON))
               // .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    
        MvcResult result2 = mvc.perform(get("/fhirtranslator/v1/mappings/getById/" + id )
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
        //.andExpect(jsonPath("$", hasSize(0)))
        .andExpect(jsonPath("$.[*].mapname", hasSize(0)))
                .andExpect(status().is4xxClientError()).andReturn();
                //.andExpect(jsonPath("$.mapname", is(mapName)));
    	
    	//System.out.print("testGetDeleteById2="+result2.getResponse().getContentLength());

        
    }   

    
 

	private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


 

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
