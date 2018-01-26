package co.com.homologacionesu.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.entidades.TblUsuario;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.PreexistingEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Objtetivo: Administrar las operaciones para la tabla usuarios
 * @author Daniel Serna
 */
public class TblUsuarioJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param emf 
     */
    public TblUsuarioJpaController(EntityManagerFactory emf) {
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
     * @param tblUsuario
     * @throws PreexistingEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void create(TblUsuario tblUsuario) throws PreexistingEntityException, 
            RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado idEstado = tblUsuario.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                tblUsuario.setIdEstado(idEstado);
            }
            TblRoles codRol = tblUsuario.getCodRol();
            if (codRol != null) {
                codRol = em.getReference(codRol.getClass(), codRol.getCodRol());
                tblUsuario.setCodRol(codRol);
            }
            em.persist(tblUsuario);
            if (idEstado != null) {
                idEstado.getTblUsuarioList().add(tblUsuario);
                idEstado = em.merge(idEstado);
            }
            if (codRol != null) {
                codRol.getTblUsuarioList().add(tblUsuario);
                codRol = em.merge(codRol);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblUsuario(tblUsuario.getUsuario()) != null) {
                throw new PreexistingEntityException("TblUsuario " + tblUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Descripción: Método que permite editar información guardada
     * @param tblUsuario
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void edit(TblUsuario tblUsuario) throws NonexistentEntityException, 
            RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblUsuario persistentTblUsuario = em.find(TblUsuario.class, tblUsuario.getUsuario());
            TblEstado idEstadoOld = persistentTblUsuario.getIdEstado();
            TblEstado idEstadoNew = tblUsuario.getIdEstado();
            TblRoles codRolOld = persistentTblUsuario.getCodRol();
            TblRoles codRolNew = tblUsuario.getCodRol();
            if (idEstadoNew != null) {
                idEstadoNew = em.getReference(idEstadoNew.getClass(), idEstadoNew.getIdEstado());
                tblUsuario.setIdEstado(idEstadoNew);
            }
            if (codRolNew != null) {
                codRolNew = em.getReference(codRolNew.getClass(), codRolNew.getCodRol());
                tblUsuario.setCodRol(codRolNew);
            }
            tblUsuario = em.merge(tblUsuario);
            if (idEstadoOld != null && !idEstadoOld.equals(idEstadoNew)) {
                idEstadoOld.getTblUsuarioList().remove(tblUsuario);
                idEstadoOld = em.merge(idEstadoOld);
            }
            if (idEstadoNew != null && !idEstadoNew.equals(idEstadoOld)) {
                idEstadoNew.getTblUsuarioList().add(tblUsuario);
                idEstadoNew = em.merge(idEstadoNew);
            }
            if (codRolOld != null && !codRolOld.equals(codRolNew)) {
                codRolOld.getTblUsuarioList().remove(tblUsuario);
                codRolOld = em.merge(codRolOld);
            }
            if (codRolNew != null && !codRolNew.equals(codRolOld)) {
                codRolNew.getTblUsuarioList().add(tblUsuario);
                codRolNew = em.merge(codRolNew);
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
                String id = tblUsuario.getUsuario();
                if (findTblUsuario(id) == null) {
                    throw new NonexistentEntityException("The tblUsuario with id " + id + " no longer exists.");
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
     * @throws NonexistentEntityException
     * @throws RollbackFailureException
     * @throws Exception 
     */
    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblUsuario tblUsuario;
            try {
                tblUsuario = em.getReference(TblUsuario.class, id);
                tblUsuario.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblUsuario with id " + id + " no longer exists.", enfe);
            }
            TblEstado idEstado = tblUsuario.getIdEstado();
            if (idEstado != null) {
                idEstado.getTblUsuarioList().remove(tblUsuario);
                idEstado = em.merge(idEstado);
            }
            TblRoles codRol = tblUsuario.getCodRol();
            if (codRol != null) {
                codRol.getTblUsuarioList().remove(tblUsuario);
                codRol = em.merge(codRol);
            }
            em.remove(tblUsuario);
            em.getTransaction().commit();
        } catch (NonexistentEntityException ex) {
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
     * Descripción: Método que obtiene un listado de información
     * @return 
     */
    public List<TblUsuario> findTblUsuarioEntities() {
        return findTblUsuarioEntities(true, -1, -1);
    }

    /**
     * Descripción: Método que obtiene un listado de información
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List<TblUsuario> findTblUsuarioEntities(int maxResults, int firstResult) {
        return findTblUsuarioEntities(false, maxResults, firstResult);
    }

    /**
     * Descripción: Método que obtiene un listado de información
     * @param all
     * @param maxResults
     * @param firstResult
     * @return 
     */
    private List<TblUsuario> findTblUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblUsuario.class));
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
     * Descripción: Método que permite obtener un sólo registro
     * @param id
     * @return 
     */
    public TblUsuario findTblUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblUsuario.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Descripción: Método que retorna la cantidad de registros
     * @return 
     */
    public int getTblUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblUsuario> rt = cq.from(TblUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    /**
     * Descripción: Método que permite obtener un registro a partir del usuario 
     * y contraseña
     * @param usuario
     * @param clave
     * @return 
     */
    public TblUsuario findTblUsuario(String usuario, String clave) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("TblUsuario.findByUsuarioContrasena");
        try {
            q.setParameter("usuario", usuario);
            q.setParameter("contrasena", clave);
            return (TblUsuario)q.getSingleResult();
        } finally {
            em.close();
        }
    }
    
}
