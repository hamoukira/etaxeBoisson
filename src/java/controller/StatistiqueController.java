/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author PC
 */
@Named("statistiqueController")
@SessionScoped
//@ViewScoped
public class StatistiqueController implements Serializable {
//
//    @EJB
//    private services.TaxeTrimBoissonFacade ejbFacade;
//    private List<TaxeTrimBoisson> items;
//    private BarChartModel barModel;
//    private LineChartModel lineModel;
//    private DonutChartModel donutModel;
//    private int typeGraphe;
//    private int anneeDeb;
//    private int anneeFin;
//    private Rue rue;
//    private Quartier thisQuartier;
//    private Secteur thisSecteur;
//    private Commune thisCommune;
////    private String cin;
////    private String rc;
////    private int annee;
////    private boolean rendred;
////     private boolean redevableFiels;
//
//    public List<TaxeTrimBoisson> getItems() {
//        return items;
//    }
//
//    public void setItems(List<TaxeTrimBoisson> items) {
//        this.items = items;
//    }
//
//    public BarChartModel getBarModel() {
//        return barModel;
//    }
//
//    public void setBarModel(BarChartModel barModel) {
//        this.barModel = barModel;
//    }
//
//    public LineChartModel getLineModel() {
//        return lineModel;
//    }
//
//    public void setLineModel(LineChartModel lineModel) {
//        this.lineModel = lineModel;
//    }
//
//    public DonutChartModel getDonutModel() {
//        return donutModel;
//    }
//
//    public void setDonutModel(DonutChartModel donutModel) {
//        this.donutModel = donutModel;
//    }
//
//    public int getTypeGraphe() {
//        return typeGraphe;
//    }
//
//    public void setTypeGraphe(int typeGraphe) {
//        this.typeGraphe = typeGraphe;
//    }
//
//    public int getAnneeDeb() {
//        return anneeDeb;
//    }
//
//    public void setAnneeDeb(int anneeDeb) {
//        this.anneeDeb = anneeDeb;
//    }
//
//    public int getAnneeFin() {
//        return anneeFin;
//    }
//
//    public void setAnneeFin(int anneeFin) {
//        this.anneeFin = anneeFin;
//    }
//
//    public Rue getRue() {
//        return rue;
//    }
//
//    public void setRue(Rue rue) {
//        this.rue = rue;
//    }
//
//    public Quartier getThisQuartier() {
//        return thisQuartier;
//    }
//
//    public void setThisQuartier(Quartier thisQuartier) {
//        this.thisQuartier = thisQuartier;
//    }
//
//    public Secteur getThisSecteur() {
//        return thisSecteur;
//    }
//
//    public void setThisSecteur(Secteur thisSecteur) {
//        this.thisSecteur = thisSecteur;
//    }
//
//    public Commune getThisCommune() {
//        return thisCommune;
//    }
//
//    public void setThisCommune(Commune thisCommune) {
//        this.thisCommune = thisCommune;
//    }
//
//    
//    
//    ///creation des charts
//    public void createModel() {
//        items = ejbFacade.findStat(anneeDeb, anneeFin, rue, thisQuartier, thisCommune, thisSecteur);
//        switch (typeGraphe) {
//
//            case 1: {
//                barModel = ejbFacade.initBarModel(items, anneeDeb, anneeFin);
//                barModel.setTitle("Statistique");
//                barModel.setLegendPosition("ne");
//                Axis xAxis = barModel.getAxis(AxisType.X);
//                xAxis.setLabel("Les trimestres");
//                Axis yAxis = barModel.getAxis(AxisType.Y);
//                yAxis.setLabel("Montant");
//                yAxis.setMin(0);
//                yAxis.setMax(ejbFacade.maxChart(items, anneeDeb, anneeFin) * 1.1);
//                break;
//            }
//            case 2: {
//                lineModel = ejbFacade.initLineModel(items, anneeDeb, anneeFin);
//                lineModel.setTitle("Statistique");
//                lineModel.setLegendPosition("ne");
//                lineModel.setAnimate(true);
//                Axis xAxis = lineModel.getAxis(AxisType.X);
//                lineModel.getAxes().put(AxisType.X, new CategoryAxis(""));
//                xAxis.setLabel("Les trimestres");
//                Axis yAxis = lineModel.getAxis(AxisType.Y);
//                yAxis.setLabel("Montant");
//                yAxis.setMin(0);
//                yAxis.setMax(ejbFacade.maxChart(items, anneeDeb, anneeFin) * 1.1);
//                break;
//            }
//            case 3:
//                donutModel = ejbFacade.initDonuModel(items, anneeDeb, anneeFin);
//                donutModel.setTitle("Statistique");
//                donutModel.setLegendPosition("ne");
//                donutModel.setSliceMargin(5);
//                donutModel.setShowDataLabels(true);
//                donutModel.setDataFormat("value");
//                donutModel.setShadow(true);
//                break;
//            default:
//                break;
//        }
//
//    }
}
