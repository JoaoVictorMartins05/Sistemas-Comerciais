/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estoque;
import model.Funcionario;
import model.Material;
import model.Setor;

/**
 * FXML Controller class
 *
 * @author joaov
 */
public class NovoEstoqueController implements Initializable {

    @FXML
    private Label lblUnidade;

    @FXML
    private ComboBox<Material> edtNome;

    @FXML
    private Label lblValor;

    @FXML
    private TextField edtQuantidade;

    @FXML
    private Label lblDescricao;

    private Stage stage;

    private Estoque estoque;

    private boolean confirmar;

    private Funcionario funcionarioLogado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.confirmar = false;

    }

    public void init(Funcionario funcionario) {
        this.setFuncionarioLogado(funcionario);
        this.preencheChoiceBox();
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.stage.close();
    }

    @FXML
    void confirmar(ActionEvent event) {
        if (validarCampos() == true) {
            this.preencherEstoque();
            this.setConfirmar(true);
            this.getStage().close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos!!");
            alert.show();
        }
    }

    private boolean validarCampos() {
        if (this.edtNome.getValue().equals("") || this.edtQuantidade.getText().equals("")) {
            return false;
        }
        return true;
    }

    private void preencherEstoque() {
        this.getEstoque().setMaterial(this.getEdtNome().getValue());
        this.getEstoque().setQuantidade(Double.parseDouble(this.getEdtQuantidade().getText()));
        this.getEstoque().setSetor(this.getFuncionarioLogado().getSetor());
    }

    public void preencheCampos(Material material) {
        this.lblDescricao.setText(material.getDescricao());
        this.lblUnidade.setText(material.getUnidadeMedida());
        this.lblValor.setText(Double.toString(material.getValor()));
    }

    private List<Material> listarMateriais() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("venda");
        EntityManager em = emf.createEntityManager();

        System.out.println();

        em.getTransaction().begin();
        Query consulta = em.createNativeQuery("SELECT * from material WHERE material.id = (SELECT DISTINCT idMaterial FROM Estoque JOIN setor ON Estoque.idSetor = " + this.getFuncionarioLogado().getSetor().getId() + ")", Material.class);

        List<Material> materiais = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return materiais;
    }

    @FXML
    private void detalhar(ActionEvent event) {
        Material material = this.edtNome.getValue();

        this.preencheCampos(material);
    }

    private void preencheChoiceBox() {
        ObservableList<Material> obsMateriais = FXCollections.observableArrayList(new MaterialController().listar());

        this.edtNome.setItems(obsMateriais);

        this.mostrarSoNomeMaterial();

        if (this.estoque.getId() == -1L) {
            this.edtNome.setValue(this.getEdtNome().getItems().get(0));
        } else {
            for (Material item : this.edtNome.getItems()) {
                if (item.getId().equals(this.estoque.getMaterial().getId())) {
                    this.edtNome.setValue(item);
                }
            }
        }

        this.detalhar(null);
    }

    private void mostrarSoNomeMaterial() {
        this.edtNome.setButtonCell(new ListCell<Material>() {
            @Override
            protected void updateItem(Material material, boolean bln) {
                super.updateItem(material, bln);
                if (material != null) {
                    setText(material.getNome());
                } else {
                    setText(null);
                }
            }
        });

        this.edtNome.setCellFactory((ListView<Material> p) -> {
            return new ListCell<Material>() {
                @Override
                protected void updateItem(Material material, boolean bln) {
                    super.updateItem(material, bln);
                    if (material != null) {
                        setText(material.getNome());
                    } else {
                        setText(null);
                    }
                }
            };
        });
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
     * @return the edtNome
     */
    public ComboBox<Material> getEdtNome() {
        return edtNome;
    }

    /**
     * @param edtNome the edtNome to set
     */
    public void setEdtNome(ComboBox<Material> edtNome) {
        this.edtNome = edtNome;
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
     * @return the edtQuantidade
     */
    public TextField getEdtQuantidade() {
        return edtQuantidade;
    }

    /**
     * @param edtQuantidade the edtQuantidade to set
     */
    public void setEdtQuantidade(TextField edtQuantidade) {
        this.edtQuantidade = edtQuantidade;
    }

    /**
     * @return the lblDescricao
     */
    public Label getLblDescricao() {
        return lblDescricao;
    }

    /**
     * @param lblDescricao the lblDescricao to set
     */
    public void setLblDescricao(Label lblDescricao) {
        this.lblDescricao = lblDescricao;
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * @return the estoque
     */
    public Estoque getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    /**
     * @return the confirmar
     */
    public boolean isConfirmar() {
        return confirmar;
    }

    /**
     * @param confirmar the confirmar to set
     */
    public void setConfirmar(boolean confirmar) {
        this.confirmar = confirmar;
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
