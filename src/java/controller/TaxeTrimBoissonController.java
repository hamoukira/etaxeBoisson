package controller;

import bean.Commune;
import bean.Locale;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import bean.TaxeTrimBoisson;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import java.io.IOException;
import services.TaxeTrimBoissonFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;
import services.LocaleFacade;
import services.RedevableFacade;

@Named("taxeTrimBoissonController")
@SessionScoped
public class TaxeTrimBoissonController implements Serializable {

    @EJB
    private services.TaxeTrimBoissonFacade ejbFacade;
    private List<TaxeTrimBoisson> items = null;
    private TaxeTrimBoisson selected;

    @EJB
    private services.SecteurFacade secteurFacade;
    @EJB
    private services.CommuneFacade communeFacade;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;
    @EJB
    RedevableFacade redevableFacade;
    @EJB
    LocaleFacade localeFacade;
    private List<Locale> localsAvailable;
    private String propCode;
    private String gerantCode;
    private TaxeTrimBoisson anItem;
    private boolean isSimulation = false;
    private String renderYear = "false";

    private BarChartModel barModel;
    private LineChartModel lineModel;
    private DonutChartModel donutModel;
    private int typeGraphe=1;
    private int anneeDeb;
    private int anneeFin;
    private Rue rue;
    private Quartier thisQuartier;
    private Secteur thisSecteur;
    private Commune thisCommune;
    private List<Secteur> secteurs;
    private List<Quartier> quartiers;
    private List<Rue> rues;
    private String cin;
    private String rc;
    private int annee;
    private boolean rendred;
    private boolean redevableFiels;

