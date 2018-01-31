package com.nttdata.agni.test;


/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.agni.Application;
import com.nttdata.agni.api.rest.MappingController2;
import com.nttdata.agni.domain.MappingMaster;
import com.nttdata.agni.domain.MappingDetail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class MappingControllerTest2 {

   // private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/fhirtranslator/v2/mapping/[0-9]+";
   private static final String RESOURCE_LOCATION_BASEURL = "/fhirtranslator/v2/mapping/";

    @InjectMocks
    MappingController2 controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get(RESOURCE_LOCATION_BASEURL)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        MappingMaster r1 = mockMappingMaster("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);
        String json1="{\"mappingDetail\":[{\"hl7\":\"IN1.3\",\"fhir\":\"Patient\"},{\"hl7\":\"NTE.3\",\"fhir\":\"Patient.id\",\"name\":\"e2\"}]}";
        System.out.println("ZZZZZZZZZZ:"+json1);
        //CREATE
        MvcResult result = mvc.perform(post(RESOURCE_LOCATION_BASEURL+"create")
                .content(json1.getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andDo(print())
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(get(RESOURCE_LOCATION_BASEURL +"get/"+ id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id", is((int) id)));
               // .andExpect(jsonPath("$.name", is(r1.getName())))

        MappingMaster r2 = mockMappingMaster("shouldCreateAndUpdate2");
        
        byte[] r2Json = toJson(r2);
        //CREATE 2
        MvcResult result2 = mvc.perform(post(RESOURCE_LOCATION_BASEURL+"create")
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andDo(print())
                .andReturn();
        //long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE ALL
        mvc.perform(get(RESOURCE_LOCATION_BASEURL +"getall")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
      //          .andExpect(jsonPath("$.id", is((int) id)));

/*
        //DELETE
        mvc.perform(delete(RESOURCE_LOCATION_BASEURL + id))
                .andExpect(status().isOk());

        //RETRIEVE should fail
        mvc.perform(get(RESOURCE_LOCATION_BASEURL + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
*/
        //todo: you can test the 404 error body too.

/*
JSONAssert.assertEquals(
  "{foo: 'bar', baz: 'qux'}",
  JSONObject.fromObject("{foo: 'bar', baz: 'xyzzy'}"));
 */
    }

    //@Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        MappingMaster r1 = mockMappingMaster("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(post(RESOURCE_LOCATION_BASEURL+"create")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                //.andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andDo(print())
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        MappingMaster r2 = mockMappingMaster("shouldCreateAndUpdate2");
        r2.setId(id);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mvc.perform(put(RESOURCE_LOCATION_BASEURL+"update/" + id)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andReturn();

        //RETRIEVE updated
        result =mvc.perform(get(RESOURCE_LOCATION_BASEURL + "get/"+id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id", is((int) id))).andReturn();
        //result.getResponse().getContentAsString().
        //DELETE CHILD
        mvc.perform(delete(RESOURCE_LOCATION_BASEURL +"delete/detail/3"))
                .andExpect(status().isOk());
        System.out.println("*****************REMOVED 3/4****************");
        //RETRIEVE updated
        mvc.perform(get(RESOURCE_LOCATION_BASEURL + "get/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id", is((int) id)));
 
        
        //DELETE
        mvc.perform(delete(RESOURCE_LOCATION_BASEURL +"delete/"+ id))
                .andExpect(status().isOk());
    }


    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private MappingMaster mockMappingMaster(String prefix) {
        //MappingMaster r = new MappingMaster();
        MappingMaster pm1 = new MappingMaster();
        
        

        List<MappingDetail> pdList = new ArrayList<MappingDetail>();
        //MappingDetail pd1 =new MappingDetail("MappingDetail A1");
        pdList.add(new MappingDetail("Generic"," detail"));
        if (prefix=="shouldCreateAndUpdate")
        	pdList.add(new MappingDetail("shouldCreateAndUpdate"," detail"));
        if (prefix=="shouldCreateAndUpdate2")
        	pdList.add(new MappingDetail("shouldCreateAndUpdate2 ","detail"));
        
        pm1.setMappingDetail(pdList);
        
        if (prefix=="shouldCreateAndUpdate")
        	pm1.setName("shouldCreateAndUpdate Master");
        else if (prefix=="shouldCreateAndUpdate2")
        	pm1.setName("shouldCreateAndUpdate2 Master");
        else
        	pm1.setName("shouldCreateRetrieveDelete");
        return pm1;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        System.out.println("YY:"+map.writeValueAsString(r));
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern2(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                //assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
