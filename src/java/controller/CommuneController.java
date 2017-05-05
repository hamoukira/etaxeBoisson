package controller;

import bean.Commune;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.CommuneFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("communeController")
@SessionScoped
public class CommuneController implements Serializable {

    @EJB
    private services.CommuneFacade ejbFacade;
    @EJB
    private services.SecteurFacade secteurFacade;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;

    private List<Commune> items = null;
    private Commune selected;
    private List<Secteur> secteurs;
    private List<Quartier> quartiers;
    private List<Rue> rues;
    private Commune thisCommun ;
    private Secteur thisSecteur ;
    private Quartier thisQuartier;
    private Secteur secteurToCreat ;
    private Quartier quartierToCreat;
    private Rue thisRue = null;


    public Secteur getSecteurToCreat() {
        if(secteurToCreat==null)
            secteurToCreat=new Secteur();
        return secteurToCreat;
    }

    public void setSecteurToCreat(Secteur secteurToCreat) {
        this.secteurToCreat = secteurToCreat;
    }

    public Quartier getQuartierToCreat() {
        if(quartierToCreat==null)
            quartierToCreat=new Quartier();
        return quartierToCreat;
    }

    public void setQuartierToCreat(Quartier quartierToCreat) {
        this.quartierToCreat = quartierToCreat;
    }

    
    

    public Rue getThisRue() {
        if (thisRue == null) {
            thisRue = new Rue();
        }
        return thisRue;
    }

    public void setThisRue(Rue thisRue) {
        this.thisRue = thisRue;
    }

    public Commune getSelected() {
        if (selected == null) {
            selected = new Commune();
        }
        return selected;
    }

    public void setSelected(Commune selected) {
        this.selected = selected;
    }

    public Commune getThisCommun() {
        if (thisCommun == null) {
            thisCommun = new Commune();
        }
        return thisCommun;
    }

    public void setThisCommun(Commune thisCommun) {
        this.thisCommun = thisCommun;
    }

    public Secteur getThisSecteur() {
        if (thisSecteur == null) {
            thisSecteur = new Secteur();
        }
        return thisSecteur;
    }

    public void setThisSecteur(Secteur thisSecteur) {
        this.thisSecteur = thisSecteur;
    }

    public Quartier getThisQuartier() {
        if (thisQuartier == null) {
            thisQuartier = new Quartier();
        }
        return thisQuartier;
    }

    public void setThisQuartier(Quartier thisQuartier) {
        this.thisQuartier = thisQuartier;
    }
    public List<Commune> getCommunesAvailableSelectOne() {
        return ejbFacade.findAll();
    }

    public List<Secteur> getSecteurs() {
        if (secteurs == null) {
            secteurs = new ArrayList<>();
        }
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Quartier> getQuartiers() {
        if (quartiers == null) {
            quartiers = new ArrayList<>();
        }
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public List<Rue> getRues() {
        if (rues == null) {
            rues = new ArrayList<>();
        }
        return rues;
    }

    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }


    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CommuneFacade getFacade() {
        return ejbFacade;
    }

    
    public void secteureByCommun(Commune commun) {
        thisCommun = commun;
        setSecteurs(secteurFacade.findSecteureByCommun(commun));
        setQuartiers(new ArrayList<Quartier>());//pour enisialiser si la commune et changer
        setRues(new ArrayList<Rue>());//appel avec get pour eviter le cas ou ThisSecteur et ThisQyartie son null

    }

    public void quartierBySecteure(Secteur secteur) {
        thisSecteur = secteur;
        setQuartiers(quartierFacade.findBySecteur(secteur));
        setRues(new ArrayList<Rue>());

    }

    public void rueByQuartier(Quartier quartier) {
        thisQuartier = quartier;
        setRues(rueFacade.findByQuartier(quartier));
    }

    public void prepareCreate() {
        selected = new Commune();
        initializeEmbeddableKey();
//        return selected;
    }

    public void prepareCreateSecteur() {
        secteurToCreat = new Secteur();
        secteurToCreat.setCommune(thisCommun);
    }

    public void prepareCreateRue() {
        thisRue = new Rue();
        thisRue.setQuartier(thisQuartier);
    }

    public void prepareCreateQuartier() {
        quartierToCreat = new Quartier();
        quartierToCreat.setSecteur(thisSecteur);
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommuneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            if (items != null) {
                items.add(ejbFacade.clone(selected));
            }
        }
    }

    public void createSecteur() {
        secteurFacade.create(secteurToCreat);
        secteurs.add(secteurFacade.clone(secteurToCreat));
        JsfUtil.addSuccessMessage("Secteure: " + secteurToCreat.getName() + " de la commune: " + secteurToCreat.getCommune().getName());
        secteurToCreat = null;
    }

    public void createQuartier() {
        quartierFacade.create(quartierToCreat);
        quartiers.add(quartierFacade.clone(quartierToCreat));
        JsfUtil.addSuccessMessage("Quartier: " + quartierToCreat.getName() + " du Secteure: " + quartierToCreat.getSecteur().getName());
        quartierToCreat = null;
    }

    public void createRue() {
        rueFacade.create(thisRue);
        rues.add(rueFacade.clone(thisRue));
        JsfUtil.addSuccessMessage("Rue: " + thisRue.getName() + " du Quartier: " + thisRue.getQuartier().getName());
        thisRue = null;
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommuneUpdated"));
        items = null;
    }
//
//    public void destroy() {
//        
//        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommuneDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }

    public List<Commune> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                } else if (persistAction == PersistAction.UPDATE) {
                    getFacade().savedEdite(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Commune getCommune(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Commune> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Commune> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Commune.class)
    public static class CommuneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommuneController controller = (CommuneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "communeController");
            return controller.getCommune(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Commune) {
                Commune o = (Commune) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Commune.class.getName()});
                return null;
            }
        }

    }

}
