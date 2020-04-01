package it.polito.tdp.lab04.DAO;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class TestDB {

	public static void main(String[] args) {

		/*
		 * 	This is a main to check the DB connection
		 */
		
		CorsoDAO cdao = new CorsoDAO();
		cdao.getTuttiICorsi();
		
		StudenteDAO sdao = new StudenteDAO();
		
		//Prova autocompletamento
		Studente s = null;
		
		s = sdao.autocompletamento(178079);
		
		System.out.println(s);
		
		//Prova da codice insegnamento tutti gli iscritti (?)
		//String cod = "01KSUPG";
		
		Corso c = new Corso("01KSUPG", null, null, null);
		c = cdao.getCorso(c);
		
		List<Studente> studDelCorso; // = new LinkedList<>();
		studDelCorso = cdao.getStudentiIscrittiAlCorso(c);
		
		for(Studente st : studDelCorso) {
			System.out.println(st+"\n");
		}
		
		//Prova di corsi a cui e' iscritto uno studente
		
		List<Corso> corsiDelloStud = sdao.corsiDelloStudente(111111);
		
		for(Corso co : corsiDelloStud) {
			System.out.println(co+"\n");
		}
	}

}
