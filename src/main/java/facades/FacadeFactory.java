package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FacadeFactory {
    
    private static final EntityManagerFactory EMF;
    
    static {
        EMF = Persistence.createEntityManagerFactory("musala_pu");
    }
}
