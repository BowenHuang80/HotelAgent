/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.model;

import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author brian
 */
@Entity
@DiscriminatorValue(value = "USER")
public class User extends Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GUEST_ID")
    private Integer guestId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GUEST_NAME")
    private String guestName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GUEST_PHONE")
    private String guestPhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GUEST_EMAIL")
    private String guestEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "GUEST_ROLE")
    private String guestRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guestId")
    private Collection<BookingDetail> bookingDetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guestId")
    private Collection<Booking> bookingCollection;

    public User() {
    }

    public User(Integer guestId) {
        this.guestId = guestId;
    }

    public User(Integer guestId, String guestName, String guestPhone, String guestEmail, String guestRole) {
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
        this.guestRole = guestRole;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }

    public String getGuestRole() {
        return guestRole;
    }

    public void setGuestRole(String guestRole) {
        this.guestRole = guestRole;
    }

    @XmlTransient
    public Collection<BookingDetail> getBookingDetailCollection() {
        return bookingDetailCollection;
    }

    public void setBookingDetailCollection(Collection<BookingDetail> bookingDetailCollection) {
        this.bookingDetailCollection = bookingDetailCollection;
    }

    @XmlTransient
    public Collection<Booking> getBookingCollection() {
        return bookingCollection;
    }

    public void setBookingCollection(Collection<Booking> bookingCollection) {
        this.bookingCollection = bookingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (guestId != null ? guestId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.guestId == null && other.guestId != null) || (this.guestId != null && !this.guestId.equals(other.guestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boh.jee.ejb.model.User[ guestId=" + guestId + " ]";
    }
    
}
