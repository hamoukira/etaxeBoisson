/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Redevable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import controller.util.SearchUtil;
import java.util.ArrayList;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class RedevableFacade extends AbstractFacade<Redevable> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RedevableFacade() {
        super(Redevable.class);
    }

    public int saveRedevable(Redevable redevable) {
        if (redevable == null) {
            return -1;
        } else {
            create(redevable);
            return 1;
        }
    }

    public void clone(Redevable source, Redevable destination) {
        destination.setCin(source.getCin());
        destination.setDateDeCommencement(source.getDateDeCommencement());
        destination.setId(source.getId());
        destination.setMail(source.getMail());
        destination.setNature(source.getNature());
        destination.setNom(source.getNom());
        destination.setPrenom(source.getPrenom());
        destination.setRc(source.getRc());
    }

    public Redevable clone(Redevable redevable) {
        Redevable cloned = new Redevable();
        clone(redevable, cloned);
        return cloned;
    }

    public List<Redevable> findRedevable(int nature, String rc, String cin, String nom, String prenom) {
        String query = "SELECT r FROM Redevable r WHERE 1=1 ";
        query += SearchUtil.addConstraint("r", "nature", "=", nature);
        query += SearchUtil.addConstraint("r", "rc", "=", rc);
        query += SearchUtil.addConstraint("r", "cin", "=", cin);
        query += SearchUtil.addConstraint("r", "nom", "=", nom);
        query += SearchUtil.addConstraint("r", "prenom", "=", prenom);
        System.out.println("*********************************************************************************");
        System.out.println("this is Find Redevable :: QUERY :: " + query);
        System.out.println("*********************************************************************************");
        return em.createQuery(query).getResultList();
    }
//    public List<Redevable> findRedevableRc(int nature, String rc) {
//        return em.createQuery("SELECT re FROM Redevable re WHERE 1=1 AND re.nature="+nature+" AND re.rc='"+rc+"'").getResultList();
//    }

    public Redevable findRedevable(String propCode, String gerantCode) {
        List<Redevable> redevables;
        if (propCode != null && !"".equals(propCode)) {
            redevables = findRedevable(2, propCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = findRedevable(2, null, propCode, null, null);
            }
            if (!redevables.isEmpty()) {
                return redevables.get(0);
            }
        } else if (gerantCode != null && !"".equals(gerantCode)) {
            redevables = findRedevable(1, gerantCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = findRedevable(1, null, gerantCode, null, null);
            }
            if (!redevables.isEmpty()) {
                return redevables.get(0);
            }
        }
        return null;
    }

    public Redevable findPropritaire(String propCode) {
        List<Redevable> redevables;
        if (propCode != null && !"".equals(propCode)) {
            redevables = findRedevable(2, propCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = findRedevable(2, null, propCode, null, null);
            }
            if (!redevables.isEmpty()) {
                return redevables.get(0);
            }
        }
        return null;
    }

    public Redevable findGerant(String gerantCode) {
        List<Redevable> redevables;
        if (gerantCode != null && !"".equals(gerantCode)) {
            redevables = findRedevable(1, gerantCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = findRedevable(1, null, gerantCode, null, null);
            }
            if (!redevables.isEmpty()) {
                return redevables.get(0);
            }
        }
        return null;
    }

}
