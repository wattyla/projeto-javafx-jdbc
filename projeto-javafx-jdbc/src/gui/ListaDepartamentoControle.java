package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

public class ListaDepartamentoControle implements Initializable {

	private DepartamentoDao departamentoDao;
	
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
	public void onBottaoNovoAction() {
		System.out.println("onBottaoNovoAction");
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

	public void setDepartamentoDao(DepartamentoDao departamentoDao) {
		this.departamentoDao = departamentoDao;
	}

	public void atualizaTableViewDepartamento() {
		if (departamentoDao == null) {
			throw new DbException("O departamentoDao ainda esta null");
		}
		
		List<Departamento> listaDepartamentos = departamentoDao.cunsultaTodos();
		
		obsListaDepartamento = FXCollections.observableArrayList(listaDepartamentos);
		
		tableViewDepartamento.setItems(obsListaDepartamento);
	}
}
