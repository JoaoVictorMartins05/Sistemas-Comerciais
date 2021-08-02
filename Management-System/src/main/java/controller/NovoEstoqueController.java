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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Material;

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
    private Label lblDescricao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void confirmar(ActionEvent event) {

    }
}
