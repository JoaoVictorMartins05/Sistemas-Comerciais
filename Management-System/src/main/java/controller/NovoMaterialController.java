/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Material;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class NovoMaterialController implements Initializable {

    @FXML
    private TextField edtDescricao;

    @FXML
    private TextField edtValor;

    @FXML
    private ComboBox<String> edtUnidadeMedida;

    @FXML
    private TextField edtNome;

    private Stage stage;

    private Material material;

    private boolean confirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.confirmar = false;
        this.preencheChoiceBox();
    }

    @FXML
    private void confirmar() {
        if (validarCampos() == true) {
            this.preencherMaterial();
            this.setConfirmar(true);
            this.stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos!!");
            alert.show();
        }
    }

    @FXML
    private void cancelar() {
        this.stage.close();
    }

    private boolean validarCampos() {
        if (edtDescricao.getText().equals("") || edtNome.getText().equals("") || edtValor.getText().equals("")) {
            return false;
        }
        return true;
    }

    public void preencheCampos() {
        if (this.material.getId() != -1) {
            this.edtNome.setText(this.material.getNome());
            this.edtUnidadeMedida.setValue((this.material.getUnidadeMedida()));
            this.edtValor.setText(String.valueOf(this.material.getValor()));
            this.edtDescricao.setText(this.material.getDescricao());
        }
    }

    private void preencherMaterial() {
        this.material.setNome(edtNome.getText());
        this.material.setDescricao(edtDescricao.getText());
        this.material.setUnidadeMedida(edtUnidadeMedida.getValue());
        this.material.setValor(Double.parseDouble(this.edtValor.getText()));
    }

    private void preencheChoiceBox() {
        List<String> unidades = new ArrayList<>();
        unidades.add("unidade");
        unidades.add("Litros");
        unidades.add("Kilos");
        ObservableList obsUnidades = FXCollections.observableArrayList(unidades);
        
        System.out.println(obsUnidades);
        this.edtUnidadeMedida.setItems(obsUnidades);
        this.edtUnidadeMedida.setValue(this.edtUnidadeMedida.getItems().get(0));
    }    

    public boolean isConfirmar() {
        return confirmar;
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
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * @param confirmar the confirmar to set
     */
    public void setConfirmar(boolean confirmar) {
        this.confirmar = confirmar;
    }

}
