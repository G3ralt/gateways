package facades;

import entity.Gateway;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class GatewayFacade {

    private final EntityManager EM;

    GatewayFacade(EntityManager em) {
        this.EM = em;
    }

    /**
     * @return the EntityManager owned by this instance
     */
    public EntityManager getEM() {
        return EM;
    }

    public Gateway createGateway(Gateway gw) {
        EM.getTransaction().begin();
        EM.persist(gw);
        EM.getTransaction().commit();
        return gw;
    }

    public List<Gateway> getAllGateways() {
        Query q = EM.createQuery("SELECT g FROM Gateway g");
        return q.getResultList();
    }

    public Gateway getGatewayById(Long id) {
        Query q = EM.createQuery("SELECT g from Gateway g WHERE g.id = ?1");
        q.setParameter(1, id);
        return (Gateway) q.getSingleResult();
    }

    public Gateway updateGateway(Gateway gw) {
        EM.getTransaction().begin();
        EM.merge(gw);
        EM.flush();
        EM.getTransaction().commit();
        return gw;
    }
}
