package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.ouvintes.OuvinteMudancaDados;
import gui.util.Alertas;
import gui.util.Restricoes;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Departamento;
import model.servico.ServicoDepartamento;

public class FormularioDepartamentoControle implements Initializable{

	private Departamento departamento;
	
	private ServicoDepartamento servicoDepartamento;
	
	private List<OuvinteMudancaDados> listaOuvinteMudancaDados = new ArrayList<>();
	
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
	public void onButtonSaveAction(ActionEvent evento) {
		if(departamento == null) {
			throw new IllegalStateException("Departamento do formulario é null");
		}
		if (servicoDepartamento == null) {
			throw new IllegalStateException("Servico Departamento do formulario é null");
		}
		try {
			departamento = getFormData();
			servicoDepartamento.insereOuAtualizaDepartamento(departamento);
			notificaOuvintesMudancaDados();
			Utils.palcoAtual(evento).close();
		} catch (DbException e) {
			Alertas.showAlert("Erro ao salvar o Departamento", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificaOuvintesMudancaDados() {
		for (OuvinteMudancaDados ouvinteMudancaDados : listaOuvinteMudancaDados) {
			ouvinteMudancaDados.onMudancaDados();
		}
	}

	private Departamento getFormData() {
		Departamento obj = new Departamento();
		obj.setId(Utils.intdaString(txtId.getText()));
		obj.setName(txtName.getText());
		
		return obj;
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
	
	public void setServicoDepartamento(ServicoDepartamento servicoDepartamento) {
		this.servicoDepartamento = servicoDepartamento;
	}

	public void tornarOuvinteMudancaDados(OuvinteMudancaDados ouvinte) {
		listaOuvinteMudancaDados.add(ouvinte);
	}
	
	public void atulizaDadosFormulario() {
		if (departamento == null) {
			throw new IllegalStateException("Departamento nulo");
		}
		txtId.setText(String.valueOf(departamento.getId()));
		txtName.setText(departamento.getName());
	}
}
