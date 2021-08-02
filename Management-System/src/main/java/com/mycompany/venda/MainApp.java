package com.mycompany.venda;

import controller.LoginController;
import controller.MainController;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Estoque;
import model.Funcionario;
import model.Material;
import model.Setor;
import model.Venda;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(this.getClass().getResource("/fxml/Login.fxml"));

        Parent root = loader.load();        
        Scene scene = new Scene(root);     
                
        stage.setScene(scene);
        
        stage.show();    
        
        LoginController ctrl = loader.getController();
        ctrl.setStage(stage);
    }

    public static void main() {
        //launch(args);

        //Material m = new Material(1L, "Canudo", "Verde", "Metros", 25000);
//        Setor s = new Setor();
//        Funcionario f = new Funcionario();
//        Estoque e = new Estoque();
//        //ItemVenda i = new ItemVenda();
//        Venda v = new Venda();
//
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("venda");
//        EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//        em.merge(m);
//        em.getTransaction().commit();
    }
}
