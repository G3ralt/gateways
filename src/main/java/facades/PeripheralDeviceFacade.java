package facades;

import entity.PeripheralDevice;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PeripheralDeviceFacade {

    private final EntityManager EM;

    PeripheralDeviceFacade(EntityManager em) {
        this.EM = em;
    }

    /**
     * @return the EntityManager owned by this instance
     */
    public EntityManager getEM() {
        return EM;
    }

    public PeripheralDevice getGatewayById(Long id) {
        Query q = EM.createQuery("SELECT pd FROM PeripheralDevice pd WHERE pd.id = ?1");
        q.setParameter(1, id);
        return (PeripheralDevice) q.getSingleResult();
    }
}
