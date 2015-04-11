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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author brian
 */
@Entity
@Table(name = "BOOKING_DETAIL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookingDetail.findByGuestId", query = "SELECT b FROM BookingDetail b WHERE b.guestId = :guestId"),
    @NamedQuery(name = "BookingDetail.findAvaliableDates", query = "SELECT b FROM BookingDetail b WHERE b.roomId = :roomId and ((b.startDate >= :startDate and b.startDate <= :endDate) or (b.endDate >= :startDate and b.endDate <= :endDate))"),
    @NamedQuery(name = "BookingDetail.findAll", query = "SELECT b FROM BookingDetail b"),
    @NamedQuery(name = "BookingDetail.findByBookingDetailId", query = "SELECT b FROM BookingDetail b WHERE b.bookingDetailId = :bookingDetailId"),
    @NamedQuery(name = "BookingDetail.findByStartDate", query = "SELECT b FROM BookingDetail b WHERE b.startDate = :startDate"),
    @NamedQuery(name = "BookingDetail.findByEndDate", query = "SELECT b FROM BookingDetail b WHERE b.endDate = :endDate")})
public class BookingDetail implements Serializable {
    @JoinColumn(name = "GUEST_ID", referencedColumnName = "GUEST_ID")
    @ManyToOne(optional = false)
    private Guest guestId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BOOKING_DETAIL_ID")
    private Integer bookingDetailId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinColumn(name = "BOOKING_ID", referencedColumnName = "BOOKING_ID")
    @ManyToOne(optional = false)
    private Booking bookingId;
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID")
    @ManyToOne(optional = false)
    private Room roomId;

    public BookingDetail() {
    }

    public BookingDetail(Integer bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public BookingDetail(Integer bookingDetailId, Date startDate, Date endDate) {
        this.bookingDetailId = bookingDetailId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(Integer bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingDetailId != null ? bookingDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookingDetail)) {
            return false;
        }
        BookingDetail other = (BookingDetail) object;
        if ((this.bookingDetailId == null && other.bookingDetailId != null) || (this.bookingDetailId != null && !this.bookingDetailId.equals(other.bookingDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boh.jee.ejb.model.BookingDetail[ bookingDetailId=" + bookingDetailId + " ]";
    }

    public Guest getGuestId() {
        return guestId;
    }

    public void setGuestId(Guest guestId) {
        this.guestId = guestId;
    }
    
}
