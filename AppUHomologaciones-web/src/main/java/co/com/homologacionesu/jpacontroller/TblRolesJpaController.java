/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.entidades.TblUsuario;
import co.com.homologacionesu.jpacontroller.exceptions.IllegalOrphanException;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.PreexistingEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dsernama
 */
public class TblRolesJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public TblRolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblRoles tblRoles) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblRoles.getTblUsuarioList() == null) {
            tblRoles.setTblUsuarioList(new ArrayList<TblUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado idEstado = tblRoles.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                tblRoles.setIdEstado(idEstado);
            }
            List<TblUsuario> attachedTblUsuarioList = new ArrayList<>();
            for (TblUsuario tblUsuarioListTblUsuarioToAttach : tblRoles.getTblUsuarioList()) {
                tblUsuarioListTblUsuarioToAttach = em.getReference(tblUsuarioListTblUsuarioToAttach.getClass(), tblUsuarioListTblUsuarioToAttach.getUsuario());
                attachedTblUsuarioList.add(tblUsuarioListTblUsuarioToAttach);
            }
            tblRoles.setTblUsuarioList(attachedTblUsuarioList);
            em.persist(tblRoles);
            if (idEstado != null) {
                idEstado.getTblRolesList().add(tblRoles);
                idEstado = em.merge(idEstado);
            }
            for (TblUsuario tblUsuarioListTblUsuario : tblRoles.getTblUsuarioList()) {
                TblRoles oldCodRolOfTblUsuarioListTblUsuario = tblUsuarioListTblUsuario.getCodRol();
                tblUsuarioListTblUsuario.setCodRol(tblRoles);
                tblUsuarioListTblUsuario = em.merge(tblUsuarioListTblUsuario);
                if (oldCodRolOfTblUsuarioListTblUsuario != null) {
                    oldCodRolOfTblUsuarioListTblUsuario.getTblUsuarioList().remove(tblUsuarioListTblUsuario);
                    oldCodRolOfTblUsuarioListTblUsuario = em.merge(oldCodRolOfTblUsuarioListTblUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblRoles(tblRoles.getCodRol()) != null) {
                throw new PreexistingEntityException("TblRoles " + tblRoles + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblRoles tblRoles) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblRoles persistentTblRoles = em.find(TblRoles.class, tblRoles.getCodRol());
            TblEstado idEstadoOld = persistentTblRoles.getIdEstado();
            TblEstado idEstadoNew = tblRoles.getIdEstado();
            List<TblUsuario> tblUsuarioListOld = persistentTblRoles.getTblUsuarioList();
            List<TblUsuario> tblUsuarioListNew = tblRoles.getTblUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (TblUsuario tblUsuarioListOldTblUsuario : tblUsuarioListOld) {
                if (!tblUsuarioListNew.contains(tblUsuarioListOldTblUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblUsuario " + tblUsuarioListOldTblUsuario + " since its codRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                tblRoles.setIdEstado(idEstadoNew);
            }
            List<TblUsuario> attachedTblUsuarioListNew = new ArrayList<>();
            for (TblUsuario tblUsuarioListNewTblUsuarioToAttach : tblUsuarioListNew) {
                tblUsuarioListNewTblUsuarioToAttach = em.getReference(tblUsuarioListNewTblUsuarioToAttach.getClass(), tblUsuarioListNewTblUsuarioToAttach.getUsuario());
                attachedTblUsuarioListNew.add(tblUsuarioListNewTblUsuarioToAttach);
            }
            tblUsuarioListNew = attachedTblUsuarioListNew;
            tblRoles.setTblUsuarioList(tblUsuarioListNew);
            tblRoles = em.merge(tblRoles);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getTblRolesList().remove(tblRoles);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getTblRolesList().add(tblRoles);
                idEstadoNew = em.merge(idEstadoNew);
            }
            for (TblUsuario tblUsuarioListNewTblUsuario : tblUsuarioListNew) {
                if (!tblUsuarioListOld.contains(tblUsuarioListNewTblUsuario)) {
                    TblRoles oldCodRolOfTblUsuarioListNewTblUsuario = tblUsuarioListNewTblUsuario.getCodRol();
                    tblUsuarioListNewTblUsuario.setCodRol(tblRoles);
                    tblUsuarioListNewTblUsuario = em.merge(tblUsuarioListNewTblUsuario);
                    if (oldCodRolOfTblUsuarioListNewTblUsuario != null && !oldCodRolOfTblUsuarioListNewTblUsuario.equals(tblRoles)) {
                        oldCodRolOfTblUsuarioListNewTblUsuario.getTblUsuarioList().remove(tblUsuarioListNewTblUsuario);
                        oldCodRolOfTblUsuarioListNewTblUsuario = em.merge(oldCodRolOfTblUsuarioListNewTblUsuario);
                    }
                }
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
                Long id = tblRoles.getCodRol();
                if (findTblRoles(id) == null) {
                    throw new NonexistentEntityException("The tblRoles with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblRoles tblRoles;
            try {
                tblRoles = em.getReference(TblRoles.class, id);
                tblRoles.getCodRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblRoles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TblUsuario> tblUsuarioListOrphanCheck = tblRoles.getTblUsuarioList();
            for (TblUsuario tblUsuarioListOrphanCheckTblUsuario : tblUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TblRoles (" + tblRoles + ") cannot be destroyed since the TblUsuario " + tblUsuarioListOrphanCheckTblUsuario + " in its tblUsuarioList field has a non-nullable codRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TblEstado idEstado = tblRoles.getIdEstado();
            if (idEstado != null) {
                idEstado.getTblRolesList().remove(tblRoles);
                idEstado = em.merge(idEstado);
            }
            em.remove(tblRoles);
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

    public List<TblRoles> findTblRolesEntities() {
        return findTblRolesEntities(true, -1, -1);
    }

    public List<TblRoles> findTblRolesEntities(int maxResults, int firstResult) {
        return findTblRolesEntities(false, maxResults, firstResult);
    }

    private List<TblRoles> findTblRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblRoles.class));
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

    public TblRoles findTblRoles(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblRoles.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblRoles> rt = cq.from(TblRoles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
