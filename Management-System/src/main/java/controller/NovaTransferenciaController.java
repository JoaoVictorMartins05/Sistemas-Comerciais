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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estoque;
import model.Funcionario;
import model.ItemTransferencia;
import model.Material;
import model.Setor;
import model.Transferencia;
import model.visualization.Carrinho;

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
    private TableView<Carrinho> tblCarrinho;

    @FXML
    private TableColumn<Carrinho, String> colunaId;

    @FXML
    private TableColumn<Carrinho, String> colunaNome;

    @FXML
    private TableColumn<Carrinho, String> colunaQuantidade;

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

    private Funcionario funcionarioLogado;
    private List<Estoque> estoques;

    private Transferencia transferencia;
    private List<Material> materiais;
    private List<Carrinho> carrinhos;
    private List<ItemTransferencia> itens;

    private ObservableList<Setor> obsSetores;

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTransferencia(new Transferencia());
        this.setMateriais(new ArrayList<>());
        this.setEstoques(new ArrayList<>());
        this.setItens(new ArrayList<>());
        this.setCarrinhos(new ArrayList<>());
    }

    public void init() {
        this.carregarDestinatario();
        this.carregarMateriais();
    }

    public void carregarDestinatario() {
        this.setObsSetores(FXCollections.observableArrayList(this.listarSetores()));

        this.getEdtDestinatario().setItems(getObsSetores());

        this.mostrarSoNomeSetor();
    }

    public void carregarMateriais() {
        ObservableList<Material> obsMaterial = FXCollections.observableArrayList(this.listarMateriaisDeUmSetor());

        this.getEdtMaterial().setItems(obsMaterial);

        this.mostrarSoNomeMaterial();
    }

    private List<Setor> listarSetores() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        System.out.println(this.getFuncionarioLogado().getId());

        String query = "SELECT * FROM `setor` WHERE setor.id != " + this.getFuncionarioLogado().getSetor().getId();

        getEm().getTransaction().begin();
        Query consulta = getEm().createNativeQuery(query, Setor.class);

        List<Setor> setores = consulta.getResultList();

        getEm().getTransaction().commit();
        getEmf().close();

        return setores;
    }

    private List<Material> listarMateriaisDeUmSetor() {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        String query = "SELECT DISTINCT material.id, nome, descricao, unidadeMedida, valor, existe "
                + "FROM material INNER JOIN `Estoque`on material.id = `Estoque`.idMaterial "
                + "WHERE material.existe = 1 AND idSetor = "
                + this.getFuncionarioLogado().getSetor().getId();

        getEm().getTransaction().begin();
        Query consulta = getEm().createNativeQuery(query, Material.class);

        List<Material> materiais = consulta.getResultList();
        getEm().getTransaction().commit();
        getEmf().close();

        return materiais;
    }

    private void mostrarSoNomeMaterial() {
        this.getEdtMaterial().setButtonCell(new ListCell<Material>() {
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

        this.getEdtMaterial().setCellFactory((ListView<Material> p) -> {
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

    private void mostrarSoNomeSetor() {
        this.getEdtDestinatario().setButtonCell(new ListCell<Setor>() {
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

        this.getEdtDestinatario().setCellFactory((ListView<Setor> p) -> {
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

    private boolean validaCampos() {
        Setor setor = this.getEdtDestinatario().getSelectionModel().getSelectedItem();
        Material material = this.getEdtMaterial().getSelectionModel().getSelectedItem();

        if (setor == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum Setor de Destino Selecionado");
            alert.show();
            return false;
        } else if (material == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum Material Selecionado");
            alert.show();
            return false;
        } else if (this.getEdtQuantidade().getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhuma Quantidade Informada");
            alert.show();
            return false;
        } else {
            try {
                Double.parseDouble(this.getEdtQuantidade().getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "A Quantidade Deve Ser um N??mero");
                alert.show();
                Logger.getLogger(NovaTransferenciaController.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    private Estoque buscarEstoque(Material material) {
        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        String query = "SELECT * FROM Estoque WHERE Estoque.idSetor = "
                + this.getFuncionarioLogado().getSetor().getId()
                + " AND Estoque.idMaterial = "
                + material.getId();

        System.out.println(query);

        getEm().getTransaction().begin();
        Query consulta = getEm().createNativeQuery(query, Estoque.class);
        List<Estoque> estoques = consulta.getResultList();

        getEm().getTransaction().commit();
        getEmf().close();

        return estoques.get(0);
    }

    private boolean verificaEstoque(ItemTransferencia item) {
        if (item.getEstoque().getQuantidade() >= item.getQuantidade()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Este setor n??o possui esta quantidade em estoque de "
                    + item.getEstoque().getMaterial().getNome());
            alert.show();
        }
        return false;
    }

    @FXML
    public void adicionar(ActionEvent event) {
        ItemTransferencia item = new ItemTransferencia();

        if (this.validaCampos()) {
            Material material = this.getEdtMaterial().getSelectionModel().getSelectedItem();
            double quantidade = Double.parseDouble(this.getEdtQuantidade().getText());

            item.setEstoque(this.buscarEstoque(material));
            item.setQuantidade(quantidade);

            if (this.verificaEstoque(item)) {
                if (!this.jaExisteItem(item)) {
                    this.getItens().add(item);
                }            
                Carrinho carrinho = this.preencheCarrinho(material, item);
                this.adicionaMaterialNoCarrinho(carrinho);
                this.atualizarTabela();    
            }
        }
    }

    private boolean jaExisteItem(ItemTransferencia item) {
        for (ItemTransferencia i : this.getItens()) {
            if (i.getEstoque().getId().equals(item.getEstoque().getId())) {
                item.setQuantidade(i.getQuantidade() + item.getQuantidade());
                if (this.verificaEstoque(item)) {
                    i.setQuantidade(item.getQuantidade());
                }
                return true;
            }
        }
        return false;
    }

    private Carrinho preencheCarrinho(Material material, ItemTransferencia item) {
        Carrinho carrinho = new Carrinho();

        carrinho.setNomeMaterial(material.getNome());
        carrinho.setCodigoMaterial(material.getId());
        carrinho.setQuantidade(item.getQuantidade());
        return carrinho;
    }

    private void adicionaMaterialNoCarrinho(Carrinho carrinho) {
        for (Carrinho c : this.getCarrinhos()) {
            if (c.getCodigoMaterial().equals(carrinho.getCodigoMaterial())) {
                carrinho.setQuantidade(carrinho.getQuantidade());
                this.getCarrinhos().remove(c);
                break;
            }
        }
        this.getCarrinhos().add(carrinho);
    }

    private void atualizarTabela() {
        this.getColunaId().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("codigoMaterial"));
        this.getColunaNome().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("nomeMaterial"));
        this.getColunaQuantidade().setCellValueFactory(new PropertyValueFactory<Carrinho, String>("quantidade"));

        ObservableList obsCarrinhos = FXCollections.observableArrayList(this.getCarrinhos());

        this.getTblCarrinho().setItems(this.getCarrinhos().isEmpty() ? null : obsCarrinhos);
    }

    @FXML
    void cancelar(ActionEvent event) {
        this.limparTransferencia();
    }

    @FXML
    void finalizar(ActionEvent event) {
        this.transferencia.setSetorOrigem(this.getFuncionarioLogado().getSetor());
        this.transferencia.setSetorDestino(this.getEdtDestinatario().getSelectionModel().getSelectedItem());
        this.transferencia.setFuncionario(this.getFuncionarioLogado());
        this.transferencia.setData(new Date());
        this.transferencia.setStatus("Em andamento");

        setEmf(Persistence.createEntityManagerFactory("venda"));
        setEm(getEmf().createEntityManager());

        em.getTransaction().begin();
        em.merge(this.transferencia);

        em.getTransaction().commit();

        em.getTransaction().begin();
        Query consulta = em.createNativeQuery("Select * from transferencia where data = (Select max(data) from transferencia)", Transferencia.class
        );
        this.transferencia = (Transferencia) consulta.getResultList().get(0);

        em.getTransaction().commit();

        for (ItemTransferencia i : this.getItens()) {
            i.setTranferencia(this.transferencia);

            System.out.println("\n\n\n\n");
            System.out.println(i.getEstoque().getMaterial().getNome() + "\n\n\n\n");
            System.out.println(i.getTranferencia().getId());

            em.getTransaction().begin();
            em.persist(i);
            em.getTransaction().commit();
        }
        emf.close();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Transferencia Realizada Com Sucesso");
        alert.show();

        this.limparTransferencia();
    }

    private void limparTransferencia() {
        this.transferencia = new Transferencia();
        this.materiais = new ArrayList<>();
        this.carrinhos = new ArrayList<>();
        this.itens = new ArrayList<>();

        this.tblCarrinho.setItems(null);
        this.getEdtDestinatario().getSelectionModel().clearSelection();
        this.getEdtMaterial().getSelectionModel().clearSelection();
        this.edtQuantidade.clear();
    }

    @FXML
    void remover(ActionEvent event) {
        Carrinho carrinho = this.tblCarrinho.getSelectionModel().getSelectedItem();

        if (carrinho != null) {
            for (ItemTransferencia item : this.getItens()) {
                if (item.getEstoque().getMaterial().getId().equals(carrinho.getCodigoMaterial())) {
                    this.getItens().remove(item);
                    break;
                }
            }
            this.getCarrinhos().remove(carrinho);
            this.atualizarTabela();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhum Material Selecionado");
            alert.show();
        }
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
    public TableView<Carrinho> getTblCarrinho() {
        return tblCarrinho;
    }

    /**
     * @param tblCarrinho the tblCarrinho to set
     */
    public void setTblCarrinho(TableView<Carrinho> tblCarrinho) {
        this.tblCarrinho = tblCarrinho;
    }

    /**
     * @return the colunaId
     */
    public TableColumn<Carrinho, String> getColunaId() {
        return colunaId;
    }

    /**
     * @param colunaId the colunaId to set
     */
    public void setColunaId(TableColumn<Carrinho, String> colunaId) {
        this.colunaId = colunaId;
    }

    /**
     * @return the colunaNome
     */
    public TableColumn<Carrinho, String> getColunaNome() {
        return colunaNome;
    }

    /**
     * @param colunaNome the colunaNome to set
     */
    public void setColunaNome(TableColumn<Carrinho, String> colunaNome) {
        this.colunaNome = colunaNome;
    }

    /**
     * @return the colunaQuantidade
     */
    public TableColumn<Carrinho, String> getColunaQuantidade() {
        return colunaQuantidade;
    }

    /**
     * @param colunaQuantidade the colunaQuantidade to set
     */
    public void setColunaQuantidade(TableColumn<Carrinho, String> colunaQuantidade) {
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
     * @return the estoques
     */
    public List<Estoque> getEstoques() {
        return estoques;
    }

    /**
     * @param estoques the estoques to set
     */
    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    /**
     * @return the transferencia
     */
    public Transferencia getTransferencia() {
        return transferencia;
    }

    /**
     * @param transferencia the transferencia to set
     */
    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    /**
     * @return the materiais
     */
    public List<Material> getMateriais() {
        return materiais;
    }

    /**
     * @param materiais the materiais to set
     */
    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
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
    public List<ItemTransferencia> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<ItemTransferencia> itens) {
        this.itens = itens;
    }

    /**
     * @return the obsSetores
     */
    public ObservableList<Setor> getObsSetores() {
        return obsSetores;
    }

    /**
     * @param obsSetores the obsSetores to set
     */
    public void setObsSetores(ObservableList<Setor> obsSetores) {
        this.obsSetores = obsSetores;
    }

}
