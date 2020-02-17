package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GATEWAY")
@Access(AccessType.FIELD)
public class Gateway implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GATEWAY_ID")
    @Expose
    private Long id;

    @Column(name = "SERIAL_NUMBER",
            nullable = false,
            length = 20,
            unique = true)
    @Expose
    private String serialNumber;

    @Column(name = "NAME",
            nullable = false,
            length = 50)
    @Expose
    private String name;

    @Column(name = "IP_ADDRESS",
            nullable = false,
            length = 15)
    @Expose
    private String ipAddress;

    @OneToMany(mappedBy = "gateway",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @Expose
    private Set<PeripheralDevice> associatedDevices;

    public Gateway() {
    }

    public Gateway(String serialNumber, String name, String ipAddress) throws Exception {
        this.serialNumber = serialNumber;
        this.name = name;
        this.ipAddress = validateIPaddress(ipAddress);
        this.associatedDevices = new HashSet<>(10);
    }

    private String validateIPaddress(String ip) throws Exception {
        if (!Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$")
                .matcher(ip).matches()) {
            throw new Exception("IP address invalid: " + ip);
        }
        return ip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gateway)) {
            return false;
        }
        return this.serialNumber.equals(((Gateway) object).getSerialNumber());
    }

    @Override
    public String toString() {
        return "entity.Gateway[ id=" + id + " ]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) throws Exception {
        this.ipAddress = validateIPaddress(ipAddress);
    }

    public boolean addDevice(PeripheralDevice device) throws Exception {
        if (this.associatedDevices.size() < 10) {
            return this.associatedDevices.add(device);
        } else {
            throw new Exception("Can't add more than 10 devices to a gateway!");
        }
    }

    public boolean removeDevice(PeripheralDevice device) {
        return this.associatedDevices.remove(device);
    }

}
