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
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estoque;
import model.Funcionario;
import model.Material;

/**
 * FXML Controller class
 *
 * @author joaov
 */
public class EstoqueController implements Initializable {

    @FXML
    private TableView<Estoque> tblEstoque;

    @FXML
    private TableColumn<Estoque, String> tblColumCodigo;

    @FXML
    private TableColumn<Estoque, String> tblColumNome;

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

    @FXML
    private Label lblQuantidade;

    private Funcionario funcionarioLogado;

    private List<Estoque> estoques;
    private ObservableList<Estoque> obsEstoques;

    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setEstoques(new ArrayList<>());

        this.getTblEstoque().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.detalhar(newValue.getMaterial()));

        this.setEstoques(this.listar());

        this.atualizarTabela(this.getEstoques());

        this.getTblEstoque().getSelectionModel().selectFirst();
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

    public List<Estoque> listar() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        em.getTransaction().begin();
        Query consulta = em.createQuery("SELECT * FROM Estoque WHERE Estoque.idSetor = " + this.getFuncionarioLogado().getSetor().getId());
        List<Estoque> estoques = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return estoques;
    }

    private void atualizarTabela(List<Estoque> estoques) {
        this.tblColumNome.setCellValueFactory(new PropertyValueFactory<Estoque, String>("nome"));
        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<Estoque, String>("id"));

        this.setEstoques(estoques);
        this.obsEstoques = FXCollections.observableArrayList(this.getEstoques());
        this.tblEstoque.setItems(obsEstoques);
    }
    
    private boolean mostrarTelaNovoEstoque(Estoque estoque) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(this.getClass().getResource("/fxml/NovoEstoque.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            NovoEstoqueController controller = loader.getController();
//            controller.setEstoque(estoque);
//            controller.setStage(stage);
//            controller.preencheCampos();

            stage.showAndWait();

            //return controller.isConfirmar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @FXML
    void adicionar(ActionEvent event) {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());
        
        Estoque estoque = new Estoque();
        if (this.mostrarTelaNovoEstoque(estoque)) {
            //funcionario.setSetor(this.getFuncionario().getSetor());
            em.getTransaction().begin();
            em.merge(estoque);
            em.getTransaction().commit();
            emf.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Material adicionado ao estoque com sucesso");
            alert.show();
            this.atualizarTabela(this.listar());
        }
    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void remover(ActionEvent event) {

    }

    /**
     * @return the tblEstoque
     */
    public TableView<Estoque> getTblEstoque() {
        return tblEstoque;
    }

    /**
     * @param tblEstoque the tblEstoque to set
     */
    public void setTblEstoque(TableView<Estoque> tblEstoque) {
        this.tblEstoque = tblEstoque;
    }

    /**
     * @return the tblColumCodigo
     */
    public TableColumn<Estoque, String> getTblColumCodigo() {
        return tblColumCodigo;
    }

    /**
     * @param tblColumCodigo the tblColumCodigo to set
     */
    public void setTblColumCodigo(TableColumn<Estoque, String> tblColumCodigo) {
        this.tblColumCodigo = tblColumCodigo;
    }

    /**
     * @return the tblColumNome
     */
    public TableColumn<Estoque, String> getTblColumNome() {
        return tblColumNome;
    }

    /**
     * @param tblColumNome the tblColumNome to set
     */
    public void setTblColumNome(TableColumn<Estoque, String> tblColumNome) {
        this.tblColumNome = tblColumNome;
    }

    /**
     * @return the edtBusca
     */
    public TextField getEdtBusca() {
        return edtBusca;
    }

    /**
     * @param edtBusca the edtBusca to set
     */
    public void setEdtBusca(TextField edtBusca) {
        this.edtBusca = edtBusca;
    }

    /**
     * @return the lblDescricao
     */
    public TextArea getLblDescricao() {
        return lblDescricao;
    }

    /**
     * @param lblDescricao the lblDescricao to set
     */
    public void setLblDescricao(TextArea lblDescricao) {
        this.lblDescricao = lblDescricao;
    }

    /**
     * @return the lblValor
     */
    public Label getLblValor() {
        return lblValor;
    }

    /**
     * @param lblValor the lblValor to set
     */
    public void setLblValor(Label lblValor) {
        this.lblValor = lblValor;
    }

    /**
     * @return the lblUnidade
     */
    public Label getLblUnidade() {
        return lblUnidade;
    }

    /**
     * @param lblUnidade the lblUnidade to set
     */
    public void setLblUnidade(Label lblUnidade) {
        this.lblUnidade = lblUnidade;
    }

    /**
     * @return the lblNome
     */
    public Label getLblNome() {
        return lblNome;
    }

    /**
     * @param lblNome the lblNome to set
     */
    public void setLblNome(Label lblNome) {
        this.lblNome = lblNome;
    }

    /**
     * @return the lblId
     */
    public Label getLblId() {
        return lblId;
    }

    /**
     * @param lblId the lblId to set
     */
    public void setLblId(Label lblId) {
        this.lblId = lblId;
    }

    /**
     * @return the lblQuantidade
     */
    public Label getLblQuantidade() {
        return lblQuantidade;
    }

    /**
     * @param lblQuantidade the lblQuantidade to set
     */
    public void setLblQuantidade(Label lblQuantidade) {
        this.lblQuantidade = lblQuantidade;
    }

    /**
     * @return the estoques
     */
    public List<Estoque> getEstoques() {
        return estoques;
    }

    /**
     * @param estoques the estoques to set
     */
    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    /**
     * @return the obsEstoques
     */
    public ObservableList<Estoque> getObsEstoques() {
        return obsEstoques;
    }

    /**
     * @param obsEstoques the obsEstoques to set
     */
    public void setObsEstoques(ObservableList<Estoque> obsEstoques) {
        this.obsEstoques = obsEstoques;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * @param emf the emf to set
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * @return the em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * @return the funcionarioLogado
     */
    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    /**
     * @param funcionarioLogado the funcionarioLogado to set
     */
    public void setFuncionarioLogado(Funcionario funcionarioLogado) {
        this.funcionarioLogado = funcionarioLogado;
    }

}
