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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Setor;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class SetorController implements Initializable {

    @FXML
    private TableView<Setor> tblSetor;

    @FXML
    private TableColumn<Setor, String> tblColumCodigo;

    @FXML
    private TableColumn<Setor, String> tblColumSetor;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblSetor;

    @FXML
    private Label lblAbreviatura;

    @FXML
    private TextArea txtDescricao;

    List<Setor> setores;
    ObservableList<Setor> obsSetor;

    EntityManagerFactory emf;
    EntityManager em;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setores = new ArrayList<>();

        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();

        this.txtDescricao.setEditable(false);
        this.atualizarTabela(this.listar());

        this.tblSetor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.detallhar(newValue));

        this.txtBuscar.setOnKeyReleased((event) -> {
            this.buscar(event);
        });

        this.tblSetor.getSelectionModel().selectFirst();
    }

    @FXML
    public void adicionar() {
        Setor setor = new Setor();
        if (this.mostrarTelaNovoSetor(setor)) {
            em.getTransaction().begin();
            em.merge(setor);
            em.getTransaction().commit();
            //emf.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Setor Adicionado Com Sucesso");
            alert.show();
            this.atualizarTabela(this.listar());
        }
    }

    public List<Setor> listar() {
        em.getTransaction().begin();
        Query consulta = em.createQuery("Select setor from Setor setor");
        List<Setor> setor = consulta.getResultList();

        em.getTransaction().commit();
        //emf.close();                

        return setor;
    }

    @FXML
    public void editar() {
        Setor setor = this.tblSetor.getSelectionModel().getSelectedItem();
        if (setor != null) {
            if (this.mostrarTelaNovoSetor(setor)) {
                em.getTransaction().begin();
                em.merge(setor);
                em.getTransaction().commit();
                //emf.close();                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Setor Editado Com Sucesso");
                alert.show();    
                this.atualizarTabela(this.listar());
                this.tblSetor.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhun Setor Selecionado");
            alert.show();
        }
    }

    @FXML
    public void remover() {
        Setor setor = this.tblSetor.getSelectionModel().getSelectedItem();
        if (setor != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Este Setor Será Excuido Permanentemente");
            alert.showAndWait();

            if (alert.getResult().getText().equals("OK")) {
                em.remove(em.find(Setor.class, setor.getId()));

                this.atualizarTabela(this.listar());
                this.tblSetor.getSelectionModel().selectFirst();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhun Material Selecionado");
            alert.show();
        }
    }

    private void buscar(KeyEvent event) {
        // List<Setor> setores = this.setorDao.buscar(this.txtBuscar.getText());
        //this.atualizarTabela(setores);
    }

    private void detallhar(Setor setor) {
        if (setor != null) {
            this.lblCodigo.setText(String.valueOf(setor.getId()));
            this.lblSetor.setText(setor.getNome());
            this.lblAbreviatura.setText(setor.getAbreviacao());
            this.txtDescricao.setText(setor.getDescricao());
        }
    }

    private void atualizarTabela(List<Setor> setores) {
        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<Setor, String>("id"));
        this.tblColumSetor.setCellValueFactory(new PropertyValueFactory<Setor, String>("nome"));

        this.setores = setores;
        this.obsSetor = FXCollections.observableArrayList(this.setores);

        this.tblSetor.setItems(obsSetor);
    }

    private boolean mostrarTelaNovoSetor(Setor setor) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/fxml/NovoSetor.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            NovoSetorController controller = loader.getController();
            controller.setSetor(setor);
            controller.setStage(stage);
            controller.preencheCampos();

            stage.showAndWait();

            return controller.isConfirmar();
        } catch (IOException ex) {
            Logger.getLogger(SetorController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

}
