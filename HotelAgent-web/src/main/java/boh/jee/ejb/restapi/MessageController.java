/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.restapi;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.CustomerMessage;
import boh.jee.ejb.model.Room;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Path("message")
@RequestScoped
public class MessageController {

    @Context
    private UriInfo context;

   @EJB
   private RoomServiceRemote rmService;
    /**
     * Creates a new instance of MessageController
     */
    public MessageController() {
    }

    
    @GET
    public /*synchronized*/ Response getAllMessages() {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonobj;
        List<CustomerMessage> lst = (List<CustomerMessage>)rmService.getAllMessages();

        jsonobj = lst;
        try {
            json = mapper.writeValueAsString(jsonobj);
        } catch (IOException ex) {
            return Response.serverError().build();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{msgdate}") //{roomid : (/[\\d+])?}")
    public /*synchronized*/ Response getRoomById(@PathParam("msgdate") long msgdate) {
        String json;
        ObjectMapper mapper = new ObjectMapper();
        Object jsonobj;

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddTHHmmssZ");
        Date tgtDate= new Date(msgdate - 5000); //5s ago
//        try {
//            tgtDate = sdf.parse(msgdate);
//        } catch (ParseException ex) {
//            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        List<CustomerMessage> msg = (List<CustomerMessage>)rmService.getMessageByDate( tgtDate );
        jsonobj = msg;
        try {
            json = mapper.writeValueAsString(jsonobj);
        } catch (IOException ex) {
            return Response.serverError().build();
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }
    
    
    /**
     * PUT method for updating or creating an instance of MessageController
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
