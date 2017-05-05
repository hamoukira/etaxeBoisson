/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Commune;
import bean.Locale;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import bean.TauxRetardBoisonTrim;
import bean.TauxTaxeBoisson;
import bean.TaxeAnnuelBoisson;
import bean.TaxeTrimBoisson;
import bean.Userr;
import controller.util.CalculeUtils;
import controller.util.FrenchNumberToWords;
import controller.util.PdfUtil;
import controller.util.SearchUtil;
import controller.util.SessionUtil;
import java.io.IOException;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class TaxeTrimBoissonFacade extends AbstractFacade<TaxeTrimBoisson> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeTrimBoissonFacade() {
        super(TaxeTrimBoisson.class);
    }
    @EJB
    TaxeAnnuelBoissonFacade taxeAnnuelBoissonFacade;
    @EJB
    TauxTaxeBoissonFacade tauxTaxeBoissonFacade;
    @EJB
    TauxRetardBoisonTrimFacade tauxRetardBoisonTrimFacade;

    public void clone(TaxeTrimBoisson source, TaxeTrimBoisson destination) {
        destination.setChiffreAffaireHT(source.getChiffreAffaireHT());
        destination.setChiffreAffaireTTC(source.getChiffreAffaireTTC());
        destination.setDateActuel(source.getDateActuel());
        destination.setId(source.getId());
        destination.setLocal(source.getLocal());
        destination.setMontantRetardAutreMois(source.getMontantRetardAutreMois());
        destination.setMontantRetardPremierMois(source.getMontantRetardPremierMois());
        destination.setMontantTotalRetard(source.getMontantTotalRetard());
        destination.setMontantTotalTaxe(source.getMontantTotalTaxe());
        destination.setMontantTaxe(source.getMontantTaxe());
        destination.setNumeroTrim(source.getNumeroTrim());
        destination.setRedevable(source.getRedevable());
        destination.setTaxeAnnuelBoisson(source.getTaxeAnnuelBoisson());
        destination.setTaxeYear(source.getTaxeYear());
        destination.setUser(source.getUser());
    }

    public TaxeTrimBoisson clone(TaxeTrimBoisson source) {
        TaxeTrimBoisson cloned = new TaxeTrimBoisson();
        clone(source, cloned);
        return cloned;
    }

    //1:success -1:annee paye -2:trimester payee -3:anne sassi superrieur a anne actuel -4:trim pas passe  -5:tauxTaxeBoisson pas trouver 
    public int createTaxeTrimBoisson(TaxeTrimBoisson taxeTrimBoisson, Userr user) {
        if (taxeTrimBoisson.getTaxeYear().until(LocalDate.now(), ChronoUnit.DAYS) > 0) {
            Object[] respons = taxeAnnuelBoissonFacade.getTaxeAnnuel(taxeTrimBoisson.getLocal(), taxeTrimBoisson.getTaxeYear().getYear(), taxeTrimBoisson.getNumeroTrim());
            int codeRespons = (int) respons[1];
            if (codeRespons < 0) {
                return codeRespons;
            } else {
                TaxeAnnuelBoisson taxeAnnuelBoisson = (TaxeAnnuelBoisson) respons[0];
                LocalDate taxeDate = setTaxeDate(taxeTrimBoisson.getNumeroTrim(), taxeTrimBoisson.getTaxeYear().getYear());
                Long months = taxeDate.until(LocalDate.now(), ChronoUnit.MONTHS);
                if (months < 0) {
                    return -4;
                } else {
                    return createTaxeTrim(taxeTrimBoisson, months, user, taxeAnnuelBoisson);
                }
            }
        }
        System.out.println("-3");
        return -3;
    }

    public TaxeTrimBoisson similerTaxeTrimBoisson(TaxeTrimBoisson anItem) {
        LocalDate taxeDate = setTaxeDate(anItem.getNumeroTrim(), anItem.getTaxeYear().getYear());
        Long months = taxeDate.until(LocalDate.now(), ChronoUnit.MONTHS);
        TauxTaxeBoisson tauxTaxeBoisson = tauxTaxeBoissonFacade.findTauxTaxeByActivity(anItem.getLocal().getTypeLocal());
        if (tauxTaxeBoisson != null) {
            anItem = initTaxeTrim(anItem, SessionUtil.getConnectedUser(), new TaxeAnnuelBoisson());
            anItem = calculate(anItem.getChiffreAffaireHT(), tauxTaxeBoisson, months, anItem);
            return anItem;
        }
        return null;
    }

    private TaxeTrimBoisson initTaxeTrim(TaxeTrimBoisson taxeTrimBoisson, Userr user, TaxeAnnuelBoisson taxeAnnuelBoisson) {
        taxeTrimBoisson.setDateActuel(LocalDate.now());
        taxeTrimBoisson.setUser(user);
        taxeTrimBoisson.setTaxeAnnuelBoisson(taxeAnnuelBoisson);
        return taxeTrimBoisson;
    }

    private LocalDate setTaxeDate(int trim, int year) {
        System.out.println("hani f :: setTaxeDate");
        LocalDate taxeDate;
        switch (trim) {
            case 1:
                taxeDate = LocalDate.of(year, 3, 31);
                break;
            case 2:
                taxeDate = LocalDate.of(year, 6, 30);
                break;
            case 3:
                taxeDate = LocalDate.of(year, 9, 30);
                break;
            default:
                taxeDate = LocalDate.of(year, 12, 31);
        }
        return taxeDate;
    }

    public Double calculeChiffreAffaireHT(Locale locale, Double chiffreAffaireTTC) {
        return CalculeUtils.formatAndRoundNumber((chiffreAffaireTTC * 100) / (locale.getTypeLocal().getTva() + 100), RoundingMode.CEILING, 3);
    }

    public Double calculeChiffreAffaireTTC(Locale locale, Double chiffreAffaireHT) {
        return CalculeUtils.formatAndRoundNumber(chiffreAffaireHT + ((chiffreAffaireHT * locale.getTypeLocal().getTva()) / 100), RoundingMode.CEILING, 3);
    }

    private int createTaxeTrim(TaxeTrimBoisson taxeTrimBoisson, Long monthsRetard, Userr user, TaxeAnnuelBoisson taxeAnnuelBoisson) {
        TauxTaxeBoisson tauxTaxeBoisson = tauxTaxeBoissonFacade.findTauxTaxeByActivity(taxeTrimBoisson.getLocal().getTypeLocal());
        if (tauxTaxeBoisson != null) {
            taxeTrimBoisson = initTaxeTrim(taxeTrimBoisson, user, taxeAnnuelBoisson);
            taxeTrimBoisson = calculate(taxeTrimBoisson.getChiffreAffaireHT(), tauxTaxeBoisson, monthsRetard, taxeTrimBoisson);
            if (taxeTrimBoisson != null) {
                taxeAnnuelBoisson.setMontantTaxeannuel(taxeAnnuelBoisson.getMontantTaxeannuel() + taxeTrimBoisson.getMontantTotalTaxe());
                create(taxeTrimBoisson);
                return 1;
            }
            return -6;
        }
        return -5;
    }

    private TaxeTrimBoisson calculate(Double chiffreAffaireHT, TauxTaxeBoisson tauxTaxeBoisson, Long monthsRetard, TaxeTrimBoisson taxeTrimBoisson) {
        Double montantTetardAutreMois;
        Double montantTetardPremierMois;
        Double montantTaxeNormal = (chiffreAffaireHT * tauxTaxeBoisson.getTaux()) / 100;
        taxeTrimBoisson.setMontantTaxe(CalculeUtils.formatAndRoundNumber(montantTaxeNormal, RoundingMode.CEILING, 3));
        if (monthsRetard > 0) {
            TauxRetardBoisonTrim tauxRetardBoisonTrim = tauxRetardBoisonTrimFacade.findTauxTaxeByActivity(tauxTaxeBoisson);
            if (tauxRetardBoisonTrim != null) {
                montantTetardPremierMois = chiffreAffaireHT * tauxRetardBoisonTrim.getTauxRetardPremierMois() / 100;
                taxeTrimBoisson.setMontantRetardPremierMois(CalculeUtils.formatAndRoundNumber(montantTetardPremierMois, RoundingMode.CEILING, 3));
                monthsRetard--;
                if (monthsRetard > 0) {
                    montantTetardAutreMois = (monthsRetard * chiffreAffaireHT * tauxRetardBoisonTrim.getTauxRetardAutreMois()) / 100;
                    taxeTrimBoisson.setMontantRetardAutreMois(CalculeUtils.formatAndRoundNumber(montantTetardAutreMois, RoundingMode.CEILING, 3));
                    taxeTrimBoisson.setMontantTotalRetard(CalculeUtils.formatAndRoundNumber(montantTetardAutreMois + montantTetardPremierMois, RoundingMode.CEILING, 3));
                } else {
                    taxeTrimBoisson.setMontantTotalRetard(CalculeUtils.formatAndRoundNumber(montantTetardPremierMois, RoundingMode.CEILING, 3));
                }
                taxeTrimBoisson.setMontantTotalTaxe(CalculeUtils.formatAndRoundNumber(montantTaxeNormal + taxeTrimBoisson.getMontantTotalRetard(), RoundingMode.CEILING, 3));
            } else {
                return null;
            }
        } else {
            taxeTrimBoisson.setMontantTotalTaxe(CalculeUtils.formatAndRoundNumber(montantTaxeNormal, RoundingMode.CEILING, 3));
        }
        return taxeTrimBoisson;
    }

    public List<TaxeTrimBoisson> findByTaxeAnnuel(TaxeAnnuelBoisson taxeAnnuelBoisson) {
        return em.createQuery("SELECT ttb FROM TaxeTrimBoisson ttb WHERE ttb.taxeAnnuelBoisson.id=" + taxeAnnuelBoisson.getId()).getResultList();
    }

    //rrecherche dial chart
    public List<TaxeTrimBoisson> findStat(int anneeDeb, int anneeFin, Rue rue, Quartier quartier, Commune commune, Secteur secteur) {
        String query = "SELECT tax FROM TaxeTrimBoisson tax where 1=1 ";

        if (anneeDeb > 0 && anneeFin > 0) {
            query += "AND tax.taxeAnnuelBoisson.annee in(" + anneeDeb + "," + anneeFin + ")";
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
        System.out.println(query);
        return em.createQuery(query).getResultList();
    }

    //init barChart //pour construire la sereis des coordonnees pour les bar
    public BarChartModel initBarModel(List<TaxeTrimBoisson> taxes, int firstYear, int secondYear) {
        ChartSeries firstYearTaxe = new ChartSeries();
        ChartSeries secondYearTaxe = new ChartSeries();
        BarChartModel model1 = new BarChartModel();
        firstYearTaxe.setLabel("" + firstYear);
        secondYearTaxe.setLabel("" + secondYear);
        int x;
        for (x = 1; x < 5; x++) {
            Double a = 0.0;
            Double b = 0.0;
            for (TaxeTrimBoisson taxeTrim : taxes) {
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == firstYear && taxeTrim.getNumeroTrim() == x) {
                    a += taxeTrim.getMontantTotalTaxe();
                }
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == secondYear && taxeTrim.getNumeroTrim() == x) {
                    b += taxeTrim.getMontantTotalTaxe();
                }
            }
            firstYearTaxe.set("Trimestre " + x, a);
            secondYearTaxe.set("Trimestre " + x, b);
        }
        model1.addSeries(firstYearTaxe);
        model1.addSeries(secondYearTaxe);
        return model1;
    }

    // Chart-Donut
    public DonutChartModel initDonuModel(List<TaxeTrimBoisson> taxes, int firstYear, int secondYear) {
        DonutChartModel donutMoel = new DonutChartModel();
        Map<String, Number> firstCircle = new LinkedHashMap<>();
        Map<String, Number> secondCircle = new LinkedHashMap<>();
        int x;
        for (x = 1; x < 5; x++) {
            Double a = 0.0;
            Double b = 0.0;
            for (TaxeTrimBoisson taxeTrim : taxes) {
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == firstYear && taxeTrim.getNumeroTrim() == x) {
                    a += taxeTrim.getMontantTotalTaxe();
                }
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == secondYear && taxeTrim.getNumeroTrim() == x) {
                    b += taxeTrim.getMontantTotalTaxe();
                }
            }
            firstCircle.put("Trimestre " + x + "-" + firstYear, a);
            secondCircle.put("Trimestre " + x + "-" + secondYear, b);
        }

        donutMoel.addCircle(firstCircle);
        donutMoel.addCircle(secondCircle);
        return donutMoel;
    }

    //initialiser  lineChart
    public LineChartModel initLineModel(List<TaxeTrimBoisson> taxes, int anneeDeb, int anneeFin) {
        ChartSeries firstSerie = new ChartSeries();
        ChartSeries secondSerie = new ChartSeries();
        int x;
        for (x = 1; x < 5; x++) {
            Double a = 0.0;
            Double b = 0.0;
            for (TaxeTrimBoisson taxeTrim : taxes) {
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == anneeDeb && taxeTrim.getNumeroTrim() == x) {
                    a += taxeTrim.getMontantTotalTaxe();
                }
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == anneeFin && taxeTrim.getNumeroTrim() == x) {
                    b += taxeTrim.getMontantTotalTaxe();
                }
            }
            firstSerie.set("Trimestre " + x, a);
            secondSerie.set("Trimestre " + x, b);
        }
        firstSerie.setLabel("" + anneeDeb);
        secondSerie.setLabel("" + anneeFin);
        LineChartModel modelLine = new LineChartModel();
        modelLine.addSeries(firstSerie);
        modelLine.addSeries(secondSerie);
        modelLine.setShowPointLabels(true);
        return modelLine;
    }

    public Double maxChart(List<TaxeTrimBoisson> taxes, int anneeDeb, int anneeFin) {
        int x;
        Double max = 0.0;
        for (x = 1; x < 5; x++) {
            Double a = 0.0;
            Double b = 0.0;
            for (TaxeTrimBoisson taxeTrim : taxes) {
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == anneeDeb && taxeTrim.getNumeroTrim() == x) {
                    a += taxeTrim.getMontantTotalTaxe();
                }
                if (taxeTrim.getTaxeAnnuelBoisson().getAnnee() == anneeFin && taxeTrim.getNumeroTrim() == x) {
                    b += taxeTrim.getMontantTotalTaxe();
                }
            }
            if (a > max) {
                max = a;
            }
            if (b > max) {
                max = b;
            }
        }
        return max;
    }

    // jasper
