package com.nttdata.agni.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.nttdata.agni.domain.MappingDetail;
import com.nttdata.agni.domain.MappingMaster;
import com.nttdata.agni.exception.DataFormatException;
import com.nttdata.agni.service.MappingService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/fhirtranslator/v2/mapping")
@Api(tags = {"mapping"})
public class MappingController2 extends AbstractRestHandler {

    @Autowired
    private MappingService2 mappingMasterService;

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a mappingMaster resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createMappingMaster(@RequestBody MappingMaster mappingMaster,
                                 HttpServletRequest request, HttpServletResponse response) {
        MappingMaster createdMappingMaster = this.mappingMasterService.createMappingMaster(mappingMaster);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdMappingMaster.getId()).toString());
    
    }

    @RequestMapping(value = "/getall",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all mappingMasters.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    List<MappingMaster> getAllMappingMaster(@ApiParam(value = "The page number (zero-based)", required = true)
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.mappingMasterService.getAllMappings();
    }

    @RequestMapping(value = "/get/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single mappingMaster.", notes = "You have to provide a valid mappingMaster ID.")
    public
    @ResponseBody
    MappingMaster getMappingMaster(@ApiParam(value = "The ID of the mappingMaster.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        MappingMaster mappingMaster = this.mappingMasterService.getMappingMaster(id);
        checkResourceFound(mappingMaster);
        //todo: http://goo.gl/6iNAkz
        return mappingMaster;
    }

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a mappingMaster resource.", notes = "You have to provide a valid mappingMaster ID in the URL and in the payload. The ID attribute can not be updated.")
    public String updateMappingMaster(@ApiParam(value = "The ID of the existing mappingMaster resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody MappingMaster mappingMaster,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.mappingMasterService.getMappingMaster(id));
        //if (id != mappingMaster.getId()) throw new DataFormatException("ID doesn't match!");
        this.mappingMasterService.updateMappingMaster(mappingMaster);
        return "Update Sucessful";   
    }
    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a mappingMaster resource.", notes = "You have to provide a valid mappingMaster ID in the URL and in the payload. The ID attribute can not be updated.")
    public String updateMappingMasterPost(@ApiParam(value = "The ID of the existing mappingMaster resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody MappingMaster mappingMaster,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.mappingMasterService.getMappingMaster(id));
        //if (id != mappingMaster.getId()) throw new DataFormatException("ID doesn't match!");
        this.mappingMasterService.createMappingMaster(mappingMaster);
        return "Update Sucessful";   
    }
    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a mappingMaster resource.", notes = "You have to provide a valid mappingMaster ID in the URL. Once deleted the resource can not be recovered.")
    public String deleteMappingMaster(@ApiParam(value = "The ID of the existing mappingMaster resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.mappingMasterService.getMappingMaster(id));
        this.mappingMasterService.deleteMappingMaster(id);
        return "Delete Sucessful";   
    }
    
    //delete child level mapping by id
    @RequestMapping(value = "/delete/detail/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a mappingchild resource.", notes = "You have to provide a valid child mapping ID in the URL. Once deleted the resource can not be recovered.")
    public String deleteMappingDetail(@ApiParam(value = "The ID of the existing child resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.mappingMasterService.getMappingDetail(id));
        this.mappingMasterService.deleteMappingDetail(id);
         return "Delete Sucessful";      

    }
}
