/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.util;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

public class JPAFactory {
    
    //factoria
    private static final EntityManagerFactory FACTORY;
    
    //unidad de persistncia
    private static final String UP ="Homologacion_PU";
    
    static {
       FACTORY= Persistence.createEntityManagerFactory(UP);
    }
    
    public static EntityManagerFactory getFACTORY() {
        return FACTORY;
    }
    
    
    
}
