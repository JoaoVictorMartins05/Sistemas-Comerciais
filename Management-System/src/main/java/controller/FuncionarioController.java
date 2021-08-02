/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Funcionario;

/**
 * FXML Controller class
 *
 * @author elisson
 */

public class FuncionarioController implements Initializable {

    @FXML
    private AnchorPane anchorpane;
        
    @FXML
    private TableView<Funcionario> tblfuncionario;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, String> colUsuario;

    @FXML
    private TableColumn<Funcionario, String> colNivel;

    @FXML
    private TextField txtPesquisa;

    private Funcionario funcionario;
    private List<Funcionario> funcionarios;
    private ObservableList<Funcionario> obsFuncionarios;

    EntityManagerFactory emf;
    EntityManager em;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFuncionarios(new ArrayList<>());
        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();

//        this.tblfuncionario.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, newValue) -> this.detallhar(newValue));
        this.txtPesquisa.setOnKeyReleased((event) -> {
            this.buscar(event);
        });

        this.setFuncionarios(this.listar());

        this.atualizarTabela(this.getFuncionarios());

        this.tblfuncionario.getSelectionModel().selectFirst();
    }

    @FXML
    public void adicionar() {
        Funcionario funcionario = new Funcionario();
        if (this.mostrarTelaNovoFuncionario(funcionario)) {
            //funcionario.setSetor(this.getFuncionario().getSetor());
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
            //emf.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Funcionário adicionado com sucesso");
            alert.show();
            this.atualizarTabela(this.listar());
        }
    }

    public List<Funcionario> listar() {
        em.getTransaction().begin();
        Query consulta = em.createQuery("Select funcionario from Funcionario funcionario");
        List<Funcionario> funcionarios = consulta.getResultList();

        em.getTransaction().commit();
        //emf.close();

        return funcionarios;
    }

    @FXML
    public void editar() {
        Funcionario funcionario = this.tblfuncionario.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            if (this.mostrarTelaNovoFuncionario(funcionario)) {
                em.getTransaction().begin();
                em.merge(funcionario);
                em.getTransaction().commit();
                //emf.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Funcionario atualizado com sucesso");
                alert.show();
                this.atualizarTabela(this.listar());
                this.tblfuncionario.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhun setor selecionado");
            alert.show();
        }
    }

    @FXML
    public void remover() {
        Funcionario funcionario = this.tblfuncionario.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Deseja realmente excluir o funcionário " + funcionario.getNome());
            alert.showAndWait();

            if (alert.getResult().getText().equals("OK")) {
                em.remove(em.find(Funcionario.class, funcionario.getId()));
                this.atualizarTabela(this.listar());
                this.tblfuncionario.getSelectionModel().selectFirst();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhun Funcionário Selecionado");
            alert.show();
        }
    }

    private void buscar(KeyEvent event) {
        //List<Funcionario> funcionarios = this.funcionarioDao.buscar(this.txtPesquisa.getText());
        //this.atualizarTabela(funcionarios);
    }

    private void atualizarTabela(List<Funcionario> funcionarios) {
        //Definicao de qual campo com qual coluna do BD
        this.colNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
        this.colUsuario.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("usuario"));
        this.colNivel.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nivelAcesso"));

        //Criar um observableCollection com os dados advindos do BD
        this.setFuncionarios(funcionarios);
        this.obsFuncionarios = FXCollections.observableArrayList(this.getFuncionarios());
        this.tblfuncionario.setItems(obsFuncionarios);
    }

    private boolean mostrarTelaNovoFuncionario(Funcionario funcionario) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/fxml/NovoFuncionario.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            NovoFuncionarioController controller = loader.getController();
            controller.setFuncionario(funcionario);
            controller.setStage(stage);
            controller.preencheCampos();

            stage.showAndWait();

            return controller.isConfirmar();
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * @return the funcionarios
     */
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    /**
     * @param funcionarios the funcionarios to set
     */
    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
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
