/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.webctrl;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.Room;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian
 */
@WebServlet(name = "HotelController", urlPatterns = {"/servlet"})
public class HotelController extends HttpServlet {

    private RoomServiceRemote remoteIF;
    private List<Room> roomList;// = (List<Books>)remoteIF.getBookList();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        remoteIF = (RoomServiceRemote)request.getSession().getAttribute("boh.jee.ejb.RoomServiceRemote");
        if( null == remoteIF ) {
            try {
                Context ctx = new InitialContext();
                remoteIF = (RoomServiceRemote)ctx.lookup(RoomServiceRemote.class.getName());
                request.getSession().setAttribute("boh.jee.ejb.RoomServiceRemote", remoteIF);
            } catch (NamingException ex) {
                Logger.getLogger(HotelController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        roomList = (List<Room>)request.getSession().getAttribute("ROOMS_LIST");
        if( roomList == null ) {
            roomList = (List<Room>)remoteIF.getAll();
            request.setAttribute("ROOMS_LIST", roomList);
        }
        
        String action = request.getParameter("action");
        
        if( action != null && action.equalsIgnoreCase("bookroom") ) {
            String roomId = request.getParameter("roomId");
            String roomType = request.getParameter("roomType");
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/bookroom.jsp");
            rd.forward(request, response);                    
        }
        else {

            request.setAttribute("ROOMS_LIST", roomList);
            
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/roomlist.jsp");
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
