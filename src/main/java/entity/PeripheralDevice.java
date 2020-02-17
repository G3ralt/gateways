package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PeripheralDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEVICE_ID")
    @Expose
    private Long id;

    @Column(name = "DEVICE_UID",
            nullable = false,
            updatable = false,
            unique = true)
    @Expose
    private Long deviceUID;

    @Column(name = "VENDOR",
            nullable = false,
            length = 255)
    private String vendor;

    @Column(name = "DATE_CREATED",
            nullable = false,
            updatable = false)
    @Temporal(TemporalType.DATE)
    @Expose
    private Date dateCreated;

    @Column(name = "IS_ONLINE",
            nullable = false)
    @Expose
    private boolean online;

    @ManyToOne
    @JoinColumn(name = "GATEWAY_ID_FK",
            referencedColumnName = "gateway_id")
    @Expose
    private Gateway gateway;

    public PeripheralDevice() {
    }

    public PeripheralDevice(Long deviceUID, String vendor, Date dateCreated, boolean online) {
        this.deviceUID = deviceUID;
        this.vendor = vendor;
        this.dateCreated = dateCreated;
        this.online = online;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeripheralDevice)) {
            return false;
        }
        PeripheralDevice other = (PeripheralDevice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PeripheralDevice[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public Long getDeviceUID() {
        return deviceUID;
    }

    public void setDeviceUID(Long deviceUID) {
        this.deviceUID = deviceUID;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

}
