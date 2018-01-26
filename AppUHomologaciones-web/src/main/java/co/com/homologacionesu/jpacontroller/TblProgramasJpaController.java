package co.com.homologacionesu.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.entidades.TblMaterias;
import java.util.ArrayList;
import java.util.List;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.jpacontroller.exceptions.IllegalOrphanException;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Objetivo: Administrar las operaciones de programas
 * @author Daniel Serna
 */
public class TblProgramasJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param emf 
     */
    public TblProgramasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    /**
     * 
     * @return 
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Descripción: Método que permite crear un nuevo registro
     * @param tblProgramas
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(TblProgramas tblProgramas) throws RollbackFailureException, Exception {
        if (tblProgramas.getTblMateriasList() == null) {
            tblProgramas.setTblMateriasList(new ArrayList<TblMaterias>());
        }
        if (tblProgramas.getTblHomologacionList() == null) {
            tblProgramas.setTblHomologacionList(new ArrayList<TblHomologacion>());
        }
        if (tblProgramas.getTblHomologacionList1() == null) {
            tblProgramas.setTblHomologacionList1(new ArrayList<TblHomologacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado idEstado = tblProgramas.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                tblProgramas.setIdEstado(idEstado);
            }
            TblUniversidad idUniversidad = tblProgramas.getIdUniversidad();
            if (idUniversidad != null) {
                idUniversidad = em.getReference(idUniversidad.getClass(), idUniversidad.getIdUniversidad());
                tblProgramas.setIdUniversidad(idUniversidad);
            }
            List<TblMaterias> attachedTblMateriasList = new ArrayList<>();
            for (TblMaterias tblMateriasListTblMateriasToAttach : tblProgramas.getTblMateriasList()) {
                tblMateriasListTblMateriasToAttach = em.getReference(tblMateriasListTblMateriasToAttach.getClass(), tblMateriasListTblMateriasToAttach.getIdMateria());
                attachedTblMateriasList.add(tblMateriasListTblMateriasToAttach);
            }
            tblProgramas.setTblMateriasList(attachedTblMateriasList);
            List<TblHomologacion> attachedTblHomologacionList = new ArrayList<>();
            for (TblHomologacion tblHomologacionListTblHomologacionToAttach : tblProgramas.getTblHomologacionList()) {
                tblHomologacionListTblHomologacionToAttach = em.getReference(tblHomologacionListTblHomologacionToAttach.getClass(), tblHomologacionListTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList.add(tblHomologacionListTblHomologacionToAttach);
            }
            tblProgramas.setTblHomologacionList(attachedTblHomologacionList);
            List<TblHomologacion> attachedTblHomologacionList1 = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1TblHomologacionToAttach : tblProgramas.getTblHomologacionList1()) {
                tblHomologacionList1TblHomologacionToAttach = em.getReference(tblHomologacionList1TblHomologacionToAttach.getClass(), tblHomologacionList1TblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1.add(tblHomologacionList1TblHomologacionToAttach);
            }
            tblProgramas.setTblHomologacionList1(attachedTblHomologacionList1);
            em.persist(tblProgramas);
            if (idEstado != null) {
                idEstado.getTblProgramasList().add(tblProgramas);
                idEstado = em.merge(idEstado);
            }
            if (idUniversidad != null) {
                idUniversidad.getTblProgramasList().add(tblProgramas);
                idUniversidad = em.merge(idUniversidad);
            }
            for (TblMaterias tblMateriasListTblMaterias : tblProgramas.getTblMateriasList()) {
                TblProgramas oldIdProgramaOfTblMateriasListTblMaterias = tblMateriasListTblMaterias.getIdPrograma();
                tblMateriasListTblMaterias.setIdPrograma(tblProgramas);
                tblMateriasListTblMaterias = em.merge(tblMateriasListTblMaterias);
                if (oldIdProgramaOfTblMateriasListTblMaterias != null) {
                    oldIdProgramaOfTblMateriasListTblMaterias.getTblMateriasList().remove(tblMateriasListTblMaterias);
                    oldIdProgramaOfTblMateriasListTblMaterias = em.merge(oldIdProgramaOfTblMateriasListTblMaterias);
                }
            }
            for (TblHomologacion tblHomologacionListTblHomologacion : tblProgramas.getTblHomologacionList()) {
                TblProgramas oldProgramaDestinoOfTblHomologacionListTblHomologacion = tblHomologacionListTblHomologacion.getProgramaDestino();
                tblHomologacionListTblHomologacion.setProgramaDestino(tblProgramas);
                tblHomologacionListTblHomologacion = em.merge(tblHomologacionListTblHomologacion);
                if (oldProgramaDestinoOfTblHomologacionListTblHomologacion != null) {
                    oldProgramaDestinoOfTblHomologacionListTblHomologacion.getTblHomologacionList().remove(tblHomologacionListTblHomologacion);
                    oldProgramaDestinoOfTblHomologacionListTblHomologacion = em.merge(oldProgramaDestinoOfTblHomologacionListTblHomologacion);
                }
            }
            for (TblHomologacion tblHomologacionList1TblHomologacion : tblProgramas.getTblHomologacionList1()) {
                TblProgramas oldProgramaOrigenOfTblHomologacionList1TblHomologacion = tblHomologacionList1TblHomologacion.getProgramaOrigen();
                tblHomologacionList1TblHomologacion.setProgramaOrigen(tblProgramas);
                tblHomologacionList1TblHomologacion = em.merge(tblHomologacionList1TblHomologacion);
                if (oldProgramaOrigenOfTblHomologacionList1TblHomologacion != null) {
                    oldProgramaOrigenOfTblHomologacionList1TblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1TblHomologacion);
                    oldProgramaOrigenOfTblHomologacionList1TblHomologacion = em.merge(oldProgramaOrigenOfTblHomologacionList1TblHomologacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Descripción: Método que permite modificar información previamente 
     * guardada
     * @param tblProgramas
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(TblProgramas tblProgramas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblProgramas persistentTblProgramas = em.find(TblProgramas.class, tblProgramas.getIdPrograma());
            TblEstado idEstadoOld = persistentTblProgramas.getIdEstado();
            TblEstado idEstadoNew = tblProgramas.getIdEstado();
            TblUniversidad idUniversidadOld = persistentTblProgramas.getIdUniversidad();
            TblUniversidad idUniversidadNew = tblProgramas.getIdUniversidad();
            List<TblMaterias> tblMateriasListOld = persistentTblProgramas.getTblMateriasList();
            List<TblMaterias> tblMateriasListNew = tblProgramas.getTblMateriasList();
            List<TblHomologacion> tblHomologacionListOld = persistentTblProgramas.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionListNew = tblProgramas.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionList1Old = persistentTblProgramas.getTblHomologacionList1();
            List<TblHomologacion> tblHomologacionList1New = tblProgramas.getTblHomologacionList1();
            List<String> illegalOrphanMessages = null;
            for (TblMaterias tblMateriasListOldTblMaterias : tblMateriasListOld) {
                if (!tblMateriasListNew.contains(tblMateriasListOldTblMaterias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblMaterias " + tblMateriasListOldTblMaterias + " since its idPrograma field is not nullable.");
                }
            }
            for (TblHomologacion tblHomologacionListOldTblHomologacion : tblHomologacionListOld) {
                if (!tblHomologacionListNew.contains(tblHomologacionListOldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionListOldTblHomologacion + " since its programaDestino field is not nullable.");
                }
            }
            for (TblHomologacion tblHomologacionList1OldTblHomologacion : tblHomologacionList1Old) {
                if (!tblHomologacionList1New.contains(tblHomologacionList1OldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionList1OldTblHomologacion + " since its programaOrigen field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                tblProgramas.setIdEstado(idEstadoNew);
            }
            if (idUniversidadNew != null) {
                idUniversidadNew = em.getReference(idUniversidadNew.getClass(), idUniversidadNew.getIdUniversidad());
                tblProgramas.setIdUniversidad(idUniversidadNew);
            }
            List<TblMaterias> attachedTblMateriasListNew = new ArrayList<>();
            for (TblMaterias tblMateriasListNewTblMateriasToAttach : tblMateriasListNew) {
                tblMateriasListNewTblMateriasToAttach = em.getReference(tblMateriasListNewTblMateriasToAttach.getClass(), tblMateriasListNewTblMateriasToAttach.getIdMateria());
                attachedTblMateriasListNew.add(tblMateriasListNewTblMateriasToAttach);
            }
            tblMateriasListNew = attachedTblMateriasListNew;
            tblProgramas.setTblMateriasList(tblMateriasListNew);
            List<TblHomologacion> attachedTblHomologacionListNew = new ArrayList<>();
            for (TblHomologacion tblHomologacionListNewTblHomologacionToAttach : tblHomologacionListNew) {
                tblHomologacionListNewTblHomologacionToAttach = em.getReference(tblHomologacionListNewTblHomologacionToAttach.getClass(), tblHomologacionListNewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionListNew.add(tblHomologacionListNewTblHomologacionToAttach);
            }
            tblHomologacionListNew = attachedTblHomologacionListNew;
            tblProgramas.setTblHomologacionList(tblHomologacionListNew);
            List<TblHomologacion> attachedTblHomologacionList1New = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1NewTblHomologacionToAttach : tblHomologacionList1New) {
                tblHomologacionList1NewTblHomologacionToAttach = em.getReference(tblHomologacionList1NewTblHomologacionToAttach.getClass(), tblHomologacionList1NewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1New.add(tblHomologacionList1NewTblHomologacionToAttach);
            }
            tblHomologacionList1New = attachedTblHomologacionList1New;
            tblProgramas.setTblHomologacionList1(tblHomologacionList1New);
            tblProgramas = em.merge(tblProgramas);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getTblProgramasList().remove(tblProgramas);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getTblProgramasList().add(tblProgramas);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idUniversidadOld != null && !idUniversidadOld.equals(idUniversidadNew)) {
                idUniversidadOld.getTblProgramasList().remove(tblProgramas);
                idUniversidadOld = em.merge(idUniversidadOld);
            }
            if (idUniversidadNew != null && !idUniversidadNew.equals(idUniversidadOld)) {
                idUniversidadNew.getTblProgramasList().add(tblProgramas);
                idUniversidadNew = em.merge(idUniversidadNew);
            }
            for (TblMaterias tblMateriasListNewTblMaterias : tblMateriasListNew) {
                if (!tblMateriasListOld.contains(tblMateriasListNewTblMaterias)) {
                    TblProgramas oldIdProgramaOfTblMateriasListNewTblMaterias = tblMateriasListNewTblMaterias.getIdPrograma();
                    tblMateriasListNewTblMaterias.setIdPrograma(tblProgramas);
                    tblMateriasListNewTblMaterias = em.merge(tblMateriasListNewTblMaterias);
                    if (oldIdProgramaOfTblMateriasListNewTblMaterias != null && !oldIdProgramaOfTblMateriasListNewTblMaterias.equals(tblProgramas)) {
                        oldIdProgramaOfTblMateriasListNewTblMaterias.getTblMateriasList().remove(tblMateriasListNewTblMaterias);
                        oldIdProgramaOfTblMateriasListNewTblMaterias = em.merge(oldIdProgramaOfTblMateriasListNewTblMaterias);
                    }
                }
            }
            for (TblHomologacion tblHomologacionListNewTblHomologacion : tblHomologacionListNew) {
                if (!tblHomologacionListOld.contains(tblHomologacionListNewTblHomologacion)) {
                    TblProgramas oldProgramaDestinoOfTblHomologacionListNewTblHomologacion = tblHomologacionListNewTblHomologacion.getProgramaDestino();
                    tblHomologacionListNewTblHomologacion.setProgramaDestino(tblProgramas);
                    tblHomologacionListNewTblHomologacion = em.merge(tblHomologacionListNewTblHomologacion);
                    if (oldProgramaDestinoOfTblHomologacionListNewTblHomologacion != null && !oldProgramaDestinoOfTblHomologacionListNewTblHomologacion.equals(tblProgramas)) {
                        oldProgramaDestinoOfTblHomologacionListNewTblHomologacion.getTblHomologacionList().remove(tblHomologacionListNewTblHomologacion);
                        oldProgramaDestinoOfTblHomologacionListNewTblHomologacion = em.merge(oldProgramaDestinoOfTblHomologacionListNewTblHomologacion);
                    }
                }
            }
            for (TblHomologacion tblHomologacionList1NewTblHomologacion : tblHomologacionList1New) {
                if (!tblHomologacionList1Old.contains(tblHomologacionList1NewTblHomologacion)) {
                    TblProgramas oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion = tblHomologacionList1NewTblHomologacion.getProgramaOrigen();
                    tblHomologacionList1NewTblHomologacion.setProgramaOrigen(tblProgramas);
                    tblHomologacionList1NewTblHomologacion = em.merge(tblHomologacionList1NewTblHomologacion);
                    if (oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion != null && !oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion.equals(tblProgramas)) {
                        oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1NewTblHomologacion);
                        oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion = em.merge(oldProgramaOrigenOfTblHomologacionList1NewTblHomologacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (IllegalOrphanException ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblProgramas.getIdPrograma();
                if (findTblProgramas(id) == null) {
                    throw new NonexistentEntityException("The tblProgramas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Descripción: Método que permite eliminar información
     * @param id
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblProgramas tblProgramas;
            try {
                tblProgramas = em.getReference(TblProgramas.class, id);
                tblProgramas.getIdPrograma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblProgramas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TblMaterias> tblMateriasListOrphanCheck = tblProgramas.getTblMateriasList();
            for (TblMaterias tblMateriasListOrphanCheckTblMaterias : tblMateriasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblProgramas (" + tblProgramas + ") cannot be destroyed since the TblMaterias " + tblMateriasListOrphanCheckTblMaterias + " in its tblMateriasList field has a non-nullable idPrograma field.");
            }
            List<TblHomologacion> tblHomologacionListOrphanCheck = tblProgramas.getTblHomologacionList();
            for (TblHomologacion tblHomologacionListOrphanCheckTblHomologacion : tblHomologacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblProgramas (" + tblProgramas + ") cannot be destroyed since the TblHomologacion " + tblHomologacionListOrphanCheckTblHomologacion + " in its tblHomologacionList field has a non-nullable programaDestino field.");
            }
            List<TblHomologacion> tblHomologacionList1OrphanCheck = tblProgramas.getTblHomologacionList1();
            for (TblHomologacion tblHomologacionList1OrphanCheckTblHomologacion : tblHomologacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblProgramas (" + tblProgramas + ") cannot be destroyed since the TblHomologacion " + tblHomologacionList1OrphanCheckTblHomologacion + " in its tblHomologacionList1 field has a non-nullable programaOrigen field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblEstado idEstado = tblProgramas.getIdEstado();
            if (idEstado != null) {
                idEstado.getTblProgramasList().remove(tblProgramas);
                idEstado = em.merge(idEstado);
            }
            TblUniversidad idUniversidad = tblProgramas.getIdUniversidad();
            if (idUniversidad != null) {
                idUniversidad.getTblProgramasList().remove(tblProgramas);
                idUniversidad = em.merge(idUniversidad);
            }
            em.remove(tblProgramas);
            em.getTransaction().commit();
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Descripción: Método que lista información previamente guardada
     * @return 
     */
    public List<TblProgramas> findTblProgramasEntities() {
        return findTblProgramasEntities(true, -1, -1);
    }

    /**
     * Descripción: Método que lista información previamente guardada
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<TblProgramas> findTblProgramasEntities(int maxResults, int firstResult) {
        return findTblProgramasEntities(false, maxResults, firstResult);
    }

    /**
     * Descripción: Método que lista información previamente guardada
     * @param all
     * @param maxResults
     * @param firstResult
     * @return 
     */
    private List<TblProgramas> findTblProgramasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblProgramas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Descripción: Método que permite obtener un registro
     * @param id
     * @return 
     */
    public TblProgramas findTblProgramas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblProgramas.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Descripción: Método que permite obtener la cantidad de registros
     * @return 
     */
    public int getTblProgramasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblProgramas> rt = cq.from(TblProgramas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Descripción: Método que consulta por criterio de búsqueda igual a 
     * Universidad
     * @param tblUniversidad
     * @return 
     */
    public List<TblProgramas> consultarPorUniversidad(TblUniversidad tblUniversidad){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("TblProgramas.findByUniversidad");
        try {
            q.setParameter("idUniversidad", tblUniversidad);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
