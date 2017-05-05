/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Commune;
import bean.Locale;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TaxeAnnuelBoisson;
import bean.TaxeTrimBoisson;
import controller.util.FrenchNumberToWords;
import controller.util.PdfUtil;
import controller.util.SearchUtil;
import controller.util.SessionUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class TaxeAnnuelBoissonFacade extends AbstractFacade<TaxeAnnuelBoisson> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeAnnuelBoissonFacade() {
        super(TaxeAnnuelBoisson.class);
    }

    @EJB
    TaxeTrimBoissonFacade taxeTrimFacade;

    public TaxeAnnuelBoisson findTaxeAnnuelByYearAndLocale(int year, Locale locale) throws NoResultException {
        return (TaxeAnnuelBoisson) em.createQuery("SELECT ta FROM TaxeAnnuelBoisson ta WHERE ta.annee=" + year + " AND ta.locale.id=" + locale.getId()).getSingleResult();
    }

    //1:success -1:annee paye -2:trimester payee
    public Object[] getTaxeAnnuel(Locale locale, int year, int trim) {
        System.out.println("hani f :: getTaxeAnnuel");
        Object[] respons;
        System.out.println(" 1 ");
        try {
            System.out.println("2");
            TaxeAnnuelBoisson taxeAnnuelBoisson = findTaxeAnnuelByYearAndLocale(year, locale);
            System.out.println(" 3 :: " + taxeAnnuelBoisson);
//            if (taxeAnnuelBoisson != null) {
            System.out.println(" if 4");
            respons = verifieTaxeTrimOfYear(taxeAnnuelBoisson, trim);
//            } else {
            System.out.println("else 5");
//                
//                System.out.println("else 6");
//            }
        } catch (NoResultException e) {
            System.out.println("exception 6");
            respons = new Object[]{createTaxeAnnuel(year, locale), 1};
//            return respons;
        }

        System.out.println(" 7 response :: " + respons);
        return respons;
    }

    private Object[] verifieTaxeTrimOfYear(TaxeAnnuelBoisson taxeAnnuelBoisson, int trim) {
        System.out.println("hani f :: verifieTaxeTrimOfYear");
        List<TaxeTrimBoisson> trimTaxes = taxeTrimFacade.findByTaxeAnnuel(taxeAnnuelBoisson);
        System.out.println(" 1 trimTaxes :: ");
        Object[] respons = new Object[]{taxeAnnuelBoisson, 1};
        if (taxeAnnuelBoisson.getFinished() < 1 && trimTaxes != null) {
            if (trimTaxes.size() == 4) {
                taxeAnnuelBoisson.setFinished(1);
                respons = new Object[]{null, -1};
            } else if (trimTaxes.size() < 4) {
                taxeAnnuelBoisson.setFinished(-1);
                for (TaxeTrimBoisson trimTaxe : trimTaxes) {
                    if (trimTaxe.getNumeroTrim() == trim) {
                        respons = new Object[]{null, -2};
                    }
                }
            }
        }
        edit(taxeAnnuelBoisson);
        return respons;
    }

    private TaxeAnnuelBoisson createTaxeAnnuel(int year, Locale locale) {
        TaxeAnnuelBoisson taxeAnnuelBoisson = new TaxeAnnuelBoisson();
        taxeAnnuelBoisson.setAnnee(year);
        taxeAnnuelBoisson.setLocale(locale);
        if (locale.getPropriete() != null) {
            taxeAnnuelBoisson.setRedevable(locale.getPropriete());
        }
        if (locale.getGerant() != null) {
            taxeAnnuelBoisson.setRedevable(locale.getGerant());
        }
        taxeAnnuelBoisson.setId(generateId("TaxeAnnuelBoisson", "id"));
        create(taxeAnnuelBoisson);
        return taxeAnnuelBoisson;
    }

    public List<TaxeAnnuelBoisson> findByCriteria(String anneeMax, String anneeMin, Rue rue, Quartier quartier, Commune commune, Secteur secteur, Date dateMin, Date dateMax, int trimMin, int triMax, int paiement, Redevable redevable) {
        System.out.println("facade :: findByCriteria ::");
        String query = "SELECT tax FROM TaxeAnnuelBoisson tax where 1=1 ";

        if (anneeMax != null && !"".equals(anneeMax)) {
            query += SearchUtil.addConstraint("tax", "annee", "<=", anneeMax);
        }
        if (anneeMin != null && !"".equals(anneeMin)) {
            query += SearchUtil.addConstraint("tax", "annee", ">=", anneeMin);
        }
        if (redevable != null) {
            query += SearchUtil.addConstraint("tax", "redevable.id", "=", redevable.getId());
        }
        if (paiement != 0) {
            query += SearchUtil.addConstraint("tax", "finished", "=", paiement);
        }
        if (rue == null) {
            if (quartier == null) {
                if (secteur == null) {
                    if (commune != null) {
                        query += SearchUtil.addConstraint("tax.local", "rue.quartier.secteur.commune.id", "=", commune.getId());
                    }
                } else {
                    query += SearchUtil.addConstraint("tax.local", "rue.quartier.secteur.id", "=", secteur.getId());
                }
            } else {
                query += SearchUtil.addConstraint("tax.local", "rue.quartier.id", "=", quartier.getId());
            }
        } else {
            query += SearchUtil.addConstraint("tax.local", "rue.id", "=", rue.getId());

        }
        System.out.println("facade :: findByCriteria :: Querry :: " + query);
        return em.createQuery(query).getResultList();
    }

    public void printPdf(TaxeAnnuelBoisson taxeAnnuelBoisson) throws JRException, IOException {
        List myList = taxeTrimFacade.findByTaxeAnnuel(taxeAnnuelBoisson);
        if (myList != null) {
            Map<String, Object> params = prepareParams(taxeAnnuelBoisson);
            PdfUtil.generatePdf(myList, params, "bordereauAnnuel" + taxeAnnuelBoisson.getId() + ".pdf", "jasper/taxeAnnuelleRapport.jasper");
        }
    }

    private Map<String, Object> prepareParams(TaxeAnnuelBoisson taxeAnnuelBoisson) {
        String nature;
        String status = "NoN";
        String cinOuRcRedevable;
        String adresse = taxeAnnuelBoisson.getLocale().getRue().getQuartier().getSecteur().getName() + " "
                + taxeAnnuelBoisson.getLocale().getRue().getQuartier().getName() + " "
                + taxeAnnuelBoisson.getLocale().getRue().getName() + " " + taxeAnnuelBoisson.getLocale().getComplementAdress();
        if (taxeAnnuelBoisson.getRedevable().getNature() == 1) {
            nature = "Gerant";
        } else {
            nature = "proprietaire";
        }
        if (taxeAnnuelBoisson.getRedevable().getCin() != null || !taxeAnnuelBoisson.getRedevable().getCin().equals("")) {
            cinOuRcRedevable = taxeAnnuelBoisson.getRedevable().getCin();
        } else {
            cinOuRcRedevable = taxeAnnuelBoisson.getRedevable().getRc();
        }

        if (taxeAnnuelBoisson.getFinished() == 1) {
            status = "Terminer";
        } else if (taxeAnnuelBoisson.getFinished() == -1 || taxeAnnuelBoisson.getFinished() == 0) {
            status = "Non Termine";
        }

        Map<String, Object> params = new HashMap();
        params.put("redevableName", taxeAnnuelBoisson.getRedevable().getNom());
        params.put("activite", taxeAnnuelBoisson.getLocale().getTypeLocal().getNom());
        params.put("taxYear", taxeAnnuelBoisson.getAnnee());
        params.put("montantAnnuel", taxeAnnuelBoisson.getMontantTaxeannuel());
        params.put("idRedevable", taxeAnnuelBoisson.getRedevable().getId());
        params.put("cinOrRcRedevable", cinOuRcRedevable);
        params.put("nomLocale", taxeAnnuelBoisson.getLocale().getNom());
        params.put("natureRedevable", nature);
        params.put("adresseLocale", adresse);
        params.put("idTaxeAnn", taxeAnnuelBoisson.getId());
        params.put("totalEnLettre", FrenchNumberToWords.convert(taxeAnnuelBoisson.getMontantTaxeannuel()));
        params.put("status", status);
        params.put("userName", SessionUtil.getConnectedUser().getNom());
        return params;
    }

}
