package controller;

import bean.Commune;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TaxeAnnuelBoisson;
import bean.TaxeTrimBoisson;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import services.TaxeAnnuelBoissonFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named("taxeAnnuelBoissonController")
@SessionScoped
public class TaxeAnnuelBoissonController implements Serializable {

    @EJB
    private services.TaxeAnnuelBoissonFacade ejbFacade;
    @EJB
    private services.RedevableFacade redevableFacade;
    @EJB
    private services.TaxeTrimBoissonFacade taxeTrimBoissonFacade;
    @EJB
    private services.CommuneFacade ejbFacadeC;
    @EJB
    private services.SecteurFacade secteurFacade;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;
//    @EJB
//    private services.UserFacade userFacade;

    private TaxeTrimBoisson selectedTaxe;
    private List<TaxeTrimBoisson> yearlyTaxeTrims;
    private TaxeTrimBoissonController taxeTrimBoissonController;
    private Rue rue;
    private Quartier thisQuartier;
    private Secteur thisSecteur;
    private Commune thisCommune;
    private List<Quartier> quartiers;
    private List<Secteur> secteurs;
    private List<Commune> communes;
    private List<Rue> rues;
    private List<TaxeAnnuelBoisson> items;
    private TaxeAnnuelBoisson selected;
    private Redevable redevable;
    private int paiement = 0;
    private int trimMin;
    private int trimMax;
    private String anneeMax;
    private String anneeMin;
    private Date dateMax;
    private Date dateMin;
    private String gerantCode;
    private String propCode;

    public TaxeAnnuelBoissonController() {
    }

    public String getGerantCode() {
        return gerantCode;
    }

    public void setGerantCode(String gerantCode) {
        this.gerantCode = gerantCode;
    }

