/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;    

/**
 *
 * @author elisson
 */
public class VendaController implements Initializable {
    @FXML
    private TableView<?> tblVenda;

    @FXML
    private TableColumn<?, ?> tblColumnCodigo;

    @FXML
    private TableColumn<?, ?> tblColumnDescricao;

    @FXML
    private TableColumn<?, ?> tblColumnQuantidade;

    @FXML
    private TableColumn<?, ?> tblColumnValor;

    @FXML
    private TableColumn<?, ?> tblColumnTotal;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView btnExcluir;

    @FXML
    private Button btnFinalisar;

    @FXML
    private TextField edtCodProduto;

    @FXML
    private ComboBox<?> edtFormaPagamento;

    @FXML
    private ComboBox<?> edtCliente;

    @FXML
    private TextField edtQuantidade;

    @FXML
    private TextField edtDescricao;

    @FXML
    private Text lblTotal;

    @FXML
    private Text lblData;    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void excluir(ActionEvent event) {

    }

    @FXML
    void finalizar(ActionEvent event) {

    }

    /**
     * @return the tblVenda
     */
    public TableView<?> getTblVenda() {
        return tblVenda;
    }

    /**
     * @param tblVenda the tblVenda to set
     */
    public void setTblVenda(TableView<?> tblVenda) {
        this.tblVenda = tblVenda;
    }

    /**
     * @return the tblColumnCodigo
     */
    public TableColumn<?, ?> getTblColumnCodigo() {
        return tblColumnCodigo;
    }

    /**
     * @param tblColumnCodigo the tblColumnCodigo to set
     */
    public void setTblColumnCodigo(TableColumn<?, ?> tblColumnCodigo) {
        this.tblColumnCodigo = tblColumnCodigo;
    }

    /**
     * @return the tblColumnDescricao
     */
    public TableColumn<?, ?> getTblColumnDescricao() {
        return tblColumnDescricao;
    }

    /**
     * @param tblColumnDescricao the tblColumnDescricao to set
     */
    public void setTblColumnDescricao(TableColumn<?, ?> tblColumnDescricao) {
        this.tblColumnDescricao = tblColumnDescricao;
    }

    /**
     * @return the tblColumnQuantidade
     */
    public TableColumn<?, ?> getTblColumnQuantidade() {
        return tblColumnQuantidade;
    }

    /**
     * @param tblColumnQuantidade the tblColumnQuantidade to set
     */
    public void setTblColumnQuantidade(TableColumn<?, ?> tblColumnQuantidade) {
        this.tblColumnQuantidade = tblColumnQuantidade;
    }

    /**
     * @return the tblColumnValor
     */
    public TableColumn<?, ?> getTblColumnValor() {
        return tblColumnValor;
    }

    /**
     * @param tblColumnValor the tblColumnValor to set
     */
    public void setTblColumnValor(TableColumn<?, ?> tblColumnValor) {
        this.tblColumnValor = tblColumnValor;
    }

    /**
     * @return the tblColumnTotal
     */
    public TableColumn<?, ?> getTblColumnTotal() {
        return tblColumnTotal;
    }

    /**
     * @param tblColumnTotal the tblColumnTotal to set
     */
    public void setTblColumnTotal(TableColumn<?, ?> tblColumnTotal) {
        this.tblColumnTotal = tblColumnTotal;
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
     * @return the btnExcluir
     */
    public ImageView getBtnExcluir() {
        return btnExcluir;
    }

    /**
     * @param btnExcluir the btnExcluir to set
     */
    public void setBtnExcluir(ImageView btnExcluir) {
        this.btnExcluir = btnExcluir;
    }

    /**
     * @return the btnFinalisar
     */
    public Button getBtnFinalisar() {
        return btnFinalisar;
    }

    /**
     * @param btnFinalisar the btnFinalisar to set
     */
    public void setBtnFinalisar(Button btnFinalisar) {
        this.btnFinalisar = btnFinalisar;
    }

    /**
     * @return the edtCodProduto
     */
    public TextField getEdtCodProduto() {
        return edtCodProduto;
    }

    /**
     * @param edtCodProduto the edtCodProduto to set
     */
    public void setEdtCodProduto(TextField edtCodProduto) {
        this.edtCodProduto = edtCodProduto;
    }

    /**
     * @return the edtFormaPagamento
     */
    public ComboBox<?> getEdtFormaPagamento() {
        return edtFormaPagamento;
    }

    /**
     * @param edtFormaPagamento the edtFormaPagamento to set
     */
    public void setEdtFormaPagamento(ComboBox<?> edtFormaPagamento) {
        this.edtFormaPagamento = edtFormaPagamento;
    }

    /**
     * @return the edtCliente
     */
    public ComboBox<?> getEdtCliente() {
        return edtCliente;
    }

    /**
     * @param edtCliente the edtCliente to set
     */
    public void setEdtCliente(ComboBox<?> edtCliente) {
        this.edtCliente = edtCliente;
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
     * @return the edtDescricao
     */
    public TextField getEdtDescricao() {
        return edtDescricao;
    }

    /**
     * @param edtDescricao the edtDescricao to set
     */
    public void setEdtDescricao(TextField edtDescricao) {
        this.edtDescricao = edtDescricao;
    }

    /**
     * @return the lblTotal
     */
    public Text getLblTotal() {
        return lblTotal;
    }

    /**
     * @param lblTotal the lblTotal to set
     */
    public void setLblTotal(Text lblTotal) {
        this.lblTotal = lblTotal;
    }

    /**
     * @return the lblData
     */
    public Text getLblData() {
        return lblData;
    }

    /**
     * @param lblData the lblData to set
     */
    public void setLblData(Text lblData) {
        this.lblData = lblData;
    }
    
}
