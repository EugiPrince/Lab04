package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
			}
			conn.close();
			
			return corsi;
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) { // qui c'era Corso corso, ma secondo me non ha senso
		
		String sql = "SELECT * FROM corso WHERE codins = ?";
		Corso c = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			}
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return c;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS " + 
				"FROM studente AS s, corso AS c, iscrizione AS i " + 
				"WHERE s.matricola = i.matricola AND c.codins = i.codins AND i.codins = ? ";
				
		List<Studente> studenti = new LinkedList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("CDS"));
				studenti.add(s);
			}
			conn.close();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		return studenti;
	}

	/**
	 * Dato uno {@link Studente} e un {@link Corso} iscrive lo studente al relativo corso, se cio' e' possibile
	 * 
	 * @param studente Studente da iscrivere
	 * @param corso Corso a cui iscrivere lo studente
	 * @return {@code true} nel caso di avvenuta iscrizione, {@code false} in caso contrario
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {

		String sql = "INSERT INTO iscrizione (matricola, codins) VALUES(?, ?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			st.executeQuery();
			
			conn.close();
			return true;
			
		} catch(SQLException e) {
			return false;
		}
	}

}
