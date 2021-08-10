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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Funcionario;
import model.Material;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class MaterialController implements Initializable {

    @FXML
    private TableView<Material> tblMaterial;

    @FXML
    private TableColumn<Material, String> tblColumCodigo;

    @FXML
    private TableColumn<Material, String> tblColumNome;

    @FXML
    private TextField edtBusca;

    @FXML
    private TextArea lblDescricao;

    @FXML
    private Label lblValor;

    @FXML
    private Label lblUnidade;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblId;

    private List<Material> materiais;
    private ObservableList<Material> obsMateriais;

    EntityManagerFactory emf;
    EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setMateriais(new ArrayList<>());        

        this.tblMaterial.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.detalhar(newValue));
       
        this.setMateriais(this.listar());

        this.atualizarTabela(this.getMateriais());

        this.tblMaterial.getSelectionModel().selectFirst();
    }

    @FXML
    void adicionar(ActionEvent event) throws IOException {

        Material material = new Material();
        if (this.mostrarTelaNovoMaterial(material)) {
            emf = Persistence.createEntityManagerFactory("venda");
            em = emf.createEntityManager();
            //funcionario.setSetor(this.getFuncionario().getSetor());
            em.getTransaction().begin();
            em.merge(material);
            em.getTransaction().commit();
            emf.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Material adicionado com sucesso");
            alert.show();
            this.atualizarTabela(this.listar());
        }
    }

    @FXML
    public void editar() {
        Material material = this.tblMaterial.getSelectionModel().getSelectedItem();
        if (material != null) {
            if (this.mostrarTelaNovoMaterial(material)) {
                emf = Persistence.createEntityManagerFactory("venda");
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.merge(material);
                em.getTransaction().commit();
                emf.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Material atualizado com sucesso");
                alert.show();
                this.atualizarTabela(this.listar());
                this.tblMaterial.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhun Material selecionado");
            alert.show();
        }
    }

    @FXML
    public void remover() {
        Material material = this.tblMaterial.getSelectionModel().getSelectedItem();
        if (material != null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Deseja realmente excluir o material " + material.getNome());
            alert.showAndWait();

            if (alert.getResult().getText().equals("OK")) {
                emf = Persistence.createEntityManagerFactory("venda");
                em = emf.createEntityManager();
                em.remove(em.find(Material.class, material.getId()));
                emf.close();
                this.atualizarTabela(this.listar());
                this.tblMaterial.getSelectionModel().selectFirst();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum Material Selecionado");
            alert.show();
        }
    }

    private void detalhar(Material material) {
        if (material != null) {
            this.lblId.setText(String.valueOf(material.getId()));
            this.lblNome.setText(material.getNome());
            this.lblUnidade.setText(material.getUnidadeMedida());
            this.lblValor.setText(String.valueOf(material.getValor()));
            this.lblDescricao.setText(material.getDescricao());
        }
    }

    private boolean mostrarTelaNovoMaterial(Material material) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/fxml/NovoMaterial.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            NovoMaterialController controller = loader.getController();
            controller.setMaterial(material);
            controller.setStage(stage);
            controller.preencheCampos();

            stage.showAndWait();

            return controller.isConfirmar();
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Material> listar() {
        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();
        em.getTransaction().begin();
        Query consulta = em.createQuery("Select material from Material material");
        List<Material> materiais = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return materiais;
    }

    private void atualizarTabela(List<Material> material) {
        //Definicao de qual campo com qual coluna do BD
        this.tblColumNome.setCellValueFactory(new PropertyValueFactory<Material, String>("nome"));
        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<Material, String>("id"));

        //Criar um observableCollection com os dados advindos do BD
        this.setMateriais(material);
        this.obsMateriais = FXCollections.observableArrayList(this.getMateriais());
        this.tblMaterial.setItems(obsMateriais);
    }

    /**
     * @return the materiais
     */
    public List<Material> getMateriais() {
        return materiais;
    }

    /**
     * @param materiais the materiais to set
     */
    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

}