//    public void printPdf(TaxeTrimBoisson taxeTrim) throws JRException, IOException {
//        System.out.println("prind pdf facade");
//        List myList = new ArrayList();
//        myList.add(taxeTrim);
//        Quartier quartier = taxeTrim.getLocal().getRue().getQuartier();
//        String nature;
//        if (taxeTrim.getRedevable().getNature() == 1) {
//            nature = "Gerant";
//        } else {
//            nature = "proprietaire";
//        }
//        Map<String, Object> params = new HashMap();
//        params.put("nomLocale", taxeTrim.getLocal().getNom());
//        params.put("adresse", taxeTrim.getLocal().getRue() + " " + quartier + " " + quartier.getSecteur() + " " + quartier.getSecteur().getCommune());
//        params.put("cin", taxeTrim.getRedevable().getCin());
//        params.put("nature ", nature);
//        params.put("nomRedevable", taxeTrim.getRedevable().getPrenom() + " " + taxeTrim.getRedevable().getNom());
//        params.put("Mail", taxeTrim.getRedevable().getMail());
////        params.put("lettre", FrenchNumberToWords.convert(152L));
////        params.put("lettre", FrenchNumberToWords.convert(150L));
//        params.put("annee", taxeTrim.getTaxeAnnuelBoisson().getAnnee());
//        params.put("numeroTrim", taxeTrim.getNumeroTrim());
//        params.put("dateActuel", taxeTrim.getDateActuel());
//        System.out.println(params);
//        System.out.println(taxeTrim);
//        PdfUtil.generatePdf(myList, params, "versementTrim" + taxeTrim.getId() + ".pdf", "/jasper/versementTrim.jasper");
//    }
    
    
    
    
    public void printPdf(TaxeTrimBoisson taxeTrim) throws JRException, IOException {
        List myList = new ArrayList();
        myList.add(taxeTrim);
        Map<String, Object> params = prepareParams(taxeTrim);
        PdfUtil.generatePdf(myList, params, "bordereau" + taxeTrim.getId() + ".pdf", "jasper/taxeTrimRapport.jasper");
    }


    
    
    private Map<String, Object> prepareParams(TaxeTrimBoisson taxeTrim) {
        String nature;
        String cinOuRcRedevable;
        String adresse = taxeTrim.getLocal().getRue().getQuartier().getSecteur().getName() + " "
                + taxeTrim.getLocal().getRue().getQuartier().getName() + " "
                + taxeTrim.getLocal().getRue().getName() + " " + taxeTrim.getLocal().getComplementAdress();
        if (taxeTrim.getRedevable().getNature() == 1) {
            nature = "Gerant";
        } else {
            nature = "proprietaire";
        }
        if (taxeTrim.getRedevable().getCin() != null || !taxeTrim.getRedevable().getCin().equals("")) {
            cinOuRcRedevable = taxeTrim.getRedevable().getCin();
        } else {
            cinOuRcRedevable = taxeTrim.getRedevable().getRc();
        }
        Map<String, Object> params = new HashMap();
        params.put("redevableName", taxeTrim.getRedevable().getNom());
        params.put("activite", taxeTrim.getLocal().getTypeLocal().getNom());
        params.put("taxYear", taxeTrim.getTaxeYear().getYear());
        params.put("dateEffectationTaxe", taxeTrim.getDateActuel().toString());
        params.put("idRedevable", taxeTrim.getRedevable().getId());
        params.put("cinOuRcRedevable", cinOuRcRedevable);
        params.put("nomLocale", taxeTrim.getLocal().getNom());
        params.put("natureRedevable", nature);
        params.put("adresseLocale", adresse);
        params.put("totalEnLettre", FrenchNumberToWords.convert(taxeTrim.getMontantTotalTaxe()));
        params.put("userName", taxeTrim.getUser().getNom());
        return params;
    }
}
