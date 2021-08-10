/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estoque;
import model.Funcionario;
import model.Material;
import model.Venda;

/**
 *
 * @author elisson
 */
public class VendaController implements Initializable {

    /**
     * @return the estoque
     */
    public List<Estoque> getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(List<Estoque> estoque) {
        this.estoque = estoque;
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
    private ComboBox<String> edtFormaPagamento;

    @FXML
    private TextField edtQuantidade;

    @FXML
    private TextField edtDescricao;

    @FXML
    private Text lblTotal;

    @FXML
    private Text lblData;

    @FXML
    private Text edtQtdItens;
    
    
    @FXML
    private Button btnAdd;
    

    private Venda venda;
    private List<Estoque> estoque;

    private EntityManagerFactory emf;
    private EntityManager em;
    private int qtdItens;
    private int valor;
    private int quantidade;
    private double total;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date data = new Date();
        this.lblData.setText(data.toString());
        preencheChoiceBox();
        this.qtdItens = 0;
        this.edtQuantidade.setText("0");
        this.total = 0;
        this.edtQtdItens.setText("0");
        this.setEstoque(this.listar());
    }

    
    @FXML
    void add(ActionEvent event) {
        edtCodProduto.setText(this.edtCodProduto.getText());
        inserirElementoTabela();
    }
    
    
    private void inserirElementoTabela() {
        for (int i = 0; i < this.estoque.size(); i++) {
            if (this.edtCodProduto.getText()!= "") {
                String aux = estoque.get(i).getMaterial().getNome() + " - " + estoque.get(i).getMaterial().getUnidadeMedida() + " - R$: " + estoque.get(i).getMaterial().getValor();
                this.edtDescricao.setText(aux);
                this.qtdItens += 1;
                this.edtQtdItens.setText(Double.toString(qtdItens));
                quantidade = Integer.parseInt(edtQuantidade.getText());
                total += quantidade * estoque.get(i).getMaterial().getValor();
                quantidade = 0;
                this.edtQuantidade.setText("0");
                edtCodProduto.setText("");
                lblTotal.setText(Double.toString(total));
                aux = "";
            }else{
                System.out.println("olá");
            }
        }
    }

//    private void atualizarTabela(List<Material> material) {
//        //Definicao de qual campo com qual coluna do BD
//        this.tblColumNome.setCellValueFactory(new PropertyValueFactory<Material, String>("nome"));
//        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<Material, String>("id"));
//
//        //Criar um observableCollection com os dados advindos do BD
//        this.setMateriais(material);
//        this.obsMateriais = FXCollections.observableArrayList(this.getMateriais());
//        this.tblMaterial.setItems(obsMateriais);
//    }

    private void preencheChoiceBox() {
        List<String> unidades = new ArrayList<>();
        unidades.add("Cartão Crédito");
        unidades.add("Cartão Débito");
        unidades.add("Dinheiro");
        unidades.add("Pix");
        unidades.add("Transferência Bancária");
        ObservableList obsUnidades = FXCollections.observableArrayList(unidades);

        //System.out.println(obsUnidades);
        this.edtFormaPagamento.setItems(obsUnidades);
        this.edtFormaPagamento.setValue(this.edtFormaPagamento.getItems().get(0));
    }

    public void init(Funcionario funcionario) {
        this.venda.setFuncionario(funcionario);
        this.setEstoque(this.listar());
    }

    public List<Estoque> listar() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        //System.out.println("\n\n\n" + this.getFuncionarioLogado().getNome() + "\n\n\n");
        getEm().getTransaction().begin();
        Query consulta = getEm().createNativeQuery("SELECT * FROM Estoque WHERE Estoque.idSetor = " + 2, Estoque.class);
        List<Estoque> estoques = consulta.getResultList();

        getEm().getTransaction().commit();
        getEmf().close();

        return estoques;
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
    public void setEdtFormaPagamento(ComboBox<String> edtFormaPagamento) {
        this.edtFormaPagamento = edtFormaPagamento;
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
