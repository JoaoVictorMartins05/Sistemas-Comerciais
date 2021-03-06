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
import model.visualization.EstoqueMaterial;

/**
 * FXML Controller class
 *
 * @author joaov
 */
public class EstoqueController implements Initializable {

    @FXML
    private TableView<EstoqueMaterial> tblEstoque;

    @FXML
    private TableColumn<EstoqueMaterial, String> tblColumCodigo;

    @FXML
    private TableColumn<EstoqueMaterial, String> tblColumQtd;

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
    private ObservableList<EstoqueMaterial> obsEstoques;

    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setEstoques(new ArrayList<>());

        this.getTblEstoque().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.ouvirTabela(newValue));

        this.getTblEstoque().getSelectionModel().selectFirst();
    }

    public void init(Funcionario funcionario) {
        this.setFuncionarioLogado(funcionario);
        this.setEstoques(this.listar());

        this.atualizarTabela();
    }
    
    private void ouvirTabela(EstoqueMaterial estoqueMaterial) {
        Material material = new Material();
        for(Estoque estoque : this.estoques) {
            if(estoque.getId().equals(estoqueMaterial.getId())) {
                material = estoqueMaterial.getMaterial(estoque);
                break;
            }
        }
        this.detalhar(material, estoqueMaterial);
    }

    private void detalhar(Material material, EstoqueMaterial estoqueMaterial) {
        if (material != null) {
            this.lblId.setText(String.valueOf(material.getId()));
            this.lblNome.setText(material.getNome());
            this.lblUnidade.setText(material.getUnidadeMedida());
            this.lblValor.setText(String.valueOf(material.getValor()));
            this.lblDescricao.setText(material.getDescricao());
            this.lblQuantidade.setText(Double.toString(estoqueMaterial.getQuantidade()));
        }
    }

    public List<Estoque> listar() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        em.getTransaction().begin();
        Query consulta = em.createNativeQuery("SELECT * FROM Estoque WHERE Estoque.idSetor = " + this.getFuncionarioLogado().getSetor().getId(), Estoque.class);
        List<Estoque> estoques = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return estoques;
    }

    private List<EstoqueMaterial> juntarEstoqueMaterial() {
        List<EstoqueMaterial> materiais = new ArrayList<>();
        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();

        for (Estoque estoque : this.estoques) {
            EstoqueMaterial estoqueMaterial = new EstoqueMaterial();

            em.getTransaction().begin();
            Query consulta = em.createNativeQuery("Select nome from material where material.id="+estoque.getMaterial().getId());
            List<String>  nomeMaterial = consulta.getResultList();
            em.getTransaction().commit();
            
            estoqueMaterial.setId(estoque.getId());
            estoqueMaterial.setNomeMaterial(nomeMaterial.get(0));
            estoqueMaterial.setQuantidade(estoque.getQuantidade());
            estoqueMaterial.setNomeSetor(this.funcionarioLogado.getSetor().getNome());
            
            materiais.add(estoqueMaterial);
        }
        emf.close();

        return materiais;
    }

    private void atualizarTabela() {
        this.tblColumQtd.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("quantidade"));
        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<EstoqueMaterial, String>("nomeMaterial"));

        this.obsEstoques = FXCollections.observableArrayList(this.juntarEstoqueMaterial());
        this.tblEstoque.setItems(obsEstoques);
        //this.tblEstoque.getSelectionModel().select(0);
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
            controller.setEstoque(estoque);
            controller.setStage(stage);
            controller.init(this.getFuncionarioLogado());

            stage.showAndWait();

            return controller.isConfirmar();
        } catch (IOException ex) {
            Logger.getLogger(FuncionarioController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @FXML
    void adicionar(ActionEvent event) {
        this.editar(true);
    }

    @FXML
    void remover(ActionEvent event) {
        this.editar(false);
    }

    private void editar(boolean flag) {
        Estoque estoque = new Estoque();

        if (this.tblEstoque.getSelectionModel().getSelectedItem() != null) {
            for(Estoque e : this.estoques) {
                if(e.getId().equals(this.tblEstoque.getSelectionModel().getSelectedItem().getId())) {
                    estoque = e;
                }
            }
        }

        if (this.mostrarTelaNovoEstoque(estoque)) {

            List<Estoque> estoques = this.listar();
            for (Estoque e : estoques) {
                if (estoque.getMaterial().getId().equals(e.getMaterial().getId())) {
                    double qtd = estoque.getQuantidade();
                    estoque = e;
                    if (flag) {
                        estoque.setQuantidade(estoque.getQuantidade() + qtd);
                    } else {
                        estoque.setQuantidade(estoque.getQuantidade() - qtd);
                    }
                }
            }
            setEmf(Persistence.createEntityManagerFactory("venda"));
            setEm(getEmf().createEntityManager());

            em.getTransaction().begin();
            em.merge(estoque);
            em.getTransaction().commit();
            emf.close();
            
            if(flag){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Materiais adicionados ao estoque com sucesso");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Materiais removidos do estoque com sucesso");
                alert.show();
            }                

            this.setEstoques(this.listar());

            for (Estoque e : this.estoques) {
                System.out.println(e.getMaterial().getNome());
            }
            this.atualizarTabela();
        }
    }

    /**
     * @return the tblEstoque
     */
    public TableView<EstoqueMaterial> getTblEstoque() {
        return tblEstoque;
    }

    /**
     * @param tblEstoque the tblEstoque to set
     */
    public void setTblEstoque(TableView<EstoqueMaterial> tblEstoque) {
        this.tblEstoque = tblEstoque;
    }

    /**
     * @return the tblColumCodigo
     */
    public TableColumn<EstoqueMaterial, String> getTblColumCodigo() {
        return tblColumCodigo;
    }

    /**
     * @param tblColumCodigo the tblColumCodigo to set
     */
    public void setTblColumCodigo(TableColumn<EstoqueMaterial, String> tblColumCodigo) {
        this.tblColumCodigo = tblColumCodigo;
    }

    /**
     * @return the tblColumNome
     */
    public TableColumn<EstoqueMaterial, String> getTblColumNome() {
        return tblColumQtd;
    }

    /**
     * @param tblColumNome the tblColumNome to set
     */
    public void setTblColumNome(TableColumn<EstoqueMaterial, String> tblColumNome) {
        this.tblColumQtd = tblColumNome;
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
    public ObservableList<EstoqueMaterial> getObsEstoques() {
        return obsEstoques;
    }

    /**
     * @param obsEstoques the obsEstoques to set
     */
    public void setObsEstoques(ObservableList<EstoqueMaterial> obsEstoques) {
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
