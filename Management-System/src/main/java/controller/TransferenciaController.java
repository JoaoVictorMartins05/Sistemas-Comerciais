/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Funcionario;
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
    
    private BorderPane painelPrincipal;

    private List<Transferencia> transferencias;
    private ObservableList<String> obsFiltros;

    private Funcionario funcionarioLogado;

    EntityManagerFactory emf;
    EntityManager em;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       transferencias = new ArrayList<>();
        transferencias = listar("Select * from transferencia");

        this.tblTransferencia.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> this.detallhar(newValue));

        edtFiltros.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                filtrarTransferencias();
            }
        });
    }
    
    private void detallhar(Transferencia transferencia) {
        if (transferencia != null) {
            this.lblId.setText(String.valueOf(transferencia.getId()));
            this.lblFuncionario.setText(transferencia.getFuncionario().getNome());
            this.lblSetorOrigem.setText(transferencia.getSetorOrigem().getNome());
            this.lblSetorDestinatario.setText(transferencia.getSetorDestino().getNome());
            this.lblStatus.setText(transferencia.getStatus());
            this.lblData.setText(String.valueOf(transferencia.getData().getDate()));
            this.lblDescricao.setText(transferencia.getDescricao());
        }
    }
    
    @FXML
    void aceitar(ActionEvent event) {
        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();
        
        Transferencia transferencia = this.tblTransferencia.getSelectionModel().getSelectedItem();
        if (transferencia != null) {
            if (transferencia.getStatus().equals("Em andamento")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja Realmente Aceitar Esta Transferência?");
                alert.showAndWait();

                if (alert.getResult().getText().equals("OK")) {
                    transferencia.setStatus("Finalizada");
                    em.getTransaction().begin();
                    em.merge(transferencia);
                    em.getTransaction().commit();

                    this.filtrarTransferencias();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Esta Transferencia Já Está Finalizada");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nenhuma Transfêrencia Selecionada");
            alert.show();
        }
    }

    @FXML
    void adicionar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/NovaTransferencia.fxml"));
        AnchorPane a = (AnchorPane) loader.load();

        NovaTransferenciaController controller = loader.getController();

        controller.setFuncionarioLogado(this.getFuncionarioLogado());

        controller.carregarDestinatario();
        controller.carregarMateriais();
        this.getPainelPrincipal().setCenter(a);
    }
    
    @FXML
    public void filtrarTransferencias() {
        String query = "";
        String filtro = this.edtFiltros.getSelectionModel().getSelectedItem();

        if (filtro.equals("Todas")) {
            query = "SELECT * FROM transferencia";
            this.btnAceitar.setVisible(false);

        } else if (filtro.equals("Enviadas")) {
            query = "SELECT * FROM transferencia WHERE idSetorOrigem = " + this.getFuncionarioLogado().getSetor().getId();
            this.btnAceitar.setVisible(false);
        } else if (filtro.equals("Recebidas")) {
            query = "SELECT * FROM transferencia WHERE idSetorDestino = " + this.getFuncionarioLogado().getSetor().getId();
            this.btnAceitar.setVisible(true);
        }

        this.transferencias = this.listar(query);
        this.atualizarTabela();
        this.tblTransferencia.refresh();
        this.tblTransferencia.getSelectionModel().selectFirst();
    }

    public List<Transferencia> listar(String query) {
        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        Query consulta = em.createNativeQuery(query, Transferencia.class);
        List<Transferencia> transferencias = consulta.getResultList();

        em.getTransaction().commit();
        emf.close();

        return transferencias;
    }

    public void carregarComboBox() {
        List<String> filtros = new ArrayList<>();
        filtros.add("Recebidas");
        filtros.add("Enviadas");
        filtros.add("Todas");

        this.obsFiltros = FXCollections.observableArrayList(filtros);
        this.edtFiltros.setItems(this.obsFiltros);
        this.edtFiltros.getSelectionModel().selectFirst();
        this.filtrarTransferencias();
    }

    private void atualizarTabela() {
        this.tblColumCodigo.setCellValueFactory(new PropertyValueFactory<Transferencia, Long>("id"));
        this.tblColumStatus.setCellValueFactory(new PropertyValueFactory<Transferencia, String>("status"));

        this.tblTransferencia.setItems(FXCollections.observableArrayList(this.transferencias));
    }

    /**
     * @return the tblTransferencia
     */
    public TableView<Transferencia> getTblTransferencia() {
        return tblTransferencia;
    }

    /**
     * @param tblTransferencia the tblTransferencia to set
     */
    public void setTblTransferencia(TableView<Transferencia> tblTransferencia) {
        this.tblTransferencia = tblTransferencia;
    }

    /**
     * @return the tblColumCodigo
     */
    public TableColumn<Transferencia, Long> getTblColumCodigo() {
        return tblColumCodigo;
    }

    /**
     * @param tblColumCodigo the tblColumCodigo to set
     */
    public void setTblColumCodigo(TableColumn<Transferencia, Long> tblColumCodigo) {
        this.tblColumCodigo = tblColumCodigo;
    }

    /**
     * @return the tblColumStatus
     */
    public TableColumn<Transferencia, String> getTblColumStatus() {
        return tblColumStatus;
    }

    /**
     * @param tblColumStatus the tblColumStatus to set
     */
    public void setTblColumStatus(TableColumn<Transferencia, String> tblColumStatus) {
        this.tblColumStatus = tblColumStatus;
    }

    /**
     * @return the lblDescricao
     */
    public TextArea getLblDescricao() {
        return lblDescricao;
    }

    /**
     * @param lblDescricao the lblDescricao to set
     */
    public void setLblDescricao(TextArea lblDescricao) {
        this.lblDescricao = lblDescricao;
    }

    /**
     * @return the lblData
     */
    public Label getLblData() {
        return lblData;
    }

    /**
     * @param lblData the lblData to set
     */
    public void setLblData(Label lblData) {
        this.lblData = lblData;
    }

    /**
     * @return the lblSetorDestinatario
     */
    public Label getLblSetorDestinatario() {
        return lblSetorDestinatario;
    }

    /**
     * @param lblSetorDestinatario the lblSetorDestinatario to set
     */
    public void setLblSetorDestinatario(Label lblSetorDestinatario) {
        this.lblSetorDestinatario = lblSetorDestinatario;
    }

    /**
     * @return the lblStatus
     */
    public Label getLblStatus() {
        return lblStatus;
    }

    /**
     * @param lblStatus the lblStatus to set
     */
    public void setLblStatus(Label lblStatus) {
        this.lblStatus = lblStatus;
    }

    /**
     * @return the lblFuncionario
     */
    public Label getLblFuncionario() {
        return lblFuncionario;
    }

    /**
     * @param lblFuncionario the lblFuncionario to set
     */
    public void setLblFuncionario(Label lblFuncionario) {
        this.lblFuncionario = lblFuncionario;
    }

    /**
     * @return the lblId
     */
    public Label getLblId() {
        return lblId;
    }

    /**
     * @param lblId the lblId to set
     */
    public void setLblId(Label lblId) {
        this.lblId = lblId;
    }

    /**
     * @return the lblSetorOrigem
     */
    public Label getLblSetorOrigem() {
        return lblSetorOrigem;
    }

    /**
     * @param lblSetorOrigem the lblSetorOrigem to set
     */
    public void setLblSetorOrigem(Label lblSetorOrigem) {
        this.lblSetorOrigem = lblSetorOrigem;
    }

    /**
     * @return the btnAceitar
     */
    public Button getBtnAceitar() {
        return btnAceitar;
    }

    /**
     * @param btnAceitar the btnAceitar to set
     */
    public void setBtnAceitar(Button btnAceitar) {
        this.btnAceitar = btnAceitar;
    }

    /**
     * @return the edtFiltros
     */
    public ComboBox<String> getEdtFiltros() {
        return edtFiltros;
    }

    /**
     * @param edtFiltros the edtFiltros to set
     */
    public void setEdtFiltros(ComboBox<String> edtFiltros) {
        this.edtFiltros = edtFiltros;
    }

    /**
     * @return the painelPrincipal
     */
    public BorderPane getPainelPrincipal() {
        return painelPrincipal;
    }

    /**
     * @param painelPrincipal the painelPrincipal to set
     */
    public void setPainelPrincipal(BorderPane painelPrincipal) {
        this.painelPrincipal = painelPrincipal;
    }

    /**
     * @return the transferencias
     */
    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    /**
     * @param transferencias the transferencias to set
     */
    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    /**
     * @return the obsFiltros
     */
    public ObservableList<String> getObsFiltros() {
        return obsFiltros;
    }

    /**
     * @param obsFiltros the obsFiltros to set
     */
    public void setObsFiltros(ObservableList<String> obsFiltros) {
        this.obsFiltros = obsFiltros;
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
}
