/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Setor;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class NovoSetorController implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtAbreviatura;

    @FXML
    private TextField txtDescricao;

    @FXML
    private Button btnConfimar;

    @FXML
    private Button btnCancelar;


    private Setor setor;
    private Stage stage;
    private boolean confirmar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setConfirmar(false);
    }

    private boolean validarCampos() {
        if (getTxtAbreviatura().getText().equals("") || getTxtDescricao().getText().equals("") || getTxtNome().getText().equals("")) {
            return false;
        }
        return true;
    }

    @FXML
    private void confirmar() {
        if (validarCampos() == true) {
            this.preencherSetor();
            this.setConfirmar(true);
            this.getStage().close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos!!");
            alert.show();
        }
    }

    @FXML
    private void cancelar() {
        this.getStage().close();
    }

    private void preencherSetor() {
        if (this.validarCampos()) {
            this.getSetor().setNome(getTxtNome().getText());
            this.getSetor().setAbreviacao(getTxtAbreviatura().getText());
            this.getSetor().setDescricao(getTxtDescricao().getText());
        }
    }

    public void preencheCampos() {
        if (this.getSetor().getId() != -1) {
            this.getTxtNome().setText(this.getSetor().getNome());
            this.getTxtDescricao().setText(this.getSetor().getDescricao());
            this.getTxtAbreviatura().setText(this.getSetor().getAbreviacao());
        }
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
     * @return the txtAbreviatura
     */
    public TextField getTxtAbreviatura() {
        return txtAbreviatura;
    }

    /**
     * @param txtAbreviatura the txtAbreviatura to set
     */
    public void setTxtAbreviatura(TextField txtAbreviatura) {
        this.txtAbreviatura = txtAbreviatura;
    }

    /**
     * @return the txtDescricao
     */
    public TextField getTxtDescricao() {
        return txtDescricao;
    }

    /**
     * @param txtDescricao the txtDescricao to set
     */
    public void setTxtDescricao(TextField txtDescricao) {
        this.txtDescricao = txtDescricao;
    }

    /**
     * @return the btnConfimar
     */
    public Button getBtnConfimar() {
        return btnConfimar;
    }

    /**
     * @param btnConfimar the btnConfimar to set
     */
    public void setBtnConfimar(Button btnConfimar) {
        this.btnConfimar = btnConfimar;
    }

    /**
     * @return the btnCancelar
     */
    public Button getBtnCancelar() {
        return btnCancelar;
    }

    /**
     * @param btnCancelar the btnCancelar to set
     */
    public void setBtnCancelar(Button btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    /**
     * @return the setor
     */
    public Setor getSetor() {
        return setor;
    }

    /**
     * @param setor the setor to set
     */
    public void setSetor(Setor setor) {
        this.setor = setor;
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
    
}
