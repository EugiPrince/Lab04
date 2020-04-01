/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCorso"
    private ComboBox<Corso> boxCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscritti"
    private Button btnCercaIscritti; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="btnOK"
    private Button btnOK; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML
    void autocompletamento(ActionEvent event) {
    	Integer matricola;
    	String mat = this.txtMatricola.getText();
    	Studente studente;
    	
    	try {
    		matricola = Integer.parseInt(mat);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire una matricola con caratteri validi!");
    		return;
    	}
    	
    	studente = this.model.autocompletamento(matricola);
    	
    	if(studente!=null) {
    		this.txtCognome.setText(studente.getCognome());
    		this.txtNome.setText(studente.getNome());
    	}
    	else {
    		this.txtResult.setText("La matricola cercata non e' presente nel database");
    		this.txtCognome.setText("");
    		this.txtNome.setText("");
    	}
    }

    @FXML
    void cercaCorsiMatricola(ActionEvent event) {
    	this.txtResult.clear();
    	Integer matricola;
    	String mat = this.txtMatricola.getText();
    	Studente stud;
    	List<Corso> corsiDelloStud;
    	
    	try {
    		matricola = Integer.parseInt(mat);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire una matricola con caratteri validi!");
    		return;
    	}
    	
    	stud = this.model.autocompletamento(matricola);
    	if(stud!=null) {
    		corsiDelloStud = this.model.corsiDelloStudente(stud.getMatricola());
    		
    		if(corsiDelloStud.size()==0)
    			this.txtResult.appendText("La matricola cercata non e' iscritta a nessun corso");
    		
    		for(Corso co : corsiDelloStud)
    			this.txtResult.appendText(co+"\n");
    	}
    	else {
    		this.txtResult.setText("La matricola cercata non e' presente nel database!");
    		this.txtCognome.setText("");
    		this.txtNome.setText("");
    		return;
    	}
    }

    @FXML
    void cercaIscrittiCorso(ActionEvent event) {
    	this.txtResult.clear();
    	Corso c = this.boxCorso.getValue();
    	
    	if(c==null || c.getCodins().compareTo("")==0) {
    		this.txtResult.setText("Non hai selezionato nessun corso");
    		return;
    	}
    	
    	List<Studente> studentiIscrittiAlCorso = this.model.studentiIscrittiAlCorso(c);
    	for(Studente s : studentiIscrittiAlCorso)
    		this.txtResult.appendText(s+"\n");
    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtResult.clear();
    }

    @FXML
    void iscriviMatricola(ActionEvent event) {
    	this.txtResult.clear();
    	Integer matricola;
    	String mat = this.txtMatricola.getText();
    	Studente stud;
    	Corso cor;
    	List<Corso> corsiDelloStud;
    	boolean iscritto = false;
    	
    	cor = this.boxCorso.getValue();
    	
    	if(cor==null || cor.getCodins().compareTo("")==0) {
    		this.txtResult.setText("Non hai selezionato nessun corso");
    		return;
    	}
    	
    	try {
    		matricola = Integer.parseInt(mat);
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Devi inserire una matricola con caratteri validi!");
    		return;
    	}
    	
    	stud = this.model.autocompletamento(matricola);
    	if(stud!=null) {
    		corsiDelloStud = this.model.corsiDelloStudente(stud.getMatricola());
    		
    		if(corsiDelloStud.contains(cor)) {
    			this.txtResult.setText("La matricola inserita e' gia iscritta al corso");
    			return;
    		}
    		else {
    			iscritto = this.model.iscriviStudenteCorso(stud, cor);
    		}
    	}
    	else {
    		this.txtResult.setText("La matricola cercata non e' presente nel database!");
    		this.txtCognome.setText("");
    		this.txtNome.setText("");
    		return;
    	}
    	
    	if(iscritto) {
    		this.txtResult.setText("Studente iscritto al corso");
    	}
    	else
    		this.txtResult.setText("Errore");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCorso != null : "fx:id=\"boxCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnOK != null : "fx:id=\"btnOK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
	public void setModel(Model model) {
		this.model = model;
		
		List<Corso> tuttiICorsi = this.model.tuttiICorsi();
		tuttiICorsi.add(new Corso());
		this.boxCorso.getItems().addAll(tuttiICorsi);
	}
    
    
}


