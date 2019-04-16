package it.polito.tdp.meteo;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private List<Citta> leCitta;
	private List<Citta> migliore;
	private double bestCosto;
	
	public List<Citta> getLeCitta(){
		return leCitta;
	}
	
 
	public Model() {
		
		
		MeteoDAO dao = new MeteoDAO();
		this.leCitta = dao.getCitta();
		bestCosto = 0.0;
		
		
	}

	
	public List<String> getAvgForEachCitta(int mese){
				
		MeteoDAO dao = new MeteoDAO();
		List<String> medie = dao.getAllRilevamentiLocalitaMese(mese);
		return medie;
		
	}
	
	public List<Citta> calcolaSequenza(Month mese) {
		
		List<Citta> parziale = new ArrayList<>();
		this.migliore = null;
		MeteoDAO dao = new MeteoDAO();
		for(Citta c : leCitta) {
			c.setRilevamenti(dao.getRilevamentiLocalitaMese(mese, c));
		}
		
		//Double costo = calcolaCosto(parziale);
		cerca(parziale, 0);
		return migliore;		
		
	}
	
	

	private void cerca(List<Citta> parziale, int livello) {
		
		if(livello == NUMERO_GIORNI_TOTALI) {
			//caso terminale
			Double costo = calcolaCosto(parziale);
			if(migliore == null || costo < calcolaCosto(migliore)) {
				migliore = new ArrayList<>(parziale);
				this.bestCosto = costo;
			}
		}else {
			
			//caso intermedio
			for(Citta prova : leCitta) {
				
				if(aggiuntaValida(prova, parziale)) {
					
					parziale.add(prova);
					cerca(parziale, livello + 1);
					parziale.remove(parziale.size() - 1);
					
				}
				
			}
			
		}
	}

	private boolean aggiuntaValida(Citta prova, List<Citta> parziale) {
		//verifica max giorni
		int count = 0;
		for(Citta precedente : parziale) {
			if(precedente.equals(prova))//equals compara il nome
				count ++ ;
		}
		if(count >= NUMERO_GIORNI_CITTA_MAX)
			return false;
		
		//verifica giorni minimi
		if(parziale.size() == 0) {
			return true;
		}
		if(parziale.size() == 1 || parziale.size() == 2) {
			return parziale.get(parziale.size() - 1).equals(prova);
		}
		if(parziale.get(parziale.size() - 1).equals(prova))
			return true; //giorni successivi al 3
		if(parziale.get(parziale.size() - 1).equals(parziale.get(parziale.size() - 2)) && 
				parziale.get(parziale.size() - 2).equals(parziale.get(parziale.size() - 3)))
			return true;
		
		
		return false;
	}
	
	private Double calcolaCosto(List<Citta> parziale) {
		
		double costo = 0.0;
		
		for(int i = 1; i <= NUMERO_GIORNI_TOTALI; i++) {
			
			Citta c = parziale.get(i - 1);
			double umidita = c.getRilevamenti().get(i - 1).getUmidita();
			costo += umidita;
			
		}
		
		for(int i = 2; i <= NUMERO_GIORNI_TOTALI; i++) {
			
			if(!parziale.get(i - 1).equals(parziale.get(i - 2)))
				costo += COST;
			
		}
		
		return costo;
	}
	
	public Double getBestCosto(){
		return this.bestCosto;
	}
	
	

}
