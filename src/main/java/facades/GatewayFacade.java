package facades;

import entity.Gateway;
import javax.persistence.EntityManager;

public class GatewayFacade {

    private final EntityManager EM;

    GatewayFacade(EntityManager em) {
        this.EM = em;
    }

    /**
     * @return the EntityManager owned by this instance 
     */
    public EntityManager getEM() {
        return this.EM;
    }
    
    public Gateway createGateway(Gateway gw) {
        EM.getTransaction().begin();
        EM.persist(gw);
        EM.getTransaction().commit();
        return gw;
    }
}
