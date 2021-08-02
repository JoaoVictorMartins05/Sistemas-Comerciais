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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import model.Transferencia;

/**
 *
 * @author elisson
 */
public class TransferenciaController implements Initializable {

    @FXML
    private TableView<Transferencia> tblTransferencia;

    @FXML
    private TableColumn<Transferencia, Long> tblColumCodigo;

    @FXML
    private TableColumn<Transferencia, String> tblColumStatus;

    @FXML
    private TextArea lblDescricao;

    @FXML
    private Label lblData;

    @FXML
    private Label lblSetorDestinatario;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblFuncionario;

    @FXML
    private Label lblId;

    @FXML
    private Label lblSetorOrigem;

    @FXML
    private Button btnAceitar;

    @FXML
    private ComboBox<String> edtFiltros;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    @FXML
    void aceitar(ActionEvent event) {

    }

    @FXML
    void adicionar(ActionEvent event) {

    }
}
