package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Rilevamento> rilevamentiMese;
	private List<Rilevamento> totali;
	private double costo_min;
 
	public Model() {
		
		costo_min = 0;
		MeteoDAO dao = new MeteoDAO();
		this.totali = dao.getAllRilevamenti();
		
	}

	public String getUmiditaMedia(int mese) {
		
		
		

		return "TODO!";
	}

	public String trovaSequenza(int mese) {
		

		return "TODO!";
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}
	
	public List<String> getAvgForEachCitta(int mese){
				
		MeteoDAO dao = new MeteoDAO();
		List<String> medie = dao.getAllRilevamentiLocalitaMese(mese);
		return medie;
		
	}
	
	public void cerca(Date data, int livello, List<Rilevamento> rilevamenti, List<Citta> parziale) {
		
		//condizione di fine
		if(data.getDay() > NUMERO_GIORNI_TOTALI) {
			return;
		}
		
		
		
		
		if(livello >= NUMERO_GIORNI_CITTA_MAX) {//FILTRO DAL GIORNO 6 IN POI PERMANENZA CITTA
			for(Citta c : parziale) {
				if(c.getCounter() > NUMERO_GIORNI_CITTA_MAX)
					return;
			}
		}
		
		
		
		for(Citta c : parziale) {//FILTRO ALMENO 3 GG CONSECUTIVI
			for(Rilevamento r : c.getRilevamenti()) {
				//if(r.getData())
			}
		}
		
		
		
		
	}

}