    public TaxeTrimBoissonController() {
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public List<Rue> getRues() {
        return rues;
    }

    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public int getTypeGraphe() {
        return typeGraphe;
    }

    public void setTypeGraphe(int typeGraphe) {
        this.typeGraphe = typeGraphe;
    }

    public int getAnneeDeb() {
        return anneeDeb;
    }

    public void setAnneeDeb(int anneeDeb) {
        this.anneeDeb = anneeDeb;
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    public void setAnneeFin(int anneeFin) {
        this.anneeFin = anneeFin;
    }

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public Quartier getThisQuartier() {
        return thisQuartier;
    }

    public void setThisQuartier(Quartier thisQuartier) {
        this.thisQuartier = thisQuartier;
    }

    public Secteur getThisSecteur() {
        return thisSecteur;
    }

    public void setThisSecteur(Secteur thisSecteur) {
        this.thisSecteur = thisSecteur;
    }

    public Commune getThisCommune() {
        return thisCommune;
    }

    public void setThisCommune(Commune thisCommune) {
        this.thisCommune = thisCommune;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public boolean isRendred() {
        return rendred;
    }

    public void setRendred(boolean rendred) {
        this.rendred = rendred;
    }

    public boolean isRedevableFiels() {
        return redevableFiels;
    }

    public void setRedevableFiels(boolean redevableFiels) {
        this.redevableFiels = redevableFiels;
    }

    public boolean isIsSimulation() {
        return isSimulation;
    }

    public void setIsSimulation(boolean isSimulation) {
        this.isSimulation = isSimulation;
    }

    public String getRenderYear() {
        return renderYear;
    }

    public void setRenderYear(String renderYear) {
        this.renderYear = renderYear;
    }

    public TaxeTrimBoisson getAnItem() {
        if (anItem == null) {
            anItem = new TaxeTrimBoisson();
        }
        return anItem;
    }

    public void setAnItem(TaxeTrimBoisson anItem) {
        this.anItem = anItem;
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

    public TaxeTrimBoisson getSelected() {
        if (selected == null) {
            selected = new TaxeTrimBoisson();
        }
        return selected;
    }

    public void setSelected(TaxeTrimBoisson selected) {
        this.selected = selected;
    }

    public List<TaxeTrimBoisson> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeTrimBoissonFacade getFacade() {
        return ejbFacade;
    }

    public List<Locale> getLocalsAvailable() {
        if (localsAvailable == null) {
            localsAvailable = new ArrayList();
        }
        return localsAvailable;
    }

    public void setLocalsAvailable(List<Locale> localsAvailable) {
        this.localsAvailable = localsAvailable;
    }

    public void secteureByCommun() {
//        thisCommun = commun;
        setSecteurs(secteurFacade.findSecteureByCommun(thisCommune));
        setQuartiers(new ArrayList<Quartier>());//pour enisialiser si la commune et changer
        setRues(new ArrayList<Rue>());//appel avec get pour eviter le cas ou ThisSecteur et ThisQyartie son null

    }

    public void quartierBySecteure() {
//        thisSecteur = secteur;
        setQuartiers(quartierFacade.findBySecteur(thisSecteur));
        setRues(new ArrayList<Rue>());

    }

    public void rueByQuartier() {
//        thisQuartier = quartier;
//        System.out.println("rueByQuartier :: thisQuartier :: "+thisQuartier);
        setRues(rueFacade.findByQuartier(thisQuartier));
    }

    public void findLocals(int natureredevable, TaxeTrimBoisson ttb) {
        List<Redevable> redevables = findRedevable(natureredevable);
        if (redevables.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RedavableSearchIsEmpty"));
        } else {
            ttb.setRedevable(redevables.get(0));
            if (natureredevable == 2) {
                localsAvailable = localeFacade.findLocals(null, null, null, null, null, null, null, null, redevables.get(0));
            }
            if (natureredevable == 1) {
                localsAvailable = localeFacade.findLocals(null, null, null, null, null, null, null, redevables.get(0), null);
            }
            if (localsAvailable == null || localsAvailable.isEmpty() || localsAvailable.isEmpty()) {
                JsfUtil.addErrorMessage("No Locals availeble");
            } else {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
                setRenderYear("true");
            }

        }

    }

    private List<Redevable> findRedevable(int natureredevable) {
        List<Redevable> redevables = new ArrayList<>();
        if (natureredevable == 2) {
            if (propCode != null && !"".equals(propCode)) {
                redevables = redevableFacade.findRedevable(2, propCode, null, null, null);
                if (redevables.isEmpty()) {
                    redevables = redevableFacade.findRedevable(2, null, propCode, null, null);
                }
            }
        } else if (gerantCode != null && !"".equals(gerantCode)) {
            redevables = redevableFacade.findRedevable(1, gerantCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = redevableFacade.findRedevable(1, null, gerantCode, null, null);
            }
        }
        return redevables;
    }

    public void calculateChiffreAffaireHT(TaxeTrimBoisson ttb) {
        ttb.setChiffreAffaireHT(ejbFacade.calculeChiffreAffaireHT(ttb.getLocal(), ttb.getChiffreAffaireTTC()));
    }

    public void calculateChiffreAffaireTTC(TaxeTrimBoisson ttb) {
        ttb.setChiffreAffaireTTC(ejbFacade.calculeChiffreAffaireTTC(ttb.getLocal(), ttb.getChiffreAffaireHT()));
    }

    public void prepareCreate() {
        setIsSimulation(false);
        selected =null;
        items=null;
    }

    public void prepareSimulation() {
        setIsSimulation(true);
        anItem = new TaxeTrimBoisson();
        initializeEmbeddableKey();
    }

    public void createTest() {
        int res = getFacade().createTaxeTrimBoisson(selected, SessionUtil.getConnectedUser());
        switch (res) {
            case -1:
                JsfUtil.addErrorMessage("Tous les trimestres de l'annee " + selected.getTaxeYear().getYear() + " son payee");
                break;
            case -2:
                JsfUtil.addErrorMessage("le trimestr " + selected.getNumeroTrim() + " de l'annee " + selected.getTaxeYear().getYear() + " et déja payee");
                break;
            case -3:
                JsfUtil.addErrorMessage("annee saisi erronée");
                break;
            case -4:
                JsfUtil.addErrorMessage("le trimestr " + selected.getNumeroTrim() + " de l'annee " + selected.getTaxeYear().getYear() + " n'est pas toujour terminee");
                break;
            case -5:
                JsfUtil.addErrorMessage("Taux Taxe pour l'activiter " + selected.getLocal().getTypeLocal().getNom() + " et introuvable \n informer l'administrateure");
                break;
            case -6:
                JsfUtil.addErrorMessage("Taux Retard Taxe pour l'activiter " + selected.getLocal().getTypeLocal().getNom() + " et introuvable \n informer l'administrateure");
                break;
            default:
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
                getItems().add(ejbFacade.clone(selected));
                break;
        }
        setRenderYear("false");
    }

    public void simuler() {
        setAnItem(getFacade().similerTaxeTrimBoisson(getAnItem()));
        setRenderYear("false");

    }

    public void cancel() {
        selected = null;
        setRenderYear("false");
    }

    public TaxeTrimBoisson getTaxeTrimBoisson(java.lang.Long id) {
        return getFacade().find(id);
    }
///creation des charts

    public void createModel() {
        items = ejbFacade.findStat(anneeDeb, anneeFin, rue, thisQuartier, thisCommune, thisSecteur);
        switch (typeGraphe) {

            case 1: {
                barModel = ejbFacade.initBarModel(items, anneeDeb, anneeFin);
                barModel.setTitle("Statistique");
                barModel.setLegendPosition("ne");
                Axis xAxis = barModel.getAxis(AxisType.X);
                xAxis.setLabel("Les trimestres");
                Axis yAxis = barModel.getAxis(AxisType.Y);
                yAxis.setLabel("Montant");
                yAxis.setMin(0);
                yAxis.setMax(ejbFacade.maxChart(items, anneeDeb, anneeFin) * 1.1);
                break;
            }
            case 2: {
                lineModel = ejbFacade.initLineModel(items, anneeDeb, anneeFin);
                lineModel.setTitle("Statistique");
                lineModel.setLegendPosition("ne");
                lineModel.setAnimate(true);
                Axis xAxis = lineModel.getAxis(AxisType.X);
                lineModel.getAxes().put(AxisType.X, new CategoryAxis(""));
                xAxis.setLabel("Les trimestres");
                Axis yAxis = lineModel.getAxis(AxisType.Y);
                yAxis.setLabel("Montant");
                yAxis.setMin(0);
                yAxis.setMax(ejbFacade.maxChart(items, anneeDeb, anneeFin) * 1.1);
                break;
            }
            case 3:
                donutModel = ejbFacade.initDonuModel(items, anneeDeb, anneeFin);
                donutModel.setTitle("Statistique");
                donutModel.setLegendPosition("ne");
                donutModel.setSliceMargin(5);
                donutModel.setShowDataLabels(true);
                donutModel.setDataFormat("value");
                donutModel.setShadow(true);
                break;
            default:
                break;
        }

    }

    ///Jasper report
    // jasper
    public void generatPdf(TaxeTrimBoisson taxeTrimBoisson) throws JRException, IOException {
        System.out.println("print pdf controller");
        ejbFacade.printPdf(taxeTrimBoisson);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    
    public  List<Commune> communesAvailableSelectOne(){
        return communeFacade.findAll();
    }

    @FacesConverter(forClass = TaxeTrimBoisson.class)
    public static class TaxeTrimBoissonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeTrimBoissonController controller = (TaxeTrimBoissonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeTrimBoissonController");
            return controller.getTaxeTrimBoisson(getKey(value));
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
            if (object instanceof TaxeTrimBoisson) {
                TaxeTrimBoisson o = (TaxeTrimBoisson) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeTrimBoisson.class.getName()});
                return null;
            }
        }

    }

}
