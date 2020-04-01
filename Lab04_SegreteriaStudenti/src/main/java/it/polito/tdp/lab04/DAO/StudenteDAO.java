package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	/**
	 * Funzione che restituisce tutte le informazioni di uno studente data la sua matricola
	 * ritorna null se lo studente cercato non e' presente nel DB
	 * 
	 * @param matricola
	 * @return
	 */
	public Studente autocompletamento(Integer matricola) {
		
		String sql ="SELECT * FROM studente WHERE matricola=?";
		
		Studente ret = null; 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				ret = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), 
						rs.getString("CDS"));
			}
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ret;
	}
	
	public List<Corso> corsiDelloStudente(Integer matricola) {
		
		String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM corso AS c, iscrizione AS i, studente AS s " + 
				"WHERE c.codins = i.codins AND i.matricola = s.matricola AND i.matricola=?";
		
		List<Corso> ret = new LinkedList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				ret.add(c);
			}
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return ret;
	}

}
