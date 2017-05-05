package controller;

import bean.Activite;
import bean.TauxRetardBoisonTrim;
import bean.TauxTaxeBoisson;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.ActiviteFacade;

import java.io.Serializable;
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

@Named("activiteController")
@SessionScoped
public class ActiviteController implements Serializable {
    
    @EJB
    private services.ActiviteFacade ejbFacade;
    private List<Activite> items = null;
    private Activite selected;
    private boolean activiteFinished;
    private boolean tauxTaxeFinished;
    private boolean tauRetardFinished;
    
    @EJB
    private services.TauxTaxeBoissonFacade tauxTaxeFacade;
    private TauxTaxeBoisson tauxTaxe;
    private TauxRetardBoisonTrim tauxRetard;
    
    public ActiviteController() {
    }
    
    public TauxTaxeBoisson getTauxTaxe() {
        if (tauxTaxe == null) {
            tauxTaxe = new TauxTaxeBoisson();
            tauxTaxe.setId(tauxTaxeFacade.generateId("TauxTaxeBoisson", "id"));
        }
        return tauxTaxe;
    }
    
    public void setTauxTaxe(TauxTaxeBoisson tauxTaxe) {
        this.tauxTaxe = tauxTaxe;
    }
    
    public TauxRetardBoisonTrim getTauxRetard() {
        if (tauxRetard == null) {
            tauxRetard = new TauxRetardBoisonTrim();
        }
        return tauxRetard;
    }
    
    public void setTauxRetard(TauxRetardBoisonTrim tauxRetard) {
        this.tauxRetard = tauxRetard;
    }
    
    public Activite getSelected() {
        if (selected == null) {
            selected = new Activite();
            selected.setId(ejbFacade.generateId("Activite", "id"));
        }
        return selected;
    }
    
    public void setSelected(Activite selected) {
        this.selected = selected;
    }
    
    public void setItems(List<Activite> items) {
        this.items = items;
    }
    
    public boolean isActiviteFinished() {
        return activiteFinished;
    }
    
    public void setActiviteFinished(boolean activiteFinished) {
        this.activiteFinished = activiteFinished;
    }
    
    public boolean isTauxTaxeFinished() {
        return tauxTaxeFinished;
    }
    
    public void setTauxTaxeFinished(boolean tauxTaxeFinished) {
        this.tauxTaxeFinished = tauxTaxeFinished;
    }
    
    public boolean isTauRetardFinished() {
        return tauRetardFinished;
    }
    
    public void setTauRetardFinished(boolean tauRetardFinished) {
        this.tauRetardFinished = tauRetardFinished;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private ActiviteFacade getFacade() {
        return ejbFacade;
    }
    
    public Activite prepareCreate() {
        selected = null;
        initializeEmbeddableKey();
        return selected;
    }
    
    public void prepareView() {
        selected = null;
        tauxTaxe = null;
        tauxRetard = null;
        activiteFinished = false;
        tauRetardFinished = false;
        tauxTaxeFinished = false;
        initializeEmbeddableKey();
    }
    
    public void activiteFinished() {
        activiteFinished = true;
        getTauxTaxe().setActivite(getSelected());
    }

    public void tauxTaxeFinished() {
        tauxTaxeFinished = true;
        getTauxRetard().setTauxBoissonTaxe(getTauxTaxe());
    }

    public void tauxRetardTaxeFinished() {
        tauRetardFinished = true;
    }

    public void create() {
        int res = ejbFacade.createActivite(getSelected(), getTauxRetard(), getTauxTaxe());
        if(res<0){
            JsfUtil.addErrorMessage("erreur pendant la creation de l'activite");
        }else
            JsfUtil.addSuccessMessage("Success");
        prepareView();
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ActiviteUpdated"));
    }
    
    public void destroy(Activite activite) {
        if (activite != null) {
            getFacade().remove(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ActiviteDeleted"));
            selected = null; // Remove selection
            items = null;
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    public List<Activite> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    private void persist(PersistAction persistAction, String successMessage) {
        if (getSelected() != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.UPDATE) {
                    getFacade().savedEdite(getSelected());
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
    
    public Activite getActivite(java.lang.Long id) {
        return getFacade().find(id);
    }
    
    public List<Activite> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<Activite> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = Activite.class)
    public static class ActiviteControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActiviteController controller = (ActiviteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "activiteController");
            return controller.getActivite(getKey(value));
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
            if (object instanceof Activite) {
                Activite o = (Activite) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Activite.class.getName()});
                return null;
            }
        }
        
    }
    
}
