/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Activite;
import bean.TauxRetardBoisonTrim;
import bean.TauxTaxeBoisson;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class ActiviteFacade extends AbstractFacade<Activite> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActiviteFacade() {
        super(Activite.class);
    }

    @EJB
    private services.TauxRetardBoisonTrimFacade tauxRetardFacade;
    @EJB
    private services.TauxTaxeBoissonFacade tauxTaxeFacade;

    public int createActivite(Activite activite, TauxRetardBoisonTrim tauxRetard, TauxTaxeBoisson tauxTaxe) {
        try {
            Activite activiteInDB = findActiviteByName(activite.getNom());
            if (activiteInDB == null) {
                create(activite);
                tauxRetardFacade.create(tauxRetard);
                tauxTaxeFacade.create(tauxTaxe);
                return 1;
            } else {
                return -2;
            }
        } catch (Exception e) {
            return -1;
        }

    }

    private Activite findActiviteByName(String nom) {
        try {
            return (Activite) getEntityManager().createQuery("SELECT a FROM Activite a WHERE a.nom='" + nom + "'").getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public int removeActivite(Activite activite) {
        try {
            TauxTaxeBoisson tauxTaxeBoisson = tauxTaxeFacade.findTauxTaxeByActivity(activite);
            TauxRetardBoisonTrim tauxRetardBoisonTrim = tauxRetardFacade.findTauxRetardByTaux(tauxTaxeBoisson);
            tauxRetardFacade.remove(tauxRetardBoisonTrim);
            tauxTaxeFacade.remove(tauxTaxeBoisson);
            remove(activite);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

}
