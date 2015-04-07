/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.restapi;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.Room;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author brian
 */
@Path("rooms")
@RequestScoped
public class RoomsController {

    @Context
    private UriInfo context;

    
    @EJB
    private RoomServiceRemote rmService;
    /**
     * Creates a new instance of TestResource
     */
    public RoomsController() {
    }

//    /**
//     * Retrieves representation of an instance of boh.jee.ejb.RoomsController
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Path("{roomid}")
//    @Produces("text/plain")
//    public String getText(@PathParam("roomid") String roomId) {
//
//            return "Hello RestApi " + roomId;
//    }

    @GET
    public /*synchronized*/ Response getRoomList() {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonobj;
        List<Room> lst = (List<Room>)rmService.getAll();

        jsonobj = lst;
        try {
            json = mapper.writeValueAsString(jsonobj);
        } catch (IOException ex) {
            return Response.serverError().build();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("{roomid}") //{roomid : (/[\\d+])?}")
    public /*synchronized*/ Response getRoomById(@PathParam("roomid") Integer roomId) {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonobj;

        Room rm = (Room)rmService.getRoomById(roomId);
        jsonobj = rm;
        try {
            json = mapper.writeValueAsString(jsonobj);
        } catch (IOException ex) {
            return Response.serverError().build();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * PUT method for updating or creating an instance of RoomsController
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
