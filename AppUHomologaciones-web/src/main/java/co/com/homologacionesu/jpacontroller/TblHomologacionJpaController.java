/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.jpacontroller;

import co.com.homologacionesu.entidades.TblHomologacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.PreexistingEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author dsernama
 */
public class TblHomologacionJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public TblHomologacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblHomologacion tblHomologacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblMaterias materiaOrigen = tblHomologacion.getMateriaOrigen();
            if (materiaOrigen != null) {
                materiaOrigen = em.getReference(materiaOrigen.getClass(), materiaOrigen.getIdMateria());
                tblHomologacion.setMateriaOrigen(materiaOrigen);
            }
            TblMaterias materiaDestino = tblHomologacion.getMateriaDestino();
            if (materiaDestino != null) {
                materiaDestino = em.getReference(materiaDestino.getClass(), materiaDestino.getIdMateria());
                tblHomologacion.setMateriaDestino(materiaDestino);
            }
            TblProgramas programaDestino = tblHomologacion.getProgramaDestino();
            if (programaDestino != null) {
                programaDestino = em.getReference(programaDestino.getClass(), programaDestino.getIdPrograma());
                tblHomologacion.setProgramaDestino(programaDestino);
            }
            TblProgramas programaOrigen = tblHomologacion.getProgramaOrigen();
            if (programaOrigen != null) {
                programaOrigen = em.getReference(programaOrigen.getClass(), programaOrigen.getIdPrograma());
                tblHomologacion.setProgramaOrigen(programaOrigen);
            }
            TblUniversidad universidadOrigen = tblHomologacion.getUniversidadOrigen();
            if (universidadOrigen != null) {
                universidadOrigen = em.getReference(universidadOrigen.getClass(), universidadOrigen.getIdUniversidad());
                tblHomologacion.setUniversidadOrigen(universidadOrigen);
            }
            TblUniversidad universidadDestino = tblHomologacion.getUniversidadDestino();
            if (universidadDestino != null) {
                universidadDestino = em.getReference(universidadDestino.getClass(), universidadDestino.getIdUniversidad());
                tblHomologacion.setUniversidadDestino(universidadDestino);
            }
            em.persist(tblHomologacion);
            if (materiaOrigen != null) {
                materiaOrigen.getTblHomologacionList().add(tblHomologacion);
                materiaOrigen = em.merge(materiaOrigen);
            }
            if (materiaDestino != null) {
                materiaDestino.getTblHomologacionList().add(tblHomologacion);
                materiaDestino = em.merge(materiaDestino);
            }
            if (programaDestino != null) {
                programaDestino.getTblHomologacionList().add(tblHomologacion);
                programaDestino = em.merge(programaDestino);
            }
            if (programaOrigen != null) {
                programaOrigen.getTblHomologacionList().add(tblHomologacion);
                programaOrigen = em.merge(programaOrigen);
            }
            if (universidadOrigen != null) {
                universidadOrigen.getTblHomologacionList().add(tblHomologacion);
                universidadOrigen = em.merge(universidadOrigen);
            }
            if (universidadDestino != null) {
                universidadDestino.getTblHomologacionList().add(tblHomologacion);
                universidadDestino = em.merge(universidadDestino);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblHomologacion(tblHomologacion.getIdHomologa()) != null) {
                throw new PreexistingEntityException("TblHomologacion " + tblHomologacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblHomologacion tblHomologacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblHomologacion persistentTblHomologacion = em.find(TblHomologacion.class, tblHomologacion.getIdHomologa());
            TblMaterias materiaOrigenOld = persistentTblHomologacion.getMateriaOrigen();
            TblMaterias materiaOrigenNew = tblHomologacion.getMateriaOrigen();
            TblMaterias materiaDestinoOld = persistentTblHomologacion.getMateriaDestino();
            TblMaterias materiaDestinoNew = tblHomologacion.getMateriaDestino();
            TblProgramas programaDestinoOld = persistentTblHomologacion.getProgramaDestino();
            TblProgramas programaDestinoNew = tblHomologacion.getProgramaDestino();
            TblProgramas programaOrigenOld = persistentTblHomologacion.getProgramaOrigen();
            TblProgramas programaOrigenNew = tblHomologacion.getProgramaOrigen();
            TblUniversidad universidadOrigenOld = persistentTblHomologacion.getUniversidadOrigen();
            TblUniversidad universidadOrigenNew = tblHomologacion.getUniversidadOrigen();
            TblUniversidad universidadDestinoOld = persistentTblHomologacion.getUniversidadDestino();
            TblUniversidad universidadDestinoNew = tblHomologacion.getUniversidadDestino();
            if (materiaOrigenNew != null) {
                materiaOrigenNew = em.getReference(materiaOrigenNew.getClass(), materiaOrigenNew.getIdMateria());
                tblHomologacion.setMateriaOrigen(materiaOrigenNew);
            }
            if (materiaDestinoNew != null) {
                materiaDestinoNew = em.getReference(materiaDestinoNew.getClass(), materiaDestinoNew.getIdMateria());
                tblHomologacion.setMateriaDestino(materiaDestinoNew);
            }
            if (programaDestinoNew != null) {
                programaDestinoNew = em.getReference(programaDestinoNew.getClass(), programaDestinoNew.getIdPrograma());
                tblHomologacion.setProgramaDestino(programaDestinoNew);
            }
            if (programaOrigenNew != null) {
                programaOrigenNew = em.getReference(programaOrigenNew.getClass(), programaOrigenNew.getIdPrograma());
                tblHomologacion.setProgramaOrigen(programaOrigenNew);
            }
            if (universidadOrigenNew != null) {
                universidadOrigenNew = em.getReference(universidadOrigenNew.getClass(), universidadOrigenNew.getIdUniversidad());
                tblHomologacion.setUniversidadOrigen(universidadOrigenNew);
            }
            if (universidadDestinoNew != null) {
                universidadDestinoNew = em.getReference(universidadDestinoNew.getClass(), universidadDestinoNew.getIdUniversidad());
                tblHomologacion.setUniversidadDestino(universidadDestinoNew);
            }
            tblHomologacion = em.merge(tblHomologacion);
            if (materiaOrigenOld != null && !materiaOrigenOld.equals(materiaOrigenNew)) {
                materiaOrigenOld.getTblHomologacionList().remove(tblHomologacion);
                materiaOrigenOld = em.merge(materiaOrigenOld);
            }
            if (materiaOrigenNew != null && !materiaOrigenNew.equals(materiaOrigenOld)) {
                materiaOrigenNew.getTblHomologacionList().add(tblHomologacion);
                materiaOrigenNew = em.merge(materiaOrigenNew);
            }
            if (materiaDestinoOld != null && !materiaDestinoOld.equals(materiaDestinoNew)) {
                materiaDestinoOld.getTblHomologacionList().remove(tblHomologacion);
                materiaDestinoOld = em.merge(materiaDestinoOld);
            }
            if (materiaDestinoNew != null && !materiaDestinoNew.equals(materiaDestinoOld)) {
                materiaDestinoNew.getTblHomologacionList().add(tblHomologacion);
                materiaDestinoNew = em.merge(materiaDestinoNew);
            }
            if (programaDestinoOld != null && !programaDestinoOld.equals(programaDestinoNew)) {
                programaDestinoOld.getTblHomologacionList().remove(tblHomologacion);
                programaDestinoOld = em.merge(programaDestinoOld);
            }
            if (programaDestinoNew != null && !programaDestinoNew.equals(programaDestinoOld)) {
                programaDestinoNew.getTblHomologacionList().add(tblHomologacion);
                programaDestinoNew = em.merge(programaDestinoNew);
            }
            if (programaOrigenOld != null && !programaOrigenOld.equals(programaOrigenNew)) {
                programaOrigenOld.getTblHomologacionList().remove(tblHomologacion);
                programaOrigenOld = em.merge(programaOrigenOld);
            }
            if (programaOrigenNew != null && !programaOrigenNew.equals(programaOrigenOld)) {
                programaOrigenNew.getTblHomologacionList().add(tblHomologacion);
                programaOrigenNew = em.merge(programaOrigenNew);
            }
            if (universidadOrigenOld != null && !universidadOrigenOld.equals(universidadOrigenNew)) {
                universidadOrigenOld.getTblHomologacionList().remove(tblHomologacion);
                universidadOrigenOld = em.merge(universidadOrigenOld);
            }
            if (universidadOrigenNew != null && !universidadOrigenNew.equals(universidadOrigenOld)) {
                universidadOrigenNew.getTblHomologacionList().add(tblHomologacion);
                universidadOrigenNew = em.merge(universidadOrigenNew);
            }
            if (universidadDestinoOld != null && !universidadDestinoOld.equals(universidadDestinoNew)) {
                universidadDestinoOld.getTblHomologacionList().remove(tblHomologacion);
                universidadDestinoOld = em.merge(universidadDestinoOld);
            }
            if (universidadDestinoNew != null && !universidadDestinoNew.equals(universidadDestinoOld)) {
                universidadDestinoNew.getTblHomologacionList().add(tblHomologacion);
                universidadDestinoNew = em.merge(universidadDestinoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tblHomologacion.getIdHomologa();
                if (findTblHomologacion(id) == null) {
                    throw new NonexistentEntityException("The tblHomologacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblHomologacion tblHomologacion;
            try {
                tblHomologacion = em.getReference(TblHomologacion.class, id);
                tblHomologacion.getIdHomologa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblHomologacion with id " + id + " no longer exists.", enfe);
            }
            TblMaterias materiaOrigen = tblHomologacion.getMateriaOrigen();
            if (materiaOrigen != null) {
                materiaOrigen.getTblHomologacionList().remove(tblHomologacion);
                materiaOrigen = em.merge(materiaOrigen);
            }
            TblMaterias materiaDestino = tblHomologacion.getMateriaDestino();
            if (materiaDestino != null) {
                materiaDestino.getTblHomologacionList().remove(tblHomologacion);
                materiaDestino = em.merge(materiaDestino);
            }
            TblProgramas programaDestino = tblHomologacion.getProgramaDestino();
            if (programaDestino != null) {
                programaDestino.getTblHomologacionList().remove(tblHomologacion);
                programaDestino = em.merge(programaDestino);
            }
            TblProgramas programaOrigen = tblHomologacion.getProgramaOrigen();
            if (programaOrigen != null) {
                programaOrigen.getTblHomologacionList().remove(tblHomologacion);
                programaOrigen = em.merge(programaOrigen);
            }
            TblUniversidad universidadOrigen = tblHomologacion.getUniversidadOrigen();
            if (universidadOrigen != null) {
                universidadOrigen.getTblHomologacionList().remove(tblHomologacion);
                universidadOrigen = em.merge(universidadOrigen);
            }
            TblUniversidad universidadDestino = tblHomologacion.getUniversidadDestino();
            if (universidadDestino != null) {
                universidadDestino.getTblHomologacionList().remove(tblHomologacion);
                universidadDestino = em.merge(universidadDestino);
            }
            em.remove(tblHomologacion);
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

    public List<TblHomologacion> findTblHomologacionEntities() {
        return findTblHomologacionEntities(true, -1, -1);
    }

    public List<TblHomologacion> findTblHomologacionEntities(int maxResults, int firstResult) {
        return findTblHomologacionEntities(false, maxResults, firstResult);
    }

    private List<TblHomologacion> findTblHomologacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblHomologacion.class));
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

    public TblHomologacion findTblHomologacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblHomologacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblHomologacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblHomologacion> rt = cq.from(TblHomologacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<TblHomologacion> findTblHomologacionEntitiesBusqueda(boolean all, int maxResults, int firstResult, TblHomologacion datosBuqueda) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblHomologacion.class));
            Query q;
            q = em.createNamedQuery("TblHomologacion.findByUniversidaCarrera");
            q.setParameter("universidadOrigen", datosBuqueda.getUniversidadOrigen());
            q.setParameter("universidadDestino", datosBuqueda.getUniversidadDestino());
            q.setParameter("programaOrigen", datosBuqueda.getProgramaOrigen());
            q.setParameter("programaDestino", datosBuqueda.getProgramaDestino());
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
