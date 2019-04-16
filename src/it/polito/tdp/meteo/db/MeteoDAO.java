package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Citta> getCitta(){
		
		final String sql = "SELECT DISTINCT Localita FROM situazione ORDER BY Localita";
		
		List<Citta> citta = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Citta c = new Citta(rs.getString("Localita"));
				citta.add(c);
				
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	


	public List</*Rilevamento*/String> getAllRilevamentiLocalitaMese(int mese/*, String localita*/) {
		
	
		List<String> medie = new ArrayList<String>();
		
		final String sql =  "SELECT Localita, AVG(Umidita) AS media " + 
							"FROM situazione " + 
							"WHERE MONTH(DATA) = ? " + 
							"GROUP BY Localita";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, mese);		

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				//Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				//rilevamenti.add(r);
				String s = rs.getString("Localita")+" "+rs.getDouble("media");
				medie.add(s);
				
			}

			conn.close();
			return medie;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

		//return null;
	}
	
	public List<Rilevamento> getRilevamentiLocalitaMese(Month mese, Citta localita){
		
final String sql = "SELECT Localita, Data, Umidita FROM situazione WHERE MONTH(Data)=? AND Localita=? ORDER BY data ASC";
		
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, mese.getValue());
			st.setString(2, localita.getNome());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	

}
