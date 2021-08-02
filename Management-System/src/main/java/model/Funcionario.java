/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. Management-System
 */
package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author elisson
 */
@Entity
@Table(name = "funcionario")
public class Funcionario {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idSetor", nullable = true)
    private Setor setor;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private int nivelAcesso;

    public Funcionario() {
        this.id = -1L;
        this.setor = new Setor();
        this.nome = "";
        this.usuario = "";
        this.senha = "";
        this.nivelAcesso = -1;
    }

    public Funcionario(Long id, Setor setor, String nome, String usuario, String senha, int nivelAcesso) {
        this.id = id;
        this.setor = setor;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    public int converteNivelDeAcessoParaInteiro(String nivelAcesso) {
        switch (nivelAcesso) {
            case "Administrador":
                return 0;
            case "Vendedor":
                return 1;
        }
        return -1;
    }

    public String converteNivelDeAcessoParaString(int nivelAcesso) {
        switch (nivelAcesso) {
            case 0:
                return "Administrador";
            case 1:
                return "Vendedor";
        }
        return "";
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the setor
     */
    public Setor getSetor() {
        return setor;
    }

    /**
     * @param setor the setor to set
     */
    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //this.senha = senha;

        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));

        StringBuilder hexStringSenhaAdmin = new StringBuilder();
        for (byte b : messageDigest) {
            hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
        }
        String senhahexadecimal = hexStringSenhaAdmin.toString();

        System.out.println("senha alterada: " + senhahexadecimal);
        this.senha = senhahexadecimal;
    }

    /**
     * @return the nivelAcesso
     */
    public int getNivelAcesso() {
        return nivelAcesso;
    }

    /**
     * @param nivelAcesso the nivelAcesso to set
     */
    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
