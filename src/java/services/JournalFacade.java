/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Activite;
import bean.Journal;
import bean.Locale;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TauxRetardBoisonTrim;
import bean.TauxTaxeBoisson;
import bean.Userr;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import controller.util.SessionUtil;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class JournalFacade extends AbstractFacade<Journal> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @EJB
    private UserFacade userFacade;
    @EJB
    private LocaleFacade localeFacade;
    @EJB
    private TauxTaxeBoissonFacade tauxTaxeBoissonFacade;
    @EJB
    private TauxRetardBoisonTrimFacade tauxRetardBoisonTrimFacade;
    @EJB
    private ActiviteFacade activiteFacade;
    @EJB
    private QuartierFacade quartierFacade;
    @EJB
    private SecteurFacade secteurFacade;
    @EJB
    private RedevableFacade redevableFacade;
    @EJB
    private RueFacade rueFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JournalFacade() {
        super(Journal.class);
    }

    public void createJournal(Object entity, int action) {

        Journal journal = initJournal(action, entity);
        if (action == 1) {
            journal.setOldeValue(entity.toString());
            journal.setMessage("Non");
        }
        if (action == 2) {
            Object oldEntity = findOldObject(entity);
            journal.setMessage(GenerateMessage(entity, oldEntity));
            journal.setNewValue(entity.toString());
            journal.setOldeValue(oldEntity.toString());
        }
        create(journal);
    }

    public boolean recreate(Journal item) {
        boolean res = recreatTheItem(item);
        if (res) {
            remove(item);
        }
        return res;
    }

    private String GenerateMessage(Object entity, Object oldEntity) {
        String chngesMessage = "";
        Javers javers = JaversBuilder.javers().build();
        System.out.println("new :: entity :: " + entity.toString());
        System.out.println("old :: oldEntity :: " + oldEntity.toString());
        String d = javers.compare(entity, oldEntity).prettyPrint();
//        List<ValueChange> change = d.getChangesByType(ValueChange.class);
//        for (ValueChange valueChange : change) {
//            chngesMessage += valueChange.getPropertyName() + " avant :" + valueChange.getRight() + " apres :" + valueChange.getLeft() + ",\n";
//        }
        System.out.println("SaveEdit :: GenerateMessage :: chngesMessage :" + d);
        return d;
    }

    private Journal initJournal(int action, Object entity) {
        Journal journal = new Journal();
        journal.setTypeDaction(action);
        journal.setUserLogin(SessionUtil.getConnectedUser().getLogin());
        journal.setClassName(entity.getClass().getSimpleName());
        journal.setDateDeModification(LocalDateTime.now());
        return journal;
    }

    private boolean recreatTheItem(Journal item) {
        System.out.println(" recreatTheItem :: ");
        try {
            Class deletedObjectClass = Class.forName("bean." + item.getClassName());
            Object recreated = deletedObjectClass.newInstance();
            Gson gson = new Gson();
            if (recreated instanceof Userr) {
                recreatUser(gson, item);
            } else if (recreated instanceof Locale) {
                recreatLocal(gson, item);
            } else if (recreated instanceof Rue) {
                recreatRue(gson, item);
            } else if (recreated instanceof Quartier) {
                recreateQuartier(gson, item);
            } else if (recreated instanceof Activite) {
                recreateActivite(gson, item);
            } else if (recreated instanceof Secteur) {
                recreateSecteur(gson, item);
            } else if (recreated instanceof TauxTaxeBoisson) {
                recreateTauxTaxe(gson, item);
            } else if (recreated instanceof TauxRetardBoisonTrim) {
                recreateTauxRetard(gson, item);
            } else if (recreated instanceof Redevable) {
                recreateRedevable(gson, item);
            }
            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return false;
        }

    }

    public Object findOldObject(Object entity) {
        System.out.println("findId");
        if (entity instanceof Userr) {
            Userr user = (Userr) entity;
            return userFacade.find(user.getLogin());
        } else if (entity instanceof Locale) {
            Locale locale = (Locale) entity;
            return localeFacade.find(locale.getId());
        } else if (entity instanceof Rue) {
            Rue rue = (Rue) entity;
            return rueFacade.find(rue.getId());
        } else if (entity instanceof Quartier) {
            Quartier quartier = (Quartier) entity;
            return quartierFacade.find(quartier.getId());
        } else if (entity instanceof Activite) {
            Activite activite = (Activite) entity;
            return activiteFacade.find(activite.getId());
        } else if (entity instanceof Secteur) {
            Secteur secteure = (Secteur) entity;
            return secteurFacade.find(secteure.getId());
        } else if (entity instanceof TauxTaxeBoisson) {
            TauxTaxeBoisson tauxTaxeBoisson = (TauxTaxeBoisson) entity;
            return tauxTaxeBoissonFacade.find(tauxTaxeBoisson.getId());
        } else if (entity instanceof TauxRetardBoisonTrim) {
            TauxRetardBoisonTrim tauxRetardBoisonTrim = (TauxRetardBoisonTrim) entity;
            return tauxRetardBoisonTrimFacade.find(tauxRetardBoisonTrim.getId());
        } else if (entity instanceof Redevable) {
            Redevable redevable = (Redevable) entity;
            return redevableFacade.find(redevable.getId());
        } else {
            return null;
        }
    }

    public List<Journal> findByConditions(String userName, LocalDateTime dateMin, LocalDateTime dateMax, int action) {
        String sqlQuery = "SELECT j FROM Journal j WHERE 1=1 ";
        if (userName != null && !userName.isEmpty()) {
            sqlQuery += " AND j.user = :userName";
        }
        if (action != -1) {
            sqlQuery += " AND j.typeDaction = :actionType";
        }
        if (dateMin != null) {
            sqlQuery += " AND j.dateDeModification >= :dateMin";
        }
        if (dateMax != null) {
            sqlQuery += " AND j.dateDeModification <= :dateMax";
        }
        TypedQuery<Journal> query = getEntityManager().createQuery(sqlQuery, Journal.class);
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

    private void recreateRedevable(Gson gson, Journal item) throws JsonSyntaxException {
        Redevable redevable = gson.fromJson(item.getOldeValue(), Redevable.class);
        if (item.getTypeDaction() == 1) {
            redevableFacade.create(redevable);
        } else {
            redevableFacade.edit(redevable);
        }
    }

    private void recreateTauxRetard(Gson gson, Journal item) throws JsonSyntaxException {
        TauxRetardBoisonTrim tauxRetardBoisonTrim = gson.fromJson(item.getOldeValue(), TauxRetardBoisonTrim.class);
        if (item.getTypeDaction() == 1) {
            if (tauxTaxeBoissonFacade.find(tauxRetardBoisonTrim.getTauxBoissonTaxe().getId()) != null) {
                tauxRetardBoisonTrimFacade.create(tauxRetardBoisonTrim);
            }
        } else {
            tauxRetardBoisonTrimFacade.edit(tauxRetardBoisonTrim);
        }
    }

    private void recreateTauxTaxe(Gson gson, Journal item) throws JsonSyntaxException {
        TauxTaxeBoisson tauxTaxeBoisson = gson.fromJson(item.getOldeValue(), TauxTaxeBoisson.class);
        if (item.getTypeDaction() == 1) {
            if (activiteFacade.find(tauxTaxeBoisson.getActivite().getId()) != null) {
                tauxTaxeBoissonFacade.create(tauxTaxeBoisson);
            }
        } else {
            tauxTaxeBoissonFacade.edit(tauxTaxeBoisson);
        }
    }

    private void recreateSecteur(Gson gson, Journal item) throws JsonSyntaxException {
        Secteur secteur = gson.fromJson(item.getOldeValue(), Secteur.class);
        if (item.getTypeDaction() == 1) {
            secteurFacade.create(secteur);
        } else {
            secteurFacade.edit(secteur);
        }
    }

    private void recreateActivite(Gson gson, Journal item) throws JsonSyntaxException {
        Activite activite = gson.fromJson(item.getOldeValue(), Activite.class);
        if (item.getTypeDaction() == 1) {
            activiteFacade.create(activite);
        } else {
            activiteFacade.edit(activite);
        }
    }

    private void recreateQuartier(Gson gson, Journal item) throws JsonSyntaxException {
        Quartier quartier = gson.fromJson(item.getOldeValue(), Quartier.class);
        if (item.getTypeDaction() == 1) {
            quartierFacade.create(quartier);
        } else {
            quartierFacade.edit(quartier);
        }
    }

    private void recreatRue(Gson gson, Journal item) throws JsonSyntaxException {
        Rue rue = gson.fromJson(item.getOldeValue(), Rue.class);
        if (item.getTypeDaction() == 1) {
            rueFacade.create(rue);
        } else {
            rueFacade.edit(rue);
        }
    }

    private void recreatLocal(Gson gson, Journal item) throws JsonSyntaxException {
        Locale locale = gson.fromJson(item.getOldeValue(), Locale.class);
        if (item.getTypeDaction() == 1) {
            localeFacade.remove(locale);
        } else {
            localeFacade.edit(locale);
        }
    }

    private void recreatUser(Gson gson, Journal item) throws JsonSyntaxException {
        Userr userr = gson.fromJson(item.getOldeValue(), Userr.class);
        if (item.getTypeDaction() == 1) {
            userFacade.create(userr);
        } else {
            userFacade.edit(userr);
        }
    }
}
