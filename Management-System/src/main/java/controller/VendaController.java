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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estoque;
import model.Funcionario;
import model.ItemTransferencia;
import model.ItemVenda;
import model.Material;
import model.Transferencia;
import model.Venda;
import model.visualization.Carrinho;

/**
 *
 * @author elisson
 */
public class VendaController implements Initializable {

    @FXML
    private TableView<Carrinho> tblVenda;

    @FXML
    private TableColumn<Carrinho, String> tblColumnCodigo;

    @FXML
    private TableColumn<Carrinho, String> tblColumnNome;

    @FXML
    private TableColumn<Carrinho, String> tblColumnQuantidade;

    @FXML
    private TableColumn<Carrinho, String> tblColumnUnMedida;

    @FXML
    private TableColumn<Carrinho, String> tblColumnValor;

    @FXML
    private TableColumn<Carrinho, String> tblColumnTotal;

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
    private int index;
    private List<Carrinho> carrinhos;
    private List<ItemVenda> itens;
    private Funcionario funcionarioLogado;
    private Date data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setData(new Date());
        this.getLblData().setText(this.getData().toString());
        preencheChoiceBox();
        this.setQtdItens(0);
        this.getEdtQuantidade().setText("0");
        this.setTotal(0);
        this.getEdtQtdItens().setText("0");      
        this.itens = new ArrayList<>();
        this.carrinhos = new ArrayList<>();
        this.venda = new Venda();
        this.qtdItens = 0;
        //this.estoque = new ArrayList<>();
    }
    
    public void init(){
        this.setEstoque(this.listar());
    }

    //quando aperta enter no cod do produto, valida e vai para o campo de quantidade
    @FXML
    void add(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String codAux = this.getEdtCodProduto().getText();
            System.out.println("codInserido- " + codAux);
            for (int i = 0; i < getEstoque().size(); i++) {

                System.out.println("id- " + getEstoque().get(i).getMaterial().getId().toString());
                System.out.println("qtd- " + getEstoque().get(i).getQuantidade());

                if (codAux.equals(getEstoque().get(i).getMaterial().getId().toString())) {

                    System.out.println("Flaggg1111");
                    if (getEstoque().get(i).getQuantidade() > 0) {
                        System.out.println("Flaggg222");
                        //edtCodProduto.setText(this.edtCodProduto.getText());
                        //inserirElementoTabela();
                        String aux = getEstoque().get(i).getMaterial().getNome() + " - " + getEstoque().get(i).getMaterial().getUnidadeMedida() + " - R$: " + getEstoque().get(i).getMaterial().getValor();
                        this.getEdtDescricao().setText(aux);
                        this.setIndex(i);
                        this.getEdtQuantidade().requestFocus();
                        break;
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produto não possui Estoque!");
                    alert.show();
                } else if (i == getEstoque().size() - 1) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produto Inválido!");
                    alert.show();
                }
            }
        }
    }

    @FXML
    void addTabela(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (getEstoque().get(getIndex()).getQuantidade() > Integer.parseInt(this.getEdtQuantidade().getText())) {
                System.out.println("ADD TABELA");
                adicionar();
                this.edtCodProduto.setText("");
                this.edtQuantidade.setText("");
                this.getEdtCodProduto().requestFocus();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produto não possui Estoque!");
                alert.show();
            }
        }
    }
    
    
        @FXML
    void finalizar(ActionEvent event) {       
        this.venda.setData(new Date());
        this.venda.setFormaPagamento(this.edtFormaPagamento.getValue());
        this.venda.setFuncionario(this.funcionarioLogado);
        this.venda.setSetor(this.funcionarioLogado.getSetor());
        this.venda.setValorTotal(this.total);
        this.venda.setDescricao("");
        
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());
        
        em.getTransaction().begin();
        em.merge(this.venda);

        em.getTransaction().commit();

        em.getTransaction().begin();
        Query consulta = em.createNativeQuery("Select * from venda where data = (Select max(data) from venda)", Venda.class);
        this.venda = (Venda) consulta.getResultList().get(0);

        em.getTransaction().commit();

        for (ItemVenda i : this.getItens()) {
            i.setVenda(this.venda);

            System.out.println("\n\n\n\n");
            System.out.println(i.getEstoque().getMaterial().getNome() + "\n\n\n\n");
            System.out.println(i.getVenda().getId());

            em.getTransaction().begin();
            em.persist(i);
            em.getTransaction().commit();
        }
        emf.close();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Venda Realizada Com Sucesso");
        alert.show();       
    }
    
    

    private void atualizarTabela() {
        this.getTblColumnCodigo().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("codigoMaterial"));
        this.getTblColumnNome().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("nomeMaterial"));
        this.getTblColumnQuantidade().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("quantidade"));
        this.getTblColumnTotal().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("valorTotal"));
        this.getTblColumnUnMedida().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("unidadeMedidaMaterial"));
        this.getTblColumnValor().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("valorMaterial"));

        ObservableList obsCarrinhos = FXCollections.observableArrayList(this.getCarrinhos());

        this.getTblVenda().setItems(this.getCarrinhos().isEmpty() ? null : obsCarrinhos);
    }

    private void adicionaMaterialNoCarrinho(Carrinho carrinho) {
        for (Carrinho c : this.getCarrinhos()) {
            if (c.getCodigoMaterial().equals(carrinho.getCodigoMaterial())) {
                carrinho.setQuantidade(c.getQuantidade() + carrinho.getQuantidade());
                carrinho.setValorTotal(carrinho.getQuantidade() * carrinho.getValorMaterial());
                this.getCarrinhos().remove(c);
                break;
            }
        }
        this.getCarrinhos().add(carrinho);
    }

    public void adicionar() {
        ItemVenda item = new ItemVenda();

        if (this.validaCampos()) {
            Material material = this.estoque.get(index).getMaterial();

            item.setEstoque(this.estoque.get(index));
            item.setQuantidade(Double.parseDouble(this.getEdtQuantidade().getText()));

            if (!this.jaExisteItem(item)) {
                this.getItens().add(item);
            }

            Carrinho carrinho = this.preencheCarrinho(material, item);

            this.adicionaMaterialNoCarrinho(carrinho);
            this.lblTotal.setText(Double.toString(this.total));
            this.qtdItens+=1;
            this.edtQtdItens.setText(Integer.toString(this.qtdItens));
        }
        this.atualizarTabela();
    }

    private Carrinho preencheCarrinho(Material material, ItemVenda item) {
        Carrinho carrinho = new Carrinho();

        carrinho.setNomeMaterial(material.getNome());
        carrinho.setCodigoMaterial(material.getId());
        carrinho.setQuantidade(item.getQuantidade());
        carrinho.setUnidadeMedidaMaterial(material.getUnidadeMedida());
        carrinho.setValorMaterial(material.getValor());
        carrinho.setValorTotal(material.getValor() * item.getQuantidade());
        this.total += carrinho.getValorTotal();
        return carrinho;
    }

    private boolean jaExisteItem(ItemVenda item) {
        for (ItemVenda i : this.getItens()) {
            if (item.getEstoque().equals(i.getEstoque())) {
                i.setQuantidade(i.getQuantidade() + item.getQuantidade());
                return true;
            }
        }
        return false;
    }

    public boolean validaCampos() {
        return true;
    }

    private void preencheChoiceBox() {
        List<String> unidades = new ArrayList<>();
        unidades.add("Cartão Crédito");
        unidades.add("Cartão Débito");
        unidades.add("Dinheiro");
        unidades.add("Pix");
        unidades.add("Transferência Bancária");
        ObservableList obsUnidades = FXCollections.observableArrayList(unidades);

        //System.out.println(obsUnidades);
        this.getEdtFormaPagamento().setItems(obsUnidades);
        this.getEdtFormaPagamento().setValue(this.getEdtFormaPagamento().getItems().get(0));
    }

    public void init(Funcionario funcionario) {
        this.getVenda().setFuncionario(funcionario);
        this.setEstoque(this.listar());
        this.edtCodProduto.requestFocus();
    }
    
    @FXML
    void remover(ActionEvent event) {
        Carrinho carrinho = this.tblVenda.getSelectionModel().getSelectedItem();

        if (carrinho != null) {
            this.total -= carrinho.getValorTotal();
            this.lblTotal.setText(Double.toString(this.total));
            for (ItemVenda item : this.getItens()) {
                if(item.getEstoque().getMaterial().getId().equals(carrinho.getCodigoMaterial())) {
                    this.getItens().remove(item);
                    break;
                }
            }
            this.getCarrinhos().remove(carrinho);
            this.atualizarTabela();
            this.qtdItens-=1;
            this.edtQtdItens.setText(Integer.toString(this.qtdItens));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum Material Selecionado");
            alert.show();
        }
    }

    public List<Estoque> listar() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        //System.out.println("\n\n\n" + this.getFuncionarioLogado().getNome() + "\n\n\n");
        getEm().getTransaction().begin();
        Query consulta = getEm().createNativeQuery("SELECT * FROM Estoque WHERE Estoque.idSetor = " + this.funcionarioLogado.getSetor().getId(), Estoque.class);
        List<Estoque> estoques = consulta.getResultList();

        getEm().getTransaction().commit();
        getEmf().close();

        return estoques;
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.limparCampos();
    }

      
    void limparCampos(){
        this.venda = new Venda();
        this.total = 0;
        this.edtCodProduto.setText("");
        this.edtQtdItens.setText("");
        this.edtQuantidade.setText("");
        this.data = new Date();
        this.carrinhos = new ArrayList<>();
        this.tblVenda.setItems(null);
        this.edtQtdItens.setText("0");
        this.lblTotal.setText("0");
        this.lblData.setText(data.toString());
    }
    
    
    /**
     * @return the tblVenda
     */
    public TableView<Carrinho> getTblVenda() {
        return tblVenda;
    }

    /**
     * @param tblVenda the tblVenda to set
     */
    public void setTblVenda(TableView<Carrinho> tblVenda) {
        this.tblVenda = tblVenda;
    }

    /**
     * @return the tblColumnCodigo
     */
    public TableColumn<Carrinho, String> getTblColumnCodigo() {
        return tblColumnCodigo;
    }

    /**
     * @param tblColumnCodigo the tblColumnCodigo to set
     */
    public void setTblColumnCodigo(TableColumn<Carrinho, String> tblColumnCodigo) {
        this.tblColumnCodigo = tblColumnCodigo;
    }

    /**
     * @return the tblColumnNome
     */
    public TableColumn<Carrinho, String> getTblColumnNome() {
        return tblColumnNome;
    }

    /**
     * @param tblColumnNome the tblColumnNome to set
     */
    public void setTblColumnNome(TableColumn<Carrinho, String> tblColumnNome) {
        this.tblColumnNome = tblColumnNome;
    }

    /**
     * @return the tblColumnQuantidade
     */
    public TableColumn<Carrinho, String> getTblColumnQuantidade() {
        return tblColumnQuantidade;
    }

    /**
     * @param tblColumnQuantidade the tblColumnQuantidade to set
     */
    public void setTblColumnQuantidade(TableColumn<Carrinho, String> tblColumnQuantidade) {
        this.tblColumnQuantidade = tblColumnQuantidade;
    }

    /**
     * @return the tblColumnUnMedida
     */
    public TableColumn<Carrinho, String> getTblColumnUnMedida() {
        return tblColumnUnMedida;
    }

    /**
     * @param tblColumnUnMedida the tblColumnUnMedida to set
     */
    public void setTblColumnUnMedida(TableColumn<Carrinho, String> tblColumnUnMedida) {
        this.tblColumnUnMedida = tblColumnUnMedida;
    }

    /**
     * @return the tblColumnValor
     */
    public TableColumn<Carrinho, String> getTblColumnValor() {
        return tblColumnValor;
    }

    /**
     * @param tblColumnValor the tblColumnValor to set
     */
    public void setTblColumnValor(TableColumn<Carrinho, String> tblColumnValor) {
        this.tblColumnValor = tblColumnValor;
    }

    /**
     * @return the tblColumnTotal
     */
    public TableColumn<Carrinho, String> getTblColumnTotal() {
        return tblColumnTotal;
    }

    /**
     * @param tblColumnTotal the tblColumnTotal to set
     */
    public void setTblColumnTotal(TableColumn<Carrinho, String> tblColumnTotal) {
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
    public ComboBox<String> getEdtFormaPagamento() {
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

    /**
     * @return the edtQtdItens
     */
    public Text getEdtQtdItens() {
        return edtQtdItens;
    }

    /**
     * @param edtQtdItens the edtQtdItens to set
     */
    public void setEdtQtdItens(Text edtQtdItens) {
        this.edtQtdItens = edtQtdItens;
    }

    /**
     * @return the btnAdd
     */
    public Button getBtnAdd() {
        return btnAdd;
    }

    /**
     * @param btnAdd the btnAdd to set
     */
    public void setBtnAdd(Button btnAdd) {
        this.btnAdd = btnAdd;
    }

    /**
     * @return the venda
     */
    public Venda getVenda() {
        return venda;
    }

    /**
     * @param venda the venda to set
     */
    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    /**
     * @return the qtdItens
     */
    public int getQtdItens() {
        return qtdItens;
    }

    /**
     * @param qtdItens the qtdItens to set
     */
    public void setQtdItens(int qtdItens) {
        this.qtdItens = qtdItens;
    }

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the total
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the carrinhos
     */
    public List<Carrinho> getCarrinhos() {
        return carrinhos;
    }

    /**
     * @param carrinhos the carrinhos to set
     */
    public void setCarrinhos(List<Carrinho> carrinhos) {
        this.carrinhos = carrinhos;
    }

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

    /**
     * @return the itens
     */
    public List<ItemVenda> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
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

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }
}
