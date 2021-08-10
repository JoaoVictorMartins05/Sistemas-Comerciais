/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Funcionario;

/**
 * FXML Controller class
 *
 * @author elisson
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    private Stage stage;

    private List<Funcionario> funcionarios;
    private FuncionarioController controller;

    private EntityManagerFactory emf;
    private EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setFuncionarios(new ArrayList<>());
        this.setController(new FuncionarioController());

        emf = Persistence.createEntityManagerFactory("venda");
        em = emf.createEntityManager();
    }

    @FXML
    public void entrar() throws IOException, Exception {
        if (this.validaCampos()) {
            String usuario = this.getTxtUsuario().getText();
            String senha = this.getTxtSenha().getText();

            //transformando senha que usuário digitou em hexadecial para comparar com senha do banco
            
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

            StringBuilder hexStringSenhaAdmin = new StringBuilder();
            for (byte b : messageDigest) {
                hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
            }
            String senhahexadecimal = hexStringSenhaAdmin.toString();
            senha = senhahexadecimal;
            //fim transformação
            
            getEm().getTransaction().begin();
            Query consulta = getEm().createQuery("Select funcionario from Funcionario funcionario");
            List<Funcionario> funcionarios = consulta.getResultList();

            getEm().getTransaction().commit();

            boolean flag = false;
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getUsuario().equals(usuario) && funcionario.getSenha().equals(senha)) {
                    getEmf().close();
                    this.mostrarTela(funcionario);
                    this.stage.close();
                    this.getController().setFuncionario(funcionario);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Usuário e senha inválido");
                alert.show();
            }
        }
        //vai sempre mostrar a tela (logar) pq ainda n tem funcionario cadastrado para logar no sistema
        //this.mostrarTela(new Funcionario());
    }

    private boolean validaCampos() {
        if (getTxtUsuario().getText().equals("") || getTxtSenha().getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preencha todos os campos!!");
            alert.show();
            return false;
        }
        return true;
    }

    private void mostrarTela(Funcionario funcionario) throws IOException {
        Parent root;
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/fxml/Main.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            MainController controller = loader.getController();

            controller.setFuncionario(funcionario);

            try {
                controller.mostrarTela("/fxml/Venda.fxml");
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

            stage.setMinWidth(tela.getWidth());
            stage.setHeight(tela.getHeight());

            System.out.println(tela.getWidth());
            System.out.println(tela.getHeight());

            //stage.setFullScreen(true);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the funcionarios
     */
    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    /**
     * @param funcionarios the funcionarios to set
     */
    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    /**
     * @return the controller
     */
    public FuncionarioController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(FuncionarioController controller) {
        this.controller = controller;
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
     * @return the txtUsuario
     */
    public TextField getTxtUsuario() {
        return txtUsuario;
    }

    /**
     * @param txtUsuario the txtUsuario to set
     */
    public void setTxtUsuario(TextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    /**
     * @return the txtSenha
     */
    public PasswordField getTxtSenha() {
        return txtSenha;
    }

    /**
     * @param txtSenha the txtSenha to set
     */
    public void setTxtSenha(PasswordField txtSenha) {
        this.txtSenha = txtSenha;
    }

    /**
     * @return the btnEntrar
     */
    public Button getBtnEntrar() {
        return btnEntrar;
    }

    /**
     * @param btnEntrar the btnEntrar to set
     */
    public void setBtnEntrar(Button btnEntrar) {
        this.btnEntrar = btnEntrar;
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
