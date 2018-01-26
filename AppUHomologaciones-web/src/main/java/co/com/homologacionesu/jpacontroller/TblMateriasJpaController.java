package co.com.homologacionesu.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.jpacontroller.exceptions.IllegalOrphanException;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Objetivo: Administrar operaciones para las materias
 * @author Daniel Serna
 */
public class TblMateriasJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param emf 
     */
    public TblMateriasJpaController(EntityManagerFactory emf) {
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
     * Descripción: Método que permite crear un registro en base de datos
     * @param tblMaterias
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(TblMaterias tblMaterias) throws RollbackFailureException, Exception {
        if (tblMaterias.getTblHomologacionList() == null) {
            tblMaterias.setTblHomologacionList(new ArrayList<TblHomologacion>());
        }
        if (tblMaterias.getTblHomologacionList1() == null) {
            tblMaterias.setTblHomologacionList1(new ArrayList<TblHomologacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado idEstado = tblMaterias.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                tblMaterias.setIdEstado(idEstado);
            }
            TblProgramas idPrograma = tblMaterias.getIdPrograma();
            if (idPrograma != null) {
                idPrograma = em.getReference(idPrograma.getClass(), idPrograma.getIdPrograma());
                tblMaterias.setIdPrograma(idPrograma);
            }
            List<TblHomologacion> attachedTblHomologacionList = new ArrayList<>();
            for (TblHomologacion tblHomologacionListTblHomologacionToAttach : tblMaterias.getTblHomologacionList()) {
                tblHomologacionListTblHomologacionToAttach = em.getReference(tblHomologacionListTblHomologacionToAttach.getClass(), tblHomologacionListTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList.add(tblHomologacionListTblHomologacionToAttach);
            }
            tblMaterias.setTblHomologacionList(attachedTblHomologacionList);
            List<TblHomologacion> attachedTblHomologacionList1 = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1TblHomologacionToAttach : tblMaterias.getTblHomologacionList1()) {
                tblHomologacionList1TblHomologacionToAttach = em.getReference(tblHomologacionList1TblHomologacionToAttach.getClass(), tblHomologacionList1TblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1.add(tblHomologacionList1TblHomologacionToAttach);
            }
            tblMaterias.setTblHomologacionList1(attachedTblHomologacionList1);
            em.persist(tblMaterias);
            if (idEstado != null) {
                idEstado.getTblMateriasList().add(tblMaterias);
                idEstado = em.merge(idEstado);
            }
            if (idPrograma != null) {
                idPrograma.getTblMateriasList().add(tblMaterias);
                idPrograma = em.merge(idPrograma);
            }
            for (TblHomologacion tblHomologacionListTblHomologacion : tblMaterias.getTblHomologacionList()) {
                TblMaterias oldMateriaOrigenOfTblHomologacionListTblHomologacion = tblHomologacionListTblHomologacion.getMateriaOrigen();
                tblHomologacionListTblHomologacion.setMateriaOrigen(tblMaterias);
                tblHomologacionListTblHomologacion = em.merge(tblHomologacionListTblHomologacion);
                if (oldMateriaOrigenOfTblHomologacionListTblHomologacion != null) {
                    oldMateriaOrigenOfTblHomologacionListTblHomologacion.getTblHomologacionList().remove(tblHomologacionListTblHomologacion);
                    oldMateriaOrigenOfTblHomologacionListTblHomologacion = em.merge(oldMateriaOrigenOfTblHomologacionListTblHomologacion);
                }
            }
            for (TblHomologacion tblHomologacionList1TblHomologacion : tblMaterias.getTblHomologacionList1()) {
                TblMaterias oldMateriaDestinoOfTblHomologacionList1TblHomologacion = tblHomologacionList1TblHomologacion.getMateriaDestino();
                tblHomologacionList1TblHomologacion.setMateriaDestino(tblMaterias);
                tblHomologacionList1TblHomologacion = em.merge(tblHomologacionList1TblHomologacion);
                if (oldMateriaDestinoOfTblHomologacionList1TblHomologacion != null) {
                    oldMateriaDestinoOfTblHomologacionList1TblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1TblHomologacion);
                    oldMateriaDestinoOfTblHomologacionList1TblHomologacion = em.merge(oldMateriaDestinoOfTblHomologacionList1TblHomologacion);
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
     * Descripción: Método que permite modificar información
     * @param tblMaterias
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(TblMaterias tblMaterias) throws IllegalOrphanException, 
            NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblMaterias persistentTblMaterias = em.find(TblMaterias.class, tblMaterias.getIdMateria());
            TblEstado idEstadoOld = persistentTblMaterias.getIdEstado();
            TblEstado idEstadoNew = tblMaterias.getIdEstado();
            TblProgramas idProgramaOld = persistentTblMaterias.getIdPrograma();
            TblProgramas idProgramaNew = tblMaterias.getIdPrograma();
            List<TblHomologacion> tblHomologacionListOld = persistentTblMaterias.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionListNew = tblMaterias.getTblHomologacionList();
            List<TblHomologacion> tblHomologacionList1Old = persistentTblMaterias.getTblHomologacionList1();
            List<TblHomologacion> tblHomologacionList1New = tblMaterias.getTblHomologacionList1();
            List<String> illegalOrphanMessages = null;
            for (TblHomologacion tblHomologacionListOldTblHomologacion : tblHomologacionListOld) {
                if (!tblHomologacionListNew.contains(tblHomologacionListOldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionListOldTblHomologacion + " since its materiaOrigen field is not nullable.");
                }
            }
            for (TblHomologacion tblHomologacionList1OldTblHomologacion : tblHomologacionList1Old) {
                if (!tblHomologacionList1New.contains(tblHomologacionList1OldTblHomologacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblHomologacion " + tblHomologacionList1OldTblHomologacion + " since its materiaDestino field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                tblMaterias.setIdEstado(idEstadoNew);
            }
            if (idProgramaNew != null) {
                idProgramaNew = em.getReference(idProgramaNew.getClass(), idProgramaNew.getIdPrograma());
                tblMaterias.setIdPrograma(idProgramaNew);
            }
            List<TblHomologacion> attachedTblHomologacionListNew = new ArrayList<>();
            for (TblHomologacion tblHomologacionListNewTblHomologacionToAttach : tblHomologacionListNew) {
                tblHomologacionListNewTblHomologacionToAttach = em.getReference(tblHomologacionListNewTblHomologacionToAttach.getClass(), tblHomologacionListNewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionListNew.add(tblHomologacionListNewTblHomologacionToAttach);
            }
            tblHomologacionListNew = attachedTblHomologacionListNew;
            tblMaterias.setTblHomologacionList(tblHomologacionListNew);
            List<TblHomologacion> attachedTblHomologacionList1New = new ArrayList<>();
            for (TblHomologacion tblHomologacionList1NewTblHomologacionToAttach : tblHomologacionList1New) {
                tblHomologacionList1NewTblHomologacionToAttach = em.getReference(tblHomologacionList1NewTblHomologacionToAttach.getClass(), tblHomologacionList1NewTblHomologacionToAttach.getIdHomologa());
                attachedTblHomologacionList1New.add(tblHomologacionList1NewTblHomologacionToAttach);
            }
            tblHomologacionList1New = attachedTblHomologacionList1New;
            tblMaterias.setTblHomologacionList1(tblHomologacionList1New);
            tblMaterias = em.merge(tblMaterias);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getTblMateriasList().remove(tblMaterias);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getTblMateriasList().add(tblMaterias);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (idProgramaOld != null && !idProgramaOld.equals(idProgramaNew)) {
                idProgramaOld.getTblMateriasList().remove(tblMaterias);
                idProgramaOld = em.merge(idProgramaOld);
            }
            if (idProgramaNew != null && !idProgramaNew.equals(idProgramaOld)) {
                idProgramaNew.getTblMateriasList().add(tblMaterias);
                idProgramaNew = em.merge(idProgramaNew);
            }
            for (TblHomologacion tblHomologacionListNewTblHomologacion : tblHomologacionListNew) {
                if (!tblHomologacionListOld.contains(tblHomologacionListNewTblHomologacion)) {
                    TblMaterias oldMateriaOrigenOfTblHomologacionListNewTblHomologacion = tblHomologacionListNewTblHomologacion.getMateriaOrigen();
                    tblHomologacionListNewTblHomologacion.setMateriaOrigen(tblMaterias);
                    tblHomologacionListNewTblHomologacion = em.merge(tblHomologacionListNewTblHomologacion);
                    if (oldMateriaOrigenOfTblHomologacionListNewTblHomologacion != null && !oldMateriaOrigenOfTblHomologacionListNewTblHomologacion.equals(tblMaterias)) {
                        oldMateriaOrigenOfTblHomologacionListNewTblHomologacion.getTblHomologacionList().remove(tblHomologacionListNewTblHomologacion);
                        oldMateriaOrigenOfTblHomologacionListNewTblHomologacion = em.merge(oldMateriaOrigenOfTblHomologacionListNewTblHomologacion);
                    }
                }
            }
            for (TblHomologacion tblHomologacionList1NewTblHomologacion : tblHomologacionList1New) {
                if (!tblHomologacionList1Old.contains(tblHomologacionList1NewTblHomologacion)) {
                    TblMaterias oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion = tblHomologacionList1NewTblHomologacion.getMateriaDestino();
                    tblHomologacionList1NewTblHomologacion.setMateriaDestino(tblMaterias);
                    tblHomologacionList1NewTblHomologacion = em.merge(tblHomologacionList1NewTblHomologacion);
                    if (oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion != null && !oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion.equals(tblMaterias)) {
                        oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion.getTblHomologacionList1().remove(tblHomologacionList1NewTblHomologacion);
                        oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion = em.merge(oldMateriaDestinoOfTblHomologacionList1NewTblHomologacion);
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
                Integer id = tblMaterias.getIdMateria();
                if (findTblMaterias(id) == null) {
                    throw new NonexistentEntityException("The tblMaterias with id " + id + " no longer exists.");
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
     * Descripción: Método que permite eliminar información previamente guardada
     * @param id
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void destroy(Integer id) throws IllegalOrphanException, 
            NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblMaterias tblMaterias;
            try {
                tblMaterias = em.getReference(TblMaterias.class, id);
                tblMaterias.getIdMateria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblMaterias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TblHomologacion> tblHomologacionListOrphanCheck = tblMaterias.getTblHomologacionList();
            for (TblHomologacion tblHomologacionListOrphanCheckTblHomologacion : tblHomologacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblMaterias (" + tblMaterias + ") cannot be destroyed since the TblHomologacion " + tblHomologacionListOrphanCheckTblHomologacion + " in its tblHomologacionList field has a non-nullable materiaOrigen field.");
            }
            List<TblHomologacion> tblHomologacionList1OrphanCheck = tblMaterias.getTblHomologacionList1();
            for (TblHomologacion tblHomologacionList1OrphanCheckTblHomologacion : tblHomologacionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblMaterias (" + tblMaterias + ") cannot be destroyed since the TblHomologacion " + tblHomologacionList1OrphanCheckTblHomologacion + " in its tblHomologacionList1 field has a non-nullable materiaDestino field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblEstado idEstado = tblMaterias.getIdEstado();
            if (idEstado != null) {
                idEstado.getTblMateriasList().remove(tblMaterias);
                idEstado = em.merge(idEstado);
            }
            TblProgramas idPrograma = tblMaterias.getIdPrograma();
            if (idPrograma != null) {
                idPrograma.getTblMateriasList().remove(tblMaterias);
                idPrograma = em.merge(idPrograma);
            }
            em.remove(tblMaterias);
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
     * Descripción: Método que obtiene un listado de registros
     * @return 
     */
    public List<TblMaterias> findTblMateriasEntities() {
        return findTblMateriasEntities(true, -1, -1);
    }

    /**
     * Descripción: Método que devuelve una lista de registros
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<TblMaterias> findTblMateriasEntities(int maxResults, int firstResult) {
        return findTblMateriasEntities(false, maxResults, firstResult);
    }

    /**
     * Descripción: Método que devuelve un listado de información
     * @param all
     * @param maxResults
     * @param firstResult
     * @return 
     */
    private List<TblMaterias> findTblMateriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblMaterias.class));
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
     * Descripción: Método que devuelve un sólo objeto
     * @param id
     * @return 
     */
    public TblMaterias findTblMaterias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblMaterias.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Descripción: Método que obtiene la cantidad total de registros
     * @return 
     */
    public int getTblMateriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblMaterias> rt = cq.from(TblMaterias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Descripción: Método que permite consultar mediante la identificación de
     * programa
     * @param tblProgramas
     * @return 
     */
    public List<TblMaterias> consultarPorpPrograma(TblProgramas tblProgramas){
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("TblMaterias.findByPrograma");
        try {
            q.setParameter("idPrograma", tblProgramas);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
