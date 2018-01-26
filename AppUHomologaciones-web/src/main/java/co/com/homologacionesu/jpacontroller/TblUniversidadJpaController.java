package co.com.homologacionesu.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import java.util.ArrayList;
import java.util.List;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.jpacontroller.exceptions.IllegalOrphanException;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Objetivo: Administrar las operaciones de la tabla Universidad
 * @author Daniel Serna
 */
public class TblUniversidadJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param emf 
     */
    public TblUniversidadJpaController(EntityManagerFactory emf) {
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
     * Descripción: Método que permite guardar información
     * @param tblUniversidad
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(TblUniversidad tblUniversidad) throws RollbackFailureException, Exception {
        if (tblUniversidad.getTblHomologacionList() == null) {
            tblUniversidad.setTblHomologacionList(new ArrayList<TblHomologacion>());
        }
        if (tblUniversidad.getTblHomologacionList1() == null) {
            tblUniversidad.setTblHomologacionList1(new ArrayList<TblHomologacion>());
        }
        if (tblUniversidad.getTblProgramasList() == null) {
            tblUniversidad.setTblProgramasList(new ArrayList<TblProgramas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado idEstado = tblUniversidad.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                tblUniversidad.setIdEstado(idEstado);
            }
            List<TblHomologacion> attachedTblHomologacionList = new ArrayList<>();
            for (TblHomologacion tblHomologacionListTblHomologacionToAttach : tblUniversidad.getTblHomologacionList()) {
                tblHomologacionListTblHomologacionToAttach = em.getReference(tblHomologacionListTblHomologacionToAttach.getClass(), tblHomologacionListTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList.add(tblHomologacionListTblHomologacionToAttach);
            }
            tblUniversidad.setTblHomologacionList(attachedTblHomologacionList);
            List<TblHomologacion> attachedTblHomologacionList1 = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1TblHomologacionToAttach : tblUniversidad.getTblHomologacionList1()) {
                tblHomologacionList1TblHomologacionToAttach = em.getReference(tblHomologacionList1TblHomologacionToAttach.getClass(), tblHomologacionList1TblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1.add(tblHomologacionList1TblHomologacionToAttach);
            }
            tblUniversidad.setTblHomologacionList1(attachedTblHomologacionList1);
            List<TblProgramas> attachedTblProgramasList = new ArrayList<>();
            for (TblProgramas tblProgramasListTblProgramasToAttach : tblUniversidad.getTblProgramasList()) {
                tblProgramasListTblProgramasToAttach = em.getReference(tblProgramasListTblProgramasToAttach.getClass(), tblProgramasListTblProgramasToAttach.getIdPrograma());
                attachedTblProgramasList.add(tblProgramasListTblProgramasToAttach);
            }
            tblUniversidad.setTblProgramasList(attachedTblProgramasList);
            em.persist(tblUniversidad);
            if (idEstado != null) {
                idEstado.getTblUniversidadList().add(tblUniversidad);
                idEstado = em.merge(idEstado);
            }
            for (TblHomologacion tblHomologacionListTblHomologacion : tblUniversidad.getTblHomologacionList()) {
                TblUniversidad oldUniversidadOrigenOfTblHomologacionListTblHomologacion = tblHomologacionListTblHomologacion.getUniversidadOrigen();
                tblHomologacionListTblHomologacion.setUniversidadOrigen(tblUniversidad);
                tblHomologacionListTblHomologacion = em.merge(tblHomologacionListTblHomologacion);
                if (oldUniversidadOrigenOfTblHomologacionListTblHomologacion != null) {
                    oldUniversidadOrigenOfTblHomologacionListTblHomologacion.getTblHomologacionList().remove(tblHomologacionListTblHomologacion);
                    oldUniversidadOrigenOfTblHomologacionListTblHomologacion = em.merge(oldUniversidadOrigenOfTblHomologacionListTblHomologacion);
                }
            }
            for (TblHomologacion tblHomologacionList1TblHomologacion : tblUniversidad.getTblHomologacionList1()) {
                TblUniversidad oldUniversidadDestinoOfTblHomologacionList1TblHomologacion = tblHomologacionList1TblHomologacion.getUniversidadDestino();
                tblHomologacionList1TblHomologacion.setUniversidadDestino(tblUniversidad);
                tblHomologacionList1TblHomologacion = em.merge(tblHomologacionList1TblHomologacion);
                if (oldUniversidadDestinoOfTblHomologacionList1TblHomologacion != null) {
                    oldUniversidadDestinoOfTblHomologacionList1TblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1TblHomologacion);
                    oldUniversidadDestinoOfTblHomologacionList1TblHomologacion = em.merge(oldUniversidadDestinoOfTblHomologacionList1TblHomologacion);
                }
            }
            for (TblProgramas tblProgramasListTblProgramas : tblUniversidad.getTblProgramasList()) {
                TblUniversidad oldIdUniversidadOfTblProgramasListTblProgramas = tblProgramasListTblProgramas.getIdUniversidad();
                tblProgramasListTblProgramas.setIdUniversidad(tblUniversidad);
                tblProgramasListTblProgramas = em.merge(tblProgramasListTblProgramas);
                if (oldIdUniversidadOfTblProgramasListTblProgramas != null) {
                    oldIdUniversidadOfTblProgramasListTblProgramas.getTblProgramasList().remove(tblProgramasListTblProgramas);
                    oldIdUniversidadOfTblProgramasListTblProgramas = em.merge(oldIdUniversidadOfTblProgramasListTblProgramas);
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
     * Descripción: Método que permite editar información previamente guardada
     * @param tblUniversidad
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(TblUniversidad tblUniversidad) throws IllegalOrphanException,
            NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblUniversidad persistentTblUniversidad = em.find(TblUniversidad.class, tblUniversidad.getIdUniversidad());
            TblEstado idEstadoOld = persistentTblUniversidad.getIdEstado();
            TblEstado idEstadoNew = tblUniversidad.getIdEstado();
            List<TblHomologacion> tblHomologacionListOld = persistentTblUniversidad.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionListNew = tblUniversidad.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionList1Old = persistentTblUniversidad.getTblHomologacionList1();
            List<TblHomologacion> tblHomologacionList1New = tblUniversidad.getTblHomologacionList1();
            List<TblProgramas> tblProgramasListOld = persistentTblUniversidad.getTblProgramasList();
            List<TblProgramas> tblProgramasListNew = tblUniversidad.getTblProgramasList();
            List<String> illegalOrphanMessages = null;
            for (TblHomologacion tblHomologacionListOldTblHomologacion : tblHomologacionListOld) {
                if (!tblHomologacionListNew.contains(tblHomologacionListOldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionListOldTblHomologacion + " since its universidadOrigen field is not nullable.");
                }
            }
            for (TblHomologacion tblHomologacionList1OldTblHomologacion : tblHomologacionList1Old) {
                if (!tblHomologacionList1New.contains(tblHomologacionList1OldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionList1OldTblHomologacion + " since its universidadDestino field is not nullable.");
                }
            }
            for (TblProgramas tblProgramasListOldTblProgramas : tblProgramasListOld) {
                if (!tblProgramasListNew.contains(tblProgramasListOldTblProgramas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblProgramas " + tblProgramasListOldTblProgramas + " since its idUniversidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                tblUniversidad.setIdEstado(idEstadoNew);
            }
            List<TblHomologacion> attachedTblHomologacionListNew = new ArrayList<>();
            for (TblHomologacion tblHomologacionListNewTblHomologacionToAttach : tblHomologacionListNew) {
                tblHomologacionListNewTblHomologacionToAttach = em.getReference(tblHomologacionListNewTblHomologacionToAttach.getClass(), tblHomologacionListNewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionListNew.add(tblHomologacionListNewTblHomologacionToAttach);
            }
            tblHomologacionListNew = attachedTblHomologacionListNew;
            tblUniversidad.setTblHomologacionList(tblHomologacionListNew);
            List<TblHomologacion> attachedTblHomologacionList1New = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1NewTblHomologacionToAttach : tblHomologacionList1New) {
                tblHomologacionList1NewTblHomologacionToAttach = em.getReference(tblHomologacionList1NewTblHomologacionToAttach.getClass(), tblHomologacionList1NewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1New.add(tblHomologacionList1NewTblHomologacionToAttach);
            }
            tblHomologacionList1New = attachedTblHomologacionList1New;
            tblUniversidad.setTblHomologacionList1(tblHomologacionList1New);
            List<TblProgramas> attachedTblProgramasListNew = new ArrayList<>();
            for (TblProgramas tblProgramasListNewTblProgramasToAttach : tblProgramasListNew) {
                tblProgramasListNewTblProgramasToAttach = em.getReference(tblProgramasListNewTblProgramasToAttach.getClass(), tblProgramasListNewTblProgramasToAttach.getIdPrograma());
                attachedTblProgramasListNew.add(tblProgramasListNewTblProgramasToAttach);
            }
            tblProgramasListNew = attachedTblProgramasListNew;
            tblUniversidad.setTblProgramasList(tblProgramasListNew);
            tblUniversidad = em.merge(tblUniversidad);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getTblUniversidadList().remove(tblUniversidad);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getTblUniversidadList().add(tblUniversidad);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (TblHomologacion tblHomologacionListNewTblHomologacion : tblHomologacionListNew) {
                if (!tblHomologacionListOld.contains(tblHomologacionListNewTblHomologacion)) {
                    TblUniversidad oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion = tblHomologacionListNewTblHomologacion.getUniversidadOrigen();
                    tblHomologacionListNewTblHomologacion.setUniversidadOrigen(tblUniversidad);
                    tblHomologacionListNewTblHomologacion = em.merge(tblHomologacionListNewTblHomologacion);
                    if (oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion != null && !oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion.equals(tblUniversidad)) {
                        oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion.getTblHomologacionList().remove(tblHomologacionListNewTblHomologacion);
                        oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion = em.merge(oldUniversidadOrigenOfTblHomologacionListNewTblHomologacion);
                    }
                }
            }
            for (TblHomologacion tblHomologacionList1NewTblHomologacion : tblHomologacionList1New) {
                if (!tblHomologacionList1Old.contains(tblHomologacionList1NewTblHomologacion)) {
                    TblUniversidad oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion = tblHomologacionList1NewTblHomologacion.getUniversidadDestino();
                    tblHomologacionList1NewTblHomologacion.setUniversidadDestino(tblUniversidad);
                    tblHomologacionList1NewTblHomologacion = em.merge(tblHomologacionList1NewTblHomologacion);
                    if (oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion != null && !oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion.equals(tblUniversidad)) {
                        oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1NewTblHomologacion);
                        oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion = em.merge(oldUniversidadDestinoOfTblHomologacionList1NewTblHomologacion);
                    }
                }
            }
            for (TblProgramas tblProgramasListNewTblProgramas : tblProgramasListNew) {
                if (!tblProgramasListOld.contains(tblProgramasListNewTblProgramas)) {
                    TblUniversidad oldIdUniversidadOfTblProgramasListNewTblProgramas = tblProgramasListNewTblProgramas.getIdUniversidad();
                    tblProgramasListNewTblProgramas.setIdUniversidad(tblUniversidad);
                    tblProgramasListNewTblProgramas = em.merge(tblProgramasListNewTblProgramas);
                    if (oldIdUniversidadOfTblProgramasListNewTblProgramas != null && !oldIdUniversidadOfTblProgramasListNewTblProgramas.equals(tblUniversidad)) {
                        oldIdUniversidadOfTblProgramasListNewTblProgramas.getTblProgramasList().remove(tblProgramasListNewTblProgramas);
                        oldIdUniversidadOfTblProgramasListNewTblProgramas = em.merge(oldIdUniversidadOfTblProgramasListNewTblProgramas);
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
                Integer id = tblUniversidad.getIdUniversidad();
                if (findTblUniversidad(id) == null) {
                    throw new NonexistentEntityException("The tblUniversidad with id " + id + " no longer exists.");
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
     * Descripción: Método que permite eliminar información guardada
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
            TblUniversidad tblUniversidad;
            try {
                tblUniversidad = em.getReference(TblUniversidad.class, id);
                tblUniversidad.getIdUniversidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblUniversidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TblHomologacion> tblHomologacionListOrphanCheck = tblUniversidad.getTblHomologacionList();
            for (TblHomologacion tblHomologacionListOrphanCheckTblHomologacion : tblHomologacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblUniversidad (" + tblUniversidad + ") cannot be destroyed since the TblHomologacion " + tblHomologacionListOrphanCheckTblHomologacion + " in its tblHomologacionList field has a non-nullable universidadOrigen field.");
            }
            List<TblHomologacion> tblHomologacionList1OrphanCheck = tblUniversidad.getTblHomologacionList1();
            for (TblHomologacion tblHomologacionList1OrphanCheckTblHomologacion : tblHomologacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblUniversidad (" + tblUniversidad + ") cannot be destroyed since the TblHomologacion " + tblHomologacionList1OrphanCheckTblHomologacion + " in its tblHomologacionList1 field has a non-nullable universidadDestino field.");
            }
            List<TblProgramas> tblProgramasListOrphanCheck = tblUniversidad.getTblProgramasList();
            for (TblProgramas tblProgramasListOrphanCheckTblProgramas : tblProgramasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblUniversidad (" + tblUniversidad + ") cannot be destroyed since the TblProgramas " + tblProgramasListOrphanCheckTblProgramas + " in its tblProgramasList field has a non-nullable idUniversidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblEstado idEstado = tblUniversidad.getIdEstado();
            if (idEstado != null) {
                idEstado.getTblUniversidadList().remove(tblUniversidad);
                idEstado = em.merge(idEstado);
            }
            em.remove(tblUniversidad);
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
     * Descripción: Método que permite obtener un listado de información
     * @return 
     */
    public List<TblUniversidad> findTblUniversidadEntities() {
        return findTblUniversidadEntities(true, -1, -1);
    }

    /**
     * Descripción: Método que permite obtener un listado de información
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<TblUniversidad> findTblUniversidadEntities(int maxResults, int firstResult) {
        return findTblUniversidadEntities(false, maxResults, firstResult);
    }

    /**
     * Descripción: Método que permite listar información guardada
     * @param all
     * @param maxResults
     * @param firstResult
     * @return 
     */
    private List<TblUniversidad> findTblUniversidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblUniversidad.class));
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
    public TblUniversidad findTblUniversidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblUniversidad.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Descripción: Método que obtiene la cantidad de registos
     * @return 
     */
    public int getTblUniversidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblUniversidad> rt = cq.from(TblUniversidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
