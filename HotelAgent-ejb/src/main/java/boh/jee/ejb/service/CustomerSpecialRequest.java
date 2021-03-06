/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.service;

import boh.jee.ejb.model.CustomerMessage;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brian
 */
//@MessageDriven(mappedName = "jms/JMSDest", activationConfig = {
//    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//})
@JMSDestinationDefinition(name = "myQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "jms/myQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class CustomerSpecialRequest implements MessageListener {
    @Resource
    private MessageDrivenContext mdc;
  
    @PersistenceContext(unitName="boh.jee.ejb_HotelAgent-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
            
    public CustomerSpecialRequest() {
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage tmsg = null;
        
        if(message instanceof TextMessage) {
            tmsg = (TextMessage) message;
            Date msgTime=null;
            String msgStr=null;
            try {
                msgTime = new Date(tmsg.getJMSTimestamp());
                msgStr = tmsg.getText();
            } catch (JMSException ex) {
                Logger.getLogger(CustomerSpecialRequest.class.getName()).log(Level.SEVERE, null, ex);
            }

            CustomerMessage msg = new CustomerMessage();


            int roomIdIdx = msgStr.indexOf("ROOMNUMBER:");
            String roomId = msgStr.substring(roomIdIdx+"ROOMNUMBER:".length(), msgStr.indexOf("\n", roomIdIdx));

            msg.setRoomNumber(roomId);

            int guestIdIdx = msgStr.indexOf("GUESTNAME:");
            String guestId = msgStr.substring(guestIdIdx+"GUESTNAME:".length(), msgStr.indexOf("\n", guestIdIdx));
            msg.setGuestName(guestId);

            int msgIdx =msgStr.indexOf("TEXT:");
            String msg_text = msgStr.substring(msgIdx + "TEXT:".length(), msgStr.length() );

            msg.setMsgText(msg_text);

            msg.setMsgTime(msgTime);
            msg.setMsgDone(Boolean.FALSE);
            try {
                em.persist(msg);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
