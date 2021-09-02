package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Funcionario;

public class MainController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemOperacoesVenda;

    @FXML
    private MenuItem menuItemOperacoesTransferencia;

    @FXML
    private MenuItem menuItemCadatrosProduto;

    @FXML
    private MenuItem menuItemCadatrosSetor;

    @FXML
    private MenuItem menuItemCadatroFuncionario;

    @FXML
    private MenuItem menuItemImprimir;

    @FXML
    private BorderPane anchorpane;

    private Stage stage;

    private Funcionario funcionario;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cadastrarProduto() {
        try {
            this.mostrarTela("/fxml/Material.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cadastrarSetor() {
        try {
            this.mostrarTela("/fxml/Setor.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cadastrarFuncionario() {
        try {
            this.mostrarTela("/fxml/Funcionario.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimirRelatorio() {
        try {
            this.mostrarTela("/fxml/relatorioImprimir.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void operacaoVenda() {
        try {
            this.mostrarTela("/fxml/Venda.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void operacaoTranferencia() {
        try {
            this.mostrarTela("/fxml/NovaTransferencia.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void estoque() {
        try {
            this.mostrarTela("/fxml/estoque.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verTransferencias() {
        try {
            this.mostrarTela("/fxml/Transferencia.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarTela(String frame) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(frame));
        AnchorPane a = (AnchorPane) loader.load();

        if (frame.equals("/fxml/estoque.fxml")) {
            EstoqueController controller = loader.getController();

            controller.init(this.getFuncionario());
        } else if (frame.equals("/fxml/NovaTransferencia.fxml")) {
            NovaTransferenciaController controller = loader.getController();
            controller.setFuncionarioLogado(this.getFuncionario());
            controller.init();            
        } else if(frame.equals("/fxml/Transferencia.fxml")) {
            TransferenciaController controller = loader.getController();
            controller.setFuncionarioLogado(this.getFuncionario());   
            controller.setPainelPrincipal(this.anchorpane);   
            
            controller.carregarComboBox();
        } else if(frame.equals("/fxml/Venda.fxml")) {
            VendaController controller = loader.getController();
            controller.setFuncionarioLogado(this.getFuncionario()); 
            controller.init();
        }
        this.anchorpane.setCenter(a);
    }

    public void start() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
