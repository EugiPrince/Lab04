package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;
	
	public Model() {
		this.corsoDAO = new CorsoDAO();
		this.studenteDAO = new StudenteDAO();
	}

	public List<Corso> tuttiICorsi() {
		return this.corsoDAO.getTuttiICorsi();
	}
	
	public Studente autocompletamento(Integer matricola) {
		return this.studenteDAO.autocompletamento(matricola);
	}
	
	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
		return this.corsoDAO.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> corsiDelloStudente(Integer matricola) {
		return this.studenteDAO.corsiDelloStudente(matricola);
	}
	
	public boolean iscriviStudenteCorso(Studente studente, Corso corso) {
		return this.corsoDAO.inscriviStudenteACorso(studente, corso);
	}
}
