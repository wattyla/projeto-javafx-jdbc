package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import gui.ouvintes.OuvinteMudancaDados;
import gui.util.Alertas;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Departamento;
import model.servico.ServicoDepartamento;

public class ListaDepartamentoControle implements Initializable, OuvinteMudancaDados {

	private ServicoDepartamento servicoDepartamento;
	
	private  ObservableList<Departamento> obsListaDepartamento;
	@FXML
	private TableView<Departamento> tableViewDepartamento;
	
	@FXML
	private TableColumn<Departamento, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Departamento, String> tableColumnName;
	
	@FXML
	private Button bottaoNovo;
	
	@FXML
	public void onBottaoNovoAction(ActionEvent evento) {
		Stage palcoPai = Utils.palcoAtual(evento);
		Departamento departamento = new Departamento();
		criarFormularioDialogo(departamento,"/gui/FormularioDepartamento.fxml",palcoPai);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {	
		inicializaNodes();
	}

	private void inicializaNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
	}

	public void setServicoDepartamento(ServicoDepartamento servicoDepartamento) {
		this.servicoDepartamento = servicoDepartamento;
	}

	public void atualizaTableViewDepartamento() {
		if (servicoDepartamento == null) {
			throw new DbException("O servicoDepartamento ainda esta null");
		}
		
		List<Departamento> listaDepartamentos = servicoDepartamento.retornaTodosDeparamentos();
		
		obsListaDepartamento = FXCollections.observableArrayList(listaDepartamentos);
		
		tableViewDepartamento.setItems(obsListaDepartamento);
	}
	
	private void criarFormularioDialogo(Departamento departamento, String nameCompleto, Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nameCompleto));
			Pane pane = loader.load();
			
			FormularioDepartamentoControle formularioDepartamentoControle = loader.getController();
			formularioDepartamentoControle.setDepartamento(departamento);
			formularioDepartamentoControle.tornarOuvinteMudancaDados(this);
			formularioDepartamentoControle.setServicoDepartamento(new ServicoDepartamento());
			formularioDepartamentoControle.atulizaDadosFormulario();
			
			Stage palcoDialogo = new Stage();
			palcoDialogo.setTitle("Entre com dados departamento");
			palcoDialogo.setScene(new Scene(pane));
			palcoDialogo.setResizable(false);
			palcoDialogo.initOwner(stage);
			palcoDialogo.initModality(Modality.WINDOW_MODAL);
			palcoDialogo.showAndWait();
			
		} catch (IOException e) {
			Alertas.showAlert("Erro de leitura/Escrita", "Erro ao carregar a tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onMudancaDados() {
		atualizaTableViewDepartamento();
	}
}
