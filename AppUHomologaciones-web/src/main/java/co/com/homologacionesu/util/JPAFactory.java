package co.com.homologacionesu.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAFactory {
    
    //factoria
    private static final EntityManagerFactory FACTORY;
    
    //unidad de persistncia
    private static final String UP ="Homologacion_PU";
    
    static {
       FACTORY= Persistence.createEntityManagerFactory(UP);
    }
    
    /**
     * 
     * @return 
     */
    public static EntityManagerFactory getFACTORY() {
        return FACTORY;
    }
    
    
    
}
