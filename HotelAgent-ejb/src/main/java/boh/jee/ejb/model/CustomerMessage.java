/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brian
 */
@Entity
@Table(name = "MESSAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerMessage.findNewByMsgTime", query = "SELECT c FROM CustomerMessage c WHERE c.msgTime >= :msgTime and c.processed=false"),
    @NamedQuery(name = "CustomerMessage.findAll", query = "SELECT c FROM CustomerMessage c"),
    @NamedQuery(name = "CustomerMessage.findByMsgId", query = "SELECT c FROM CustomerMessage c WHERE c.msgId = :msgId"),
    @NamedQuery(name = "CustomerMessage.findByRoomNumber", query = "SELECT c FROM CustomerMessage c WHERE c.roomNumber = :roomNumber"),
    @NamedQuery(name = "CustomerMessage.findByGuestName", query = "SELECT c FROM CustomerMessage c WHERE c.guestName = :guestName"),
    @NamedQuery(name = "CustomerMessage.findByMsgTime", query = "SELECT c FROM CustomerMessage c WHERE c.msgTime >= :msgTime"),
    @NamedQuery(name = "CustomerMessage.findByMsgText", query = "SELECT c FROM CustomerMessage c WHERE c.msgText = :msgText")})
public class CustomerMessage implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROCESSED")
    private Boolean processed;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MSG_ID")
    private Integer msgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GUEST_NAME")
    private String guestName;
    @Column(name = "MSG_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "MSG_TEXT")
    private String msgText;

    public CustomerMessage() {
    }

    public CustomerMessage(Integer msgId) {
        this.msgId = msgId;
    }

    public CustomerMessage(Integer msgId, String roomNumber, String guestName, String msgText) {
        this.msgId = msgId;
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.msgText = msgText;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerMessage)) {
            return false;
        }
        CustomerMessage other = (CustomerMessage) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boh.jee.ejb.model.CustomerMessage[ msgId=" + msgId + " ]";
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
    
}
