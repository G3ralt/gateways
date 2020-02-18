package facades;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FacadeFactory {

    private static final EntityManagerFactory EMF;

    static {
        EMF = Persistence.createEntityManagerFactory("musala_pu");
    }

    public static GatewayFacade getGatewayFacade() {
        return new GatewayFacade(EMF.createEntityManager());
    }

    public static PeripheralDeviceFacade getPeripheralDeviceFacade() {
        return new PeripheralDeviceFacade(EMF.createEntityManager());
    }
}
