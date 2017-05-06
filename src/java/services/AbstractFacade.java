/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.History;
import bean.Journal;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;

/**
 *
 * @author Lmarbouh Mhamùed
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    @EJB
    private JournalFacade journalFacade;

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void savedEdite(T entity) {
        System.out.println("savedEdite");
        journalFacade.createJournal(entity, 2);
        edit(entity);
        System.out.println("saveEdit :: oldEntity = null");

    }

    public void remove(T entity) {
        if (!(entity instanceof Journal) || !(entity instanceof History)) {
            journalFacade.createJournal(entity, 1);
        }
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        System.out.println("my Entity :: " + entityClass);
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public Long generateId(String beanName, String idName) {
        List<Long> maxId = getEntityManager().createQuery(" Select max(item." + idName + ") FROM " + beanName + " item").getResultList();
        if (maxId == null || maxId.isEmpty() || maxId.get(0) == null) {
            return 1L;
        }
        return maxId.get(0) + 1;
    }

}
