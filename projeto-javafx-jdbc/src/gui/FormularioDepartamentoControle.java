package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Departamento;

public class FormularioDepartamentoControle implements Initializable{

	private Departamento departamento;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrosNome;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private Button buttonCancelar;
	
	@FXML
	public void onButtonSaveAction() {
		System.out.println("onButtonSaveAction");
	}
	
	@FXML
	public void onButtonCancelarAction() {
		System.out.println("onButtonCancelarAction");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		incializaNodes();
	}
	
	private void incializaNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtName, 30);
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void atulizaDadosFormulario() {
		if (departamento == null) {
			throw new IllegalStateException("Departamento nulo");
		}
		txtId.setText(String.valueOf(departamento.getId()));
		txtName.setText(departamento.getName());
	}
}
