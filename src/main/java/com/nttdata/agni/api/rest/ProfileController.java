package com.nttdata.agni.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.nttdata.agni.domain.ProfileDetail;
import com.nttdata.agni.domain.ProfileMaster;
import com.nttdata.agni.exception.DataFormatException;
import com.nttdata.agni.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/fhirtranslator/v1/profile/")
@Api(tags = {"profile"})
public class ProfileController extends AbstractRestHandler {

    @Autowired
    private ProfileService profileMasterService;

    @RequestMapping(value = "/create",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a profileMaster resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createProfileMaster(@RequestBody ProfileMaster profileMaster,
                                 HttpServletRequest request, HttpServletResponse response) {
        ProfileMaster createdProfileMaster = this.profileMasterService.createProfileMaster(profileMaster);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdProfileMaster.getId()).toString());
    
    }

    @RequestMapping(value = "/getall",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all profileMasters.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    List<ProfileMaster> getAllProfileMaster(@ApiParam(value = "The page number (zero-based)", required = true)
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.profileMasterService.getAllProfiles();
    }

    @RequestMapping(value = "/get/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single profileMaster.", notes = "You have to provide a valid profileMaster ID.")
    public
    @ResponseBody
    ProfileMaster getProfileMaster(@ApiParam(value = "The ID of the profileMaster.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProfileMaster profileMaster = this.profileMasterService.getProfileMaster(id);
        checkResourceFound(profileMaster);
        //todo: http://goo.gl/6iNAkz
        return profileMaster;
    }

    @RequestMapping(value = "/update/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update a profileMaster resource.", notes = "You have to provide a valid profileMaster ID in the URL and in the payload. The ID attribute can not be updated.")
    public String updateProfileMaster(@ApiParam(value = "The ID of the existing profileMaster resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody ProfileMaster profileMaster,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.profileMasterService.getProfileMaster(id));
        if (id != profileMaster.getId()) throw new DataFormatException("ID doesn't match!");
        this.profileMasterService.updateProfileMaster(profileMaster);
        return "Update Sucessful";   
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a profileMaster resource.", notes = "You have to provide a valid profileMaster ID in the URL. Once deleted the resource can not be recovered.")
    public String deleteProfileMaster(@ApiParam(value = "The ID of the existing profileMaster resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.profileMasterService.getProfileMaster(id));
        this.profileMasterService.deleteProfileMaster(id);
        return "Delete Sucessful";   
    }
    
    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/delete/detail/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete a profileMaster resource.", notes = "You have to provide a valid profileMaster ID in the URL. Once deleted the resource can not be recovered.")
    public String deleteProfileDetail(@ApiParam(value = "The ID of the existing profileMaster resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.profileMasterService.getProfileDetail(id));
        this.profileMasterService.deleteProfileDetail(id);
         return "Delete Sucessful";      
        
    }
}
