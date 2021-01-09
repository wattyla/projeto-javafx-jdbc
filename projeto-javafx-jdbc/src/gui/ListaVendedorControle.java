package gui;

import java.net.URL;
import java.util.Date;
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
import model.dao.VendedorDao;
import model.entidades.Vendedor;

public class ListaVendedorControle implements Initializable {
	
	private VendedorDao vendedorDao;
	
	private  ObservableList<Vendedor> obsListaVendedor;
	
	@FXML
	private TableView<Vendedor> tableViewVendedor;
	
	@FXML
	private TableColumn<Vendedor, Integer> tableColumnId;
	@FXML
	private TableColumn<Vendedor, String> tableColumnName;
	@FXML
	private TableColumn<Vendedor, String> tableColumnEmail;
	@FXML
	private TableColumn<Vendedor, Date> tableColumnDataNascimento;
	@FXML
	private TableColumn<Vendedor, Double> tableColumnSalarioBase;
	@FXML
	private TableColumn<Vendedor, Integer> tableColumnDepartamentoId;
	
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
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		tableColumnSalarioBase.setCellValueFactory(new PropertyValueFactory<>("salarioBase"));
		tableColumnDepartamentoId.setCellValueFactory(new PropertyValueFactory<>("departamento"));
		
		Stage stage = (Stage) Main.getCenaPrincipal().getWindow();
		tableViewVendedor.prefHeightProperty().bind(stage.heightProperty());
	}

	public void setVendedorDao(VendedorDao vendedorDao) {
		this.vendedorDao = vendedorDao;
	}

	public void atualizaTableViewVendedor() {
		if (vendedorDao == null) {
			throw new DbException("O departamentoDao ainda esta null");
		}
		
		List<Vendedor> listaVendedor = vendedorDao.cunsultaTodos();
		
		obsListaVendedor = FXCollections.observableArrayList(listaVendedor);
		
		tableViewVendedor.setItems(obsListaVendedor);
	}
}
