/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author elisson
 */

@Entity
@Table(name = "setor")
public class Setor {
 
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String abreviacao;
    
    @Column(nullable = false)
    private String descricao;

    public Setor() {
        this.id = -1L;       
        this.nome = "";
        this.abreviacao = "";
        this.descricao = "";
    }

    public Setor(Long id, String nome, String abreviacao, String descricao) {
        this.id = id;
        this.nome = nome;
        this.abreviacao = abreviacao;
        this.descricao = descricao;
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
     * @return the abreviacao
     */
    public String getAbreviacao() {
        return abreviacao;
    }

    /**
     * @param abreviacao the abreviacao to set
     */
    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}