    public String getPropCode() {
        return propCode;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public List<Rue> getRues() {
        return rues;
    }

    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(List<Commune> communes) {
        this.communes = communes;
    }

    public List<TaxeTrimBoisson> getYearlyTaxeTrims() {
        if (yearlyTaxeTrims == null) {
            yearlyTaxeTrims = new ArrayList();
        }
        return yearlyTaxeTrims;
    }

    public void setYearlyTaxeTrims(List<TaxeTrimBoisson> yearlyTaxeTrims) {
        this.yearlyTaxeTrims = yearlyTaxeTrims;
    }

    public TaxeAnnuelBoisson getSelected() {
        if (selected == null) {
            selected = new TaxeAnnuelBoisson();
        }
        return selected;
    }

    public TaxeTrimBoissonController getTaxeTrimBoissonController() {
        return taxeTrimBoissonController;
    }

    public void setTaxeTrimBoissonController(TaxeTrimBoissonController taxeTrimBoissonController) {
        this.taxeTrimBoissonController = taxeTrimBoissonController;
    }

    public void setSelected(TaxeAnnuelBoisson selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeAnnuelBoissonFacade getFacade() {
        return ejbFacade;
    }

    public TaxeAnnuelBoisson prepareCreate() {
        selected = new TaxeAnnuelBoisson();
        initializeEmbeddableKey();
        return selected;
    }

    public Rue getRue() {
        if (rue == null) {
            rue = new Rue();
        }
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
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

    public Secteur getThisSecteur() {
        if (thisSecteur == null) {
            thisSecteur = new Secteur();
        }
        return thisSecteur;
    }

    public void setThisSecteur(Secteur thisSecteur) {
        this.thisSecteur = thisSecteur;
    }

    public Redevable getRedevable() {
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public TaxeTrimBoisson getSelectedTaxe() {
        return selectedTaxe;
    }

    public void setSelectedTaxe(TaxeTrimBoisson selectedTaxe) {
        this.selectedTaxe = selectedTaxe;
    }

    public int getPaiement() {
        return paiement;
    }

    public void setPaiement(int paiement) {
        this.paiement = paiement;
    }

    public Commune getThisCommune() {
        System.out.println("get commune");
        if (thisCommune == null) {
            System.out.println("commune walo");
            thisCommune = new Commune();
        }
        return thisCommune;
    }

    public void setThisCommune(Commune thisCommune) {
        this.thisCommune = thisCommune;
    }

    public void findByCriteria() {
        System.out.println("controller :: findByCriteria :: ");
        items = ejbFacade.findByCriteria(anneeMax, anneeMin, rue, thisQuartier, thisCommune, thisSecteur, dateMin, dateMax, trimMin, trimMax, paiement, redevableFacade.findRedevable(propCode, gerantCode));
        if (items == null) {
            JsfUtil.addSuccessMessage("No Data Found");
            System.out.println("n search");
        } else {
            JsfUtil.addSuccessMessage("successe");
            System.out.println("sucess");

        }
    }

    public void redirectToTaxeTrim() throws IOException{
        SessionUtil.redirect("/eTaxeCommunalNoMavenV2/faces/secured/taxeTrimBoisson/List");
    }
   

    public int getTrimMin() {
        return trimMin;
    }

    public void setTrimMin(int trimMin) {
        this.trimMin = trimMin;
    }

    public int getTrimMax() {
        return trimMax;
    }

    public void setTrimMax(int trimMax) {
        this.trimMax = trimMax;
    }

    public String getAnneeMax() {
        return anneeMax;
    }

    public void setAnneeMax(String anneeMax) {
        this.anneeMax = anneeMax;
    }

    public String getAnneeMin() {
        return anneeMin;
    }

    public void setAnneeMin(String anneeMin) {
        this.anneeMin = anneeMin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public Date getDateMin() {
        return dateMin;
    }

    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
    }

    public void secteureByCommun() {
        if (thisCommune != null) {
            setSecteurs(secteurFacade.findSecteureByCommun(getThisCommune()));
            setQuartiers(new ArrayList<Quartier>());
            setRues(new ArrayList<Rue>());
        } else {
            setSecteurs(new ArrayList<Secteur>());
        }
    }

    public void quartierBySecteure() {
        if (thisSecteur != null) {
            setQuartiers(quartierFacade.findBySecteur(getThisSecteur()));
        } else {
            setQuartiers(new ArrayList<Quartier>());
        }
    }

    public void rueByQuartier() {
        if (thisQuartier != null) {
            setRues(rueFacade.findByQuartier(getThisQuartier()));
        } else {
            setRues(new ArrayList<Rue>());
        }
    }

    public List<Commune> getCommunesAvailableSelectOne() {
        return ejbFacadeC.findAll();
    }

    //jasper
//    public void generatPdf() throws JRException, IOException {
//        System.out.println("print pdf controller");
//        taxeTrimBoissonFacade.printPdf(selectedTaxe);
//        FacesContext.getCurrentInstance().responseComplete();
//    }
    public List<Redevable> getRedevablessAvaibleSelectOne() {
        return redevableFacade.findAll();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void findTaxeTrim(TaxeAnnuelBoisson taxeAnnuelBoisson) {
        yearlyTaxeTrims = taxeTrimBoissonFacade.findByTaxeAnnuel(taxeAnnuelBoisson);
        if (yearlyTaxeTrims.isEmpty() || yearlyTaxeTrims == null) {
            JsfUtil.addErrorMessage("failed");
        } else {
            JsfUtil.addSuccessMessage("Success");
        }
    }

    public List<TaxeAnnuelBoisson> getItems() {
        if (items == null) {
            System.out.println("items null");
            items = new ArrayList<>();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public TaxeAnnuelBoisson getTaxeAnnuelBoisson(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TaxeAnnuelBoisson> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TaxeAnnuelBoisson> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TaxeAnnuelBoisson.class)
    public static class TaxeAnnuelBoissonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeAnnuelBoissonController controller = (TaxeAnnuelBoissonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeAnnuelBoissonController");
            return controller.getTaxeAnnuelBoisson(getKey(value));
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
            if (object instanceof TaxeAnnuelBoisson) {
                TaxeAnnuelBoisson o = (TaxeAnnuelBoisson) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeAnnuelBoisson.class.getName()});
                return null;
            }
        }

    }

}
