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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "GUEST")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="GUEST_ROLE", length=4)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Guest.findAll", query = "SELECT g FROM Guest g"),
    @NamedQuery(name = "Guest.findByGuestId", query = "SELECT g FROM Guest g WHERE g.guestId = :guestId"),
    @NamedQuery(name = "Guest.findByGuestName", query = "SELECT g FROM Guest g WHERE g.guestName = :guestName"),
    @NamedQuery(name = "Guest.findByGuestPhone", query = "SELECT g FROM Guest g WHERE g.guestPhone = :guestPhone"),
    @NamedQuery(name = "Guest.findByGuestEmail", query = "SELECT g FROM Guest g WHERE g.guestEmail = :guestEmail")})
public abstract class Guest implements Serializable {
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 4)
//    @Column(name = "GUEST_ROLE")
//    private String guestRole;
    private static final long serialVersionUID = 1L;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guestId")
    private Collection<Booking> bookingCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guestId")
    private Collection<BookingDetail> bookingDetailCollection;
    
    public Guest() {
    }

    public Guest(Integer guestId) {
        this.guestId = guestId;
    }

    public Guest(Integer guestId, String guestName, String guestPhone, String guestEmail) {
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestPhone = guestPhone;
        this.guestEmail = guestEmail;
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
        if (!(object instanceof Guest)) {
            return false;
        }
        Guest other = (Guest) object;
        if ((this.guestId == null && other.guestId != null) || (this.guestId != null && !this.guestId.equals(other.guestId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "boh.jee.ejb.model.Guest[ guestId=" + guestId + " ]";
    }

//    public String getGuestRole() {
//        return guestRole;
//    }
//
//    public void setGuestRole(String guestRole) {
//        this.guestRole = guestRole;
//    }
    
}
