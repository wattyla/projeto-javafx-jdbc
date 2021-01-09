package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.dao.DaoFabrica;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemVendedor;
	@FXML
	private MenuItem menuItemDepartamento;
	@FXML
	private MenuItem menuItemSobre;

	@FXML
	public void onMenuItemVendedorAction() {
		carregarTela("/gui/ListaVendedor.fxml", (ListaVendedorControle controle) -> {
			controle.setVendedorDao(DaoFabrica.criaVendedorDao());
			controle.atualizaTableViewVendedor();
		});
	}

	@FXML
	public void onMenuItemDepartamentoAction() {
		carregarTela("/gui/ListaDepartamento.fxml", (ListaDepartamentoControle controle) -> {
			controle.setDepartamentoDao(DaoFabrica.criaDepartamentoDao());
			controle.atualizaTableViewDepartamento();
		});
	}

	@FXML
	public void onMenuItemSobreAction() {
		carregarTela("/gui/Sobre.fxml", x -> {});
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	private synchronized <T> void carregarTela(String nameCompleto, Consumer<T> acaoInicializacao) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nameCompleto));
			VBox novoVBox = loader.load();
			
			Scene mainScene = Main.getCenaPrincipal();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(novoVBox.getChildren());	
			
			T controle = loader.getController();
			acaoInicializacao.accept(controle);
			
		} catch (IOException e) {
			Alertas.showAlert("IO Exception","Erro ao carrega a tela", e.getMessage(), AlertType.ERROR);
		}
	}
}
