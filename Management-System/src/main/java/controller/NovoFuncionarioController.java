/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Funcionario;
import model.Setor;

public class NovoFuncionarioController implements Initializable {

    @FXML
    private ComboBox<String> cbxNivelAcesso;

    @FXML
    private ComboBox<Setor> cbxSetor;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private PasswordField txtConfirmaSenha;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtUsuario;

    private Funcionario funcionario;
    private Stage stage;
    private boolean confirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.confirmar = false;
        this.preencheChoiceBoxNivelAcesso();
        this.preencheChoiceBoxSetores();
    }

    @FXML
    private void confirmar() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (this.validarCampos()) {
            this.preencherFuncionario();
            this.setConfirmar(true);
            this.stage.close();
        }
    }

    @FXML
    private void cancelar() {
        this.stage.close();
    }

    private boolean validarCampos() {
        if (txtUsuario.getText().equals("") || txtNome.getText().equals("") || txtSenha.getText().equals("") || txtConfirmaSenha.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos!");
            alert.show();
            return false;
        }

        if (!txtSenha.getText().equals(txtConfirmaSenha.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "As senhas não conferem!");
            alert.show();
            return false;
        }
        return true;
    }

    private List<Setor> listarSetores() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("venda");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Query consulta = em.createQuery("Select setor from Setor setor");
        List<Setor> setores = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return setores;
    }

    private void preencherFuncionario() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.getFuncionario().setUsuario(txtUsuario.getText());
        this.getFuncionario().setNome(txtNome.getText());
        this.getFuncionario().setNivelAcesso(this.getFuncionario().converteNivelDeAcessoParaInteiro(cbxNivelAcesso.getValue()));
        this.getFuncionario().setSenha(txtSenha.getText());
        this.getFuncionario().setSetor(this.cbxSetor.getValue());
    }

    public void preencheCampos() {
        if (this.getFuncionario().getId() != -1) {
            this.txtNome.setText(this.getFuncionario().getNome());
            this.cbxNivelAcesso.setValue(this.getFuncionario().converteNivelDeAcessoParaString(this.getFuncionario().getNivelAcesso()));
            this.cbxSetor.setValue(this.getFuncionario().getSetor());
            this.txtUsuario.setText(this.getFuncionario().getUsuario());
            this.txtSenha.setText(this.getFuncionario().getSenha());
            this.txtConfirmaSenha.setText(this.getFuncionario().getSenha());
        }
    }

    private void preencheChoiceBoxNivelAcesso() {
        List<String> unidades = new ArrayList<>();
        unidades.add("Administrador");
        unidades.add("Vendedor");
        ObservableList obsUnidades = FXCollections.observableArrayList(unidades);
        this.cbxNivelAcesso.setItems(obsUnidades);
        this.cbxNivelAcesso.setValue(this.cbxNivelAcesso.getItems().get(0));
    }

    private void preencheChoiceBoxSetores() {
        ObservableList<Setor> obsSetores = FXCollections.observableArrayList(this.listarSetores());

        this.cbxSetor.setItems(obsSetores);

        this.mostrarSoNomeSetor();

        this.cbxSetor.setValue(this.cbxSetor.getItems().get(0));
    }

    private void mostrarSoNomeSetor() {
        this.cbxSetor.setButtonCell(new ListCell<Setor>() {
            @Override
            protected void updateItem(Setor setor, boolean bln) {
                super.updateItem(setor, bln);
                if (setor != null) {
                    setText(setor.getNome());
                } else {
                    setText(null);
                }
            }
        });

        this.cbxSetor.setCellFactory((ListView<Setor> p) -> {
            return new ListCell<Setor>() {
                @Override
                protected void updateItem(Setor setor, boolean bln) {
                    super.updateItem(setor, bln);
                    if (setor != null) {
                        setText(setor.getNome());
                    } else {
                        setText(null);
                    }
                }
            };
        });
    }

    /**
     * @return the cbxNivelAcesso
     */
    public ComboBox<String> getCbxNivelAcesso() {
        return cbxNivelAcesso;
    }

    /**
     * @param cbxNivelAcesso the cbxNivelAcesso to set
     */
    public void setCbxNivelAcesso(ComboBox<String> cbxNivelAcesso) {
        this.cbxNivelAcesso = cbxNivelAcesso;
    }

    /**
     * @return the txtSenha
     */
    public PasswordField getTxtSenha() {
        return txtSenha;
    }

    /**
     * @param txtSenha the txtSenha to set
     */
    public void setTxtSenha(PasswordField txtSenha) {
        this.txtSenha = txtSenha;
    }

    /**
     * @return the txtConfirmaSenha
     */
    public PasswordField getTxtConfirmaSenha() {
        return txtConfirmaSenha;
    }

    /**
     * @param txtConfirmaSenha the txtConfirmaSenha to set
     */
    public void setTxtConfirmaSenha(PasswordField txtConfirmaSenha) {
        this.txtConfirmaSenha = txtConfirmaSenha;
    }

    /**
     * @return the txtNome
     */
    public TextField getTxtNome() {
        return txtNome;
    }

    /**
     * @param txtNome the txtNome to set
     */
    public void setTxtNome(TextField txtNome) {
        this.txtNome = txtNome;
    }

    /**
     * @return the txtUsuario
     */
    public TextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * @param txtUsuario the txtUsuario to set
     */
    public void setTxtUsuario(TextField txtUsuario) {
        this.txtUsuario = txtUsuario;
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
