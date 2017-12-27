/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.jpacontroller;

import co.com.homologacionesu.entidades.TblEstado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.homologacionesu.entidades.TblMaterias;
import java.util.ArrayList;
import java.util.List;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUsuario;
import co.com.homologacionesu.jpacontroller.exceptions.IllegalOrphanException;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.PreexistingEntityException;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author dsernama
 */
public class TblEstadoJpaController implements Serializable {

    private static final long serialVersionUID = 1L;

    public TblEstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TblEstado tblEstado) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (tblEstado.getTblMateriasList() == null) {
            tblEstado.setTblMateriasList(new ArrayList<TblMaterias>());
        }
        if (tblEstado.getTblRolesList() == null) {
            tblEstado.setTblRolesList(new ArrayList<TblRoles>());
        }
        if (tblEstado.getTblUniversidadList() == null) {
            tblEstado.setTblUniversidadList(new ArrayList<TblUniversidad>());
        }
        if (tblEstado.getTblProgramasList() == null) {
            tblEstado.setTblProgramasList(new ArrayList<TblProgramas>());
        }
        if (tblEstado.getTblUsuarioList() == null) {
            tblEstado.setTblUsuarioList(new ArrayList<TblUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TblMaterias> attachedTblMateriasList = new ArrayList<>();
            for (TblMaterias tblMateriasListTblMateriasToAttach : tblEstado.getTblMateriasList()) {
                tblMateriasListTblMateriasToAttach = em.getReference(tblMateriasListTblMateriasToAttach.getClass(), tblMateriasListTblMateriasToAttach.getIdMateria());
                attachedTblMateriasList.add(tblMateriasListTblMateriasToAttach);
            }
            tblEstado.setTblMateriasList(attachedTblMateriasList);
            List<TblRoles> attachedTblRolesList = new ArrayList<>();
            for (TblRoles tblRolesListTblRolesToAttach : tblEstado.getTblRolesList()) {
                tblRolesListTblRolesToAttach = em.getReference(tblRolesListTblRolesToAttach.getClass(), tblRolesListTblRolesToAttach.getCodRol());
                attachedTblRolesList.add(tblRolesListTblRolesToAttach);
            }
            tblEstado.setTblRolesList(attachedTblRolesList);
            List<TblUniversidad> attachedTblUniversidadList = new ArrayList<>();
            for (TblUniversidad tblUniversidadListTblUniversidadToAttach : tblEstado.getTblUniversidadList()) {
                tblUniversidadListTblUniversidadToAttach = em.getReference(tblUniversidadListTblUniversidadToAttach.getClass(), tblUniversidadListTblUniversidadToAttach.getIdUniversidad());
                attachedTblUniversidadList.add(tblUniversidadListTblUniversidadToAttach);
            }
            tblEstado.setTblUniversidadList(attachedTblUniversidadList);
            List<TblProgramas> attachedTblProgramasList = new ArrayList<>();
            for (TblProgramas tblProgramasListTblProgramasToAttach : tblEstado.getTblProgramasList()) {
                tblProgramasListTblProgramasToAttach = em.getReference(tblProgramasListTblProgramasToAttach.getClass(), tblProgramasListTblProgramasToAttach.getIdPrograma());
                attachedTblProgramasList.add(tblProgramasListTblProgramasToAttach);
            }
            tblEstado.setTblProgramasList(attachedTblProgramasList);
            List<TblUsuario> attachedTblUsuarioList = new ArrayList<>();
            for (TblUsuario tblUsuarioListTblUsuarioToAttach : tblEstado.getTblUsuarioList()) {
                tblUsuarioListTblUsuarioToAttach = em.getReference(tblUsuarioListTblUsuarioToAttach.getClass(), tblUsuarioListTblUsuarioToAttach.getUsuario());
                attachedTblUsuarioList.add(tblUsuarioListTblUsuarioToAttach);
            }
            tblEstado.setTblUsuarioList(attachedTblUsuarioList);
            em.persist(tblEstado);
            for (TblMaterias tblMateriasListTblMaterias : tblEstado.getTblMateriasList()) {
                TblEstado oldIdEstadoOfTblMateriasListTblMaterias = tblMateriasListTblMaterias.getIdEstado();
                tblMateriasListTblMaterias.setIdEstado(tblEstado);
                tblMateriasListTblMaterias = em.merge(tblMateriasListTblMaterias);
                if (oldIdEstadoOfTblMateriasListTblMaterias != null) {
                    oldIdEstadoOfTblMateriasListTblMaterias.getTblMateriasList().remove(tblMateriasListTblMaterias);
                    oldIdEstadoOfTblMateriasListTblMaterias = em.merge(oldIdEstadoOfTblMateriasListTblMaterias);
                }
            }
            for (TblRoles tblRolesListTblRoles : tblEstado.getTblRolesList()) {
                TblEstado oldIdEstadoOfTblRolesListTblRoles = tblRolesListTblRoles.getIdEstado();
                tblRolesListTblRoles.setIdEstado(tblEstado);
                tblRolesListTblRoles = em.merge(tblRolesListTblRoles);
                if (oldIdEstadoOfTblRolesListTblRoles != null) {
                    oldIdEstadoOfTblRolesListTblRoles.getTblRolesList().remove(tblRolesListTblRoles);
                    oldIdEstadoOfTblRolesListTblRoles = em.merge(oldIdEstadoOfTblRolesListTblRoles);
                }
            }
            for (TblUniversidad tblUniversidadListTblUniversidad : tblEstado.getTblUniversidadList()) {
                TblEstado oldIdEstadoOfTblUniversidadListTblUniversidad = tblUniversidadListTblUniversidad.getIdEstado();
                tblUniversidadListTblUniversidad.setIdEstado(tblEstado);
                tblUniversidadListTblUniversidad = em.merge(tblUniversidadListTblUniversidad);
                if (oldIdEstadoOfTblUniversidadListTblUniversidad != null) {
                    oldIdEstadoOfTblUniversidadListTblUniversidad.getTblUniversidadList().remove(tblUniversidadListTblUniversidad);
                    oldIdEstadoOfTblUniversidadListTblUniversidad = em.merge(oldIdEstadoOfTblUniversidadListTblUniversidad);
                }
            }
            for (TblProgramas tblProgramasListTblProgramas : tblEstado.getTblProgramasList()) {
                TblEstado oldIdEstadoOfTblProgramasListTblProgramas = tblProgramasListTblProgramas.getIdEstado();
                tblProgramasListTblProgramas.setIdEstado(tblEstado);
                tblProgramasListTblProgramas = em.merge(tblProgramasListTblProgramas);
                if (oldIdEstadoOfTblProgramasListTblProgramas != null) {
                    oldIdEstadoOfTblProgramasListTblProgramas.getTblProgramasList().remove(tblProgramasListTblProgramas);
                    oldIdEstadoOfTblProgramasListTblProgramas = em.merge(oldIdEstadoOfTblProgramasListTblProgramas);
                }
            }
            for (TblUsuario tblUsuarioListTblUsuario : tblEstado.getTblUsuarioList()) {
                TblEstado oldIdEstadoOfTblUsuarioListTblUsuario = tblUsuarioListTblUsuario.getIdEstado();
                tblUsuarioListTblUsuario.setIdEstado(tblEstado);
                tblUsuarioListTblUsuario = em.merge(tblUsuarioListTblUsuario);
                if (oldIdEstadoOfTblUsuarioListTblUsuario != null) {
                    oldIdEstadoOfTblUsuarioListTblUsuario.getTblUsuarioList().remove(tblUsuarioListTblUsuario);
                    oldIdEstadoOfTblUsuarioListTblUsuario = em.merge(oldIdEstadoOfTblUsuarioListTblUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTblEstado(tblEstado.getIdEstado()) != null) {
                throw new PreexistingEntityException("TblEstado " + tblEstado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TblEstado tblEstado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TblEstado persistentTblEstado = em.find(TblEstado.class, tblEstado.getIdEstado());
            List<TblMaterias> tblMateriasListOld = persistentTblEstado.getTblMateriasList();
            List<TblMaterias> tblMateriasListNew = tblEstado.getTblMateriasList();
            List<TblRoles> tblRolesListOld = persistentTblEstado.getTblRolesList();
            List<TblRoles> tblRolesListNew = tblEstado.getTblRolesList();
            List<TblUniversidad> tblUniversidadListOld = persistentTblEstado.getTblUniversidadList();
            List<TblUniversidad> tblUniversidadListNew = tblEstado.getTblUniversidadList();
            List<TblProgramas> tblProgramasListOld = persistentTblEstado.getTblProgramasList();
            List<TblProgramas> tblProgramasListNew = tblEstado.getTblProgramasList();
            List<TblUsuario> tblUsuarioListOld = persistentTblEstado.getTblUsuarioList();
            List<TblUsuario> tblUsuarioListNew = tblEstado.getTblUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (TblMaterias tblMateriasListOldTblMaterias : tblMateriasListOld) {
                if (!tblMateriasListNew.contains(tblMateriasListOldTblMaterias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblMaterias " + tblMateriasListOldTblMaterias + " since its idEstado field is not nullable.");
                }
            }
            for (TblRoles tblRolesListOldTblRoles : tblRolesListOld) {
                if (!tblRolesListNew.contains(tblRolesListOldTblRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblRoles " + tblRolesListOldTblRoles + " since its idEstado field is not nullable.");
                }
            }
            for (TblUniversidad tblUniversidadListOldTblUniversidad : tblUniversidadListOld) {
                if (!tblUniversidadListNew.contains(tblUniversidadListOldTblUniversidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblUniversidad " + tblUniversidadListOldTblUniversidad + " since its idEstado field is not nullable.");
                }
            }
            for (TblProgramas tblProgramasListOldTblProgramas : tblProgramasListOld) {
                if (!tblProgramasListNew.contains(tblProgramasListOldTblProgramas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblProgramas " + tblProgramasListOldTblProgramas + " since its idEstado field is not nullable.");
                }
            }
            for (TblUsuario tblUsuarioListOldTblUsuario : tblUsuarioListOld) {
                if (!tblUsuarioListNew.contains(tblUsuarioListOldTblUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<>();
                    }
                    illegalOrphanMessages.add("You must retain TblUsuario " + tblUsuarioListOldTblUsuario + " since its idEstado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TblMaterias> attachedTblMateriasListNew = new ArrayList<>();
            for (TblMaterias tblMateriasListNewTblMateriasToAttach : tblMateriasListNew) {
                tblMateriasListNewTblMateriasToAttach = em.getReference(tblMateriasListNewTblMateriasToAttach.getClass(), tblMateriasListNewTblMateriasToAttach.getIdMateria());
                attachedTblMateriasListNew.add(tblMateriasListNewTblMateriasToAttach);
            }
            tblMateriasListNew = attachedTblMateriasListNew;
            tblEstado.setTblMateriasList(tblMateriasListNew);
            List<TblRoles> attachedTblRolesListNew = new ArrayList<>();
            for (TblRoles tblRolesListNewTblRolesToAttach : tblRolesListNew) {
                tblRolesListNewTblRolesToAttach = em.getReference(tblRolesListNewTblRolesToAttach.getClass(), tblRolesListNewTblRolesToAttach.getCodRol());
                attachedTblRolesListNew.add(tblRolesListNewTblRolesToAttach);
            }
            tblRolesListNew = attachedTblRolesListNew;
            tblEstado.setTblRolesList(tblRolesListNew);
            List<TblUniversidad> attachedTblUniversidadListNew = new ArrayList<>();
            for (TblUniversidad tblUniversidadListNewTblUniversidadToAttach : tblUniversidadListNew) {
                tblUniversidadListNewTblUniversidadToAttach = em.getReference(tblUniversidadListNewTblUniversidadToAttach.getClass(), tblUniversidadListNewTblUniversidadToAttach.getIdUniversidad());
                attachedTblUniversidadListNew.add(tblUniversidadListNewTblUniversidadToAttach);
            }
            tblUniversidadListNew = attachedTblUniversidadListNew;
            tblEstado.setTblUniversidadList(tblUniversidadListNew);
            List<TblProgramas> attachedTblProgramasListNew = new ArrayList<>();
            for (TblProgramas tblProgramasListNewTblProgramasToAttach : tblProgramasListNew) {
                tblProgramasListNewTblProgramasToAttach = em.getReference(tblProgramasListNewTblProgramasToAttach.getClass(), tblProgramasListNewTblProgramasToAttach.getIdPrograma());
                attachedTblProgramasListNew.add(tblProgramasListNewTblProgramasToAttach);
            }
            tblProgramasListNew = attachedTblProgramasListNew;
            tblEstado.setTblProgramasList(tblProgramasListNew);
            List<TblUsuario> attachedTblUsuarioListNew = new ArrayList<>();
            for (TblUsuario tblUsuarioListNewTblUsuarioToAttach : tblUsuarioListNew) {
                tblUsuarioListNewTblUsuarioToAttach = em.getReference(tblUsuarioListNewTblUsuarioToAttach.getClass(), tblUsuarioListNewTblUsuarioToAttach.getUsuario());
                attachedTblUsuarioListNew.add(tblUsuarioListNewTblUsuarioToAttach);
            }
            tblUsuarioListNew = attachedTblUsuarioListNew;
            tblEstado.setTblUsuarioList(tblUsuarioListNew);
            tblEstado = em.merge(tblEstado);
            for (TblMaterias tblMateriasListNewTblMaterias : tblMateriasListNew) {
                if (!tblMateriasListOld.contains(tblMateriasListNewTblMaterias)) {
                    TblEstado oldIdEstadoOfTblMateriasListNewTblMaterias = tblMateriasListNewTblMaterias.getIdEstado();
                    tblMateriasListNewTblMaterias.setIdEstado(tblEstado);
                    tblMateriasListNewTblMaterias = em.merge(tblMateriasListNewTblMaterias);
                    if (oldIdEstadoOfTblMateriasListNewTblMaterias != null && !oldIdEstadoOfTblMateriasListNewTblMaterias.equals(tblEstado)) {
                        oldIdEstadoOfTblMateriasListNewTblMaterias.getTblMateriasList().remove(tblMateriasListNewTblMaterias);
                        oldIdEstadoOfTblMateriasListNewTblMaterias = em.merge(oldIdEstadoOfTblMateriasListNewTblMaterias);
                    }
                }
            }
            for (TblRoles tblRolesListNewTblRoles : tblRolesListNew) {
                if (!tblRolesListOld.contains(tblRolesListNewTblRoles)) {
                    TblEstado oldIdEstadoOfTblRolesListNewTblRoles = tblRolesListNewTblRoles.getIdEstado();
                    tblRolesListNewTblRoles.setIdEstado(tblEstado);
                    tblRolesListNewTblRoles = em.merge(tblRolesListNewTblRoles);
                    if (oldIdEstadoOfTblRolesListNewTblRoles != null && !oldIdEstadoOfTblRolesListNewTblRoles.equals(tblEstado)) {
                        oldIdEstadoOfTblRolesListNewTblRoles.getTblRolesList().remove(tblRolesListNewTblRoles);
                        oldIdEstadoOfTblRolesListNewTblRoles = em.merge(oldIdEstadoOfTblRolesListNewTblRoles);
                    }
                }
            }
            for (TblUniversidad tblUniversidadListNewTblUniversidad : tblUniversidadListNew) {
                if (!tblUniversidadListOld.contains(tblUniversidadListNewTblUniversidad)) {
                    TblEstado oldIdEstadoOfTblUniversidadListNewTblUniversidad = tblUniversidadListNewTblUniversidad.getIdEstado();
                    tblUniversidadListNewTblUniversidad.setIdEstado(tblEstado);
                    tblUniversidadListNewTblUniversidad = em.merge(tblUniversidadListNewTblUniversidad);
                    if (oldIdEstadoOfTblUniversidadListNewTblUniversidad != null && !oldIdEstadoOfTblUniversidadListNewTblUniversidad.equals(tblEstado)) {
                        oldIdEstadoOfTblUniversidadListNewTblUniversidad.getTblUniversidadList().remove(tblUniversidadListNewTblUniversidad);
                        oldIdEstadoOfTblUniversidadListNewTblUniversidad = em.merge(oldIdEstadoOfTblUniversidadListNewTblUniversidad);
                    }
                }
            }
            for (TblProgramas tblProgramasListNewTblProgramas : tblProgramasListNew) {
                if (!tblProgramasListOld.contains(tblProgramasListNewTblProgramas)) {
                    TblEstado oldIdEstadoOfTblProgramasListNewTblProgramas = tblProgramasListNewTblProgramas.getIdEstado();
                    tblProgramasListNewTblProgramas.setIdEstado(tblEstado);
                    tblProgramasListNewTblProgramas = em.merge(tblProgramasListNewTblProgramas);
                    if (oldIdEstadoOfTblProgramasListNewTblProgramas != null && !oldIdEstadoOfTblProgramasListNewTblProgramas.equals(tblEstado)) {
                        oldIdEstadoOfTblProgramasListNewTblProgramas.getTblProgramasList().remove(tblProgramasListNewTblProgramas);
                        oldIdEstadoOfTblProgramasListNewTblProgramas = em.merge(oldIdEstadoOfTblProgramasListNewTblProgramas);
                    }
                }
            }
            for (TblUsuario tblUsuarioListNewTblUsuario : tblUsuarioListNew) {
                if (!tblUsuarioListOld.contains(tblUsuarioListNewTblUsuario)) {
                    TblEstado oldIdEstadoOfTblUsuarioListNewTblUsuario = tblUsuarioListNewTblUsuario.getIdEstado();
                    tblUsuarioListNewTblUsuario.setIdEstado(tblEstado);
                    tblUsuarioListNewTblUsuario = em.merge(tblUsuarioListNewTblUsuario);
                    if (oldIdEstadoOfTblUsuarioListNewTblUsuario != null && !oldIdEstadoOfTblUsuarioListNewTblUsuario.equals(tblEstado)) {
                        oldIdEstadoOfTblUsuarioListNewTblUsuario.getTblUsuarioList().remove(tblUsuarioListNewTblUsuario);
                        oldIdEstadoOfTblUsuarioListNewTblUsuario = em.merge(oldIdEstadoOfTblUsuarioListNewTblUsuario);
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
                Long id = tblEstado.getIdEstado();
                if (findTblEstado(id) == null) {
                    throw new NonexistentEntityException("The tblEstado with id " + id + " no longer exists.");
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
            TblEstado tblEstado;
            try {
                tblEstado = em.getReference(TblEstado.class, id);
                tblEstado.getIdEstado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tblEstado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TblMaterias> tblMateriasListOrphanCheck = tblEstado.getTblMateriasList();
            for (TblMaterias tblMateriasListOrphanCheckTblMaterias : tblMateriasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblEstado (" + tblEstado + ") cannot be destroyed since the TblMaterias " + tblMateriasListOrphanCheckTblMaterias + " in its tblMateriasList field has a non-nullable idEstado field.");
            }
            List<TblRoles> tblRolesListOrphanCheck = tblEstado.getTblRolesList();
            for (TblRoles tblRolesListOrphanCheckTblRoles : tblRolesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblEstado (" + tblEstado + ") cannot be destroyed since the TblRoles " + tblRolesListOrphanCheckTblRoles + " in its tblRolesList field has a non-nullable idEstado field.");
            }
            List<TblUniversidad> tblUniversidadListOrphanCheck = tblEstado.getTblUniversidadList();
            for (TblUniversidad tblUniversidadListOrphanCheckTblUniversidad : tblUniversidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblEstado (" + tblEstado + ") cannot be destroyed since the TblUniversidad " + tblUniversidadListOrphanCheckTblUniversidad + " in its tblUniversidadList field has a non-nullable idEstado field.");
            }
            List<TblProgramas> tblProgramasListOrphanCheck = tblEstado.getTblProgramasList();
            for (TblProgramas tblProgramasListOrphanCheckTblProgramas : tblProgramasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblEstado (" + tblEstado + ") cannot be destroyed since the TblProgramas " + tblProgramasListOrphanCheckTblProgramas + " in its tblProgramasList field has a non-nullable idEstado field.");
            }
            List<TblUsuario> tblUsuarioListOrphanCheck = tblEstado.getTblUsuarioList();
            for (TblUsuario tblUsuarioListOrphanCheckTblUsuario : tblUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<>();
                }
                illegalOrphanMessages.add("This TblEstado (" + tblEstado + ") cannot be destroyed since the TblUsuario " + tblUsuarioListOrphanCheckTblUsuario + " in its tblUsuarioList field has a non-nullable idEstado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tblEstado);
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

    public List<TblEstado> findTblEstadoEntities() {
        return findTblEstadoEntities(true, -1, -1);
    }

    public List<TblEstado> findTblEstadoEntities(int maxResults, int firstResult) {
        return findTblEstadoEntities(false, maxResults, firstResult);
    }

    private List<TblEstado> findTblEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TblEstado.class));
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

    public TblEstado findTblEstado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TblEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTblEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TblEstado> rt = cq.from(TblEstado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
