/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.History;
import bean.Userr;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }

    public void createHistoryElement(Userr loadedUser, int type) {
        History connexionHistory = new History();
        connexionHistory.setUserLogin(loadedUser.getLogin());
        if (type == 1) {
            connexionHistory.setType(1);
            connexionHistory.setInOutTimeStamp(LocalDateTime.now());
        }
        if (type == 2) {
            connexionHistory.setType(2);
            connexionHistory.setInOutTimeStamp(LocalDateTime.now());
        }
        System.out.println("createHistoryElement :: "+connexionHistory);
        create(connexionHistory);
    }

    public List<History> findByConditions(String userName, LocalDateTime dateMin, LocalDateTime dateMax, int action) {
        String sqlQuery = "SELECT h FROM History h WHERE 1=1 ";
        if (userName != null && !userName.isEmpty()) {
            sqlQuery += " AND h.user = :userName";
        }
        if (action != -1) {
            sqlQuery += " AND h.type = :actionType";
        }
        if (dateMin != null) {
            sqlQuery += " AND h.inOutTimeStamp >= :dateMin";
        }
        if (dateMax != null) {
            sqlQuery += " AND h.inOutTimeStamp <= :dateMax";
        }
        TypedQuery<History> query = getEntityManager().createQuery(sqlQuery, History.class);
        if (sqlQuery.contains("userName")) {
            query.setParameter("userName", userName);
        }

        if (sqlQuery.contains("actionType")) {
            query.setParameter("actionType", action);
        }

        if (sqlQuery.contains("dateMin")) {
            query.setParameter("dateMin", dateMin);
        }

        if (sqlQuery.contains("dateMax")) {
            query.setParameter("dateMax", dateMax);
        }

        return query.getResultList();
    }
//    public List<History> findByConditions(String userName, LocalDateTime dateMin, LocalDateTime dateMax, int action) {
//        System.out.println("History :: findByConditions  :: ");
//        String query = "SELECT h FROM History h WHERE 1=1 ";
//        System.out.println("History :: findByConditions  :: query ::" + query);
//        if (userName != null && !userName.isEmpty()) {
//            System.out.println("userName :: " + userName);
//            query += SearchUtil.addConstraint("h", "user.login", "=", userName);
//        }
//        System.out.println("History :: findByConditions  :: query ::" + query);
//        if (action != -1) {
//            query += SearchUtil.addConstraint("h", "type", "=", action);
//        }
//        System.out.println("History :: findByConditions  :: query ::" + query);
//        query += SearchUtil.addConstraintMinMaxTimeStamp("h", "inOutTimeStamp", dateMin, dateMax);
//        System.out.println("History :: findByConditions  :: query ::" + query);
//        return getEntityManager().createQuery(query).getResultList();
//    }

}
