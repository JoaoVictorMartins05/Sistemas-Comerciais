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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Material;
import model.Setor;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class NovaTransferenciaController implements Initializable {

    @FXML
    private ComboBox<Setor> edtDestinatario;

    @FXML
    private ComboBox<Material> edtMaterial;

    @FXML
    private TableView<Material> tblCarrinho;

    @FXML
    private TableColumn<Material, String> colunaId;

    @FXML
    private TableColumn<Material, String> colunaNome;

    @FXML
    private TableColumn<Material, String> colunaQuantidade;

    @FXML
    private Button btnadicionar;

    @FXML
    private Button btnRemover;

    @FXML
    private TextField edtQuantidade;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnCancelar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
        @FXML
    void adicionar(ActionEvent event) {

    }

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void finalizar(ActionEvent event) {

    }

    @FXML
    void remover(ActionEvent event) {

    }

    /**
     * @return the edtDestinatario
     */
    public ComboBox<Setor> getEdtDestinatario() {
        return edtDestinatario;
    }

    /**
     * @param edtDestinatario the edtDestinatario to set
     */
    public void setEdtDestinatario(ComboBox<Setor> edtDestinatario) {
        this.edtDestinatario = edtDestinatario;
    }

    /**
     * @return the edtMaterial
     */
    public ComboBox<Material> getEdtMaterial() {
        return edtMaterial;
    }

    /**
     * @param edtMaterial the edtMaterial to set
     */
    public void setEdtMaterial(ComboBox<Material> edtMaterial) {
        this.edtMaterial = edtMaterial;
    }

    /**
     * @return the tblCarrinho
     */
    public TableView<Material> getTblCarrinho() {
        return tblCarrinho;
    }

    /**
     * @param tblCarrinho the tblCarrinho to set
     */
    public void setTblCarrinho(TableView<Material> tblCarrinho) {
        this.tblCarrinho = tblCarrinho;
    }

    /**
     * @return the colunaId
     */
    public TableColumn<Material, String> getColunaId() {
        return colunaId;
    }

    /**
     * @param colunaId the colunaId to set
     */
    public void setColunaId(TableColumn<Material, String> colunaId) {
        this.colunaId = colunaId;
    }

    /**
     * @return the colunaNome
     */
    public TableColumn<Material, String> getColunaNome() {
        return colunaNome;
    }

    /**
     * @param colunaNome the colunaNome to set
     */
    public void setColunaNome(TableColumn<Material, String> colunaNome) {
        this.colunaNome = colunaNome;
    }

    /**
     * @return the colunaQuantidade
     */
    public TableColumn<Material, String> getColunaQuantidade() {
        return colunaQuantidade;
    }

    /**
     * @param colunaQuantidade the colunaQuantidade to set
     */
    public void setColunaQuantidade(TableColumn<Material, String> colunaQuantidade) {
        this.colunaQuantidade = colunaQuantidade;
    }

    /**
     * @return the btnadicionar
     */
    public Button getBtnadicionar() {
        return btnadicionar;
    }

    /**
     * @param btnadicionar the btnadicionar to set
     */
    public void setBtnadicionar(Button btnadicionar) {
        this.btnadicionar = btnadicionar;
    }

    /**
     * @return the btnRemover
     */
    public Button getBtnRemover() {
        return btnRemover;
    }

    /**
     * @param btnRemover the btnRemover to set
     */
    public void setBtnRemover(Button btnRemover) {
        this.btnRemover = btnRemover;
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
     * @return the btnFinalizar
     */
    public Button getBtnFinalizar() {
        return btnFinalizar;
    }

    /**
     * @param btnFinalizar the btnFinalizar to set
     */
    public void setBtnFinalizar(Button btnFinalizar) {
        this.btnFinalizar = btnFinalizar;
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
    
}
