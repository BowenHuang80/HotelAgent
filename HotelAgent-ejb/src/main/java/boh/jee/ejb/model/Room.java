/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author brian
 */
@Entity
@Table(name = "ROOM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Room.findAll", query = "SELECT r FROM Room r"),
    @NamedQuery(name = "Room.findByRoomId", query = "SELECT r FROM Room r WHERE r.roomId = :roomId"),
    @NamedQuery(name = "Room.findByRoomNumber", query = "SELECT r FROM Room r WHERE r.roomNumber = :roomNumber"),
    @NamedQuery(name = "Room.findByRoomSize", query = "SELECT r FROM Room r WHERE r.roomSize = :roomSize"),
    @NamedQuery(name = "Room.findByRoomType", query = "SELECT r FROM Room r WHERE r.roomType = :roomType"),
    @NamedQuery(name = "Room.findByRoomPrice", query = "SELECT r FROM Room r WHERE r.roomPrice = :roomPrice"),
    @NamedQuery(name = "Room.findByRoomDescription", query = "SELECT r FROM Room r WHERE r.roomDescription = :roomDescription"),
    @NamedQuery(name = "Room.findByRoomFloor", query = "SELECT r FROM Room r WHERE r.roomFloor = :roomFloor"),
    @NamedQuery(name = "Room.findByRoomPica", query = "SELECT r FROM Room r WHERE r.roomPica = :roomPica"),
    @NamedQuery(name = "Room.findByRoomPicb", query = "SELECT r FROM Room r WHERE r.roomPicb = :roomPicb"),
    @NamedQuery(name = "Room.findByRoomPicc", query = "SELECT r FROM Room r WHERE r.roomPicc = :roomPicc")})
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROOM_ID")
    private Integer roomId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROOM_SIZE")
    private int roomSize;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ROOM_TYPE")
    private String roomType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROOM_PRICE")
    private double roomPrice;
    @Size(max = 100)
    @Column(name = "ROOM_DESCRIPTION")
    private String roomDescription;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROOM_FLOOR")
    private short roomFloor;
    @Size(max = 200)
    @Column(name = "ROOM_PICA")
    private String roomPica;
    @Size(max = 200)
    @Column(name = "ROOM_PICB")
    private String roomPicb;
    @Size(max = 200)
    @Column(name = "ROOM_PICC")
    private String roomPicc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomId")
    private Collection<BookingDetail> bookingDetailCollection;

    public Room() {
    }

    public Room(Integer roomId) {
        this.roomId = roomId;
    }

    public Room(Integer roomId, String roomNumber, int roomSize, String roomType, double roomPrice, short roomFloor) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomSize = roomSize;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomFloor = roomFloor;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public short getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(short roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getRoomPica() {
        return roomPica;
    }

    public void setRoomPica(String roomPica) {
        this.roomPica = roomPica;
    }

    public String getRoomPicb() {
        return roomPicb;
    }

    public void setRoomPicb(String roomPicb) {
        this.roomPicb = roomPicb;
    }

    public String getRoomPicc() {
        return roomPicc;
    }

    public void setRoomPicc(String roomPicc) {
        this.roomPicc = roomPicc;
    }

    @XmlTransient
    public Collection<BookingDetail> getBookingDetailCollection() {
        return bookingDetailCollection;
    }

    public void setBookingDetailCollection(Collection<BookingDetail> bookingDetailCollection) {
        this.bookingDetailCollection = bookingDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boh.jee.ejb.model.Room[ roomId=" + roomId + " ]";
    }
    
}
