/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visualization;

import model.Estoque;
import model.Material;

/**
 *
 * @author elisson
 */
public class EstoqueMaterial {

    private Long id;
    private String nomeSetor;
    private String nomeMaterial;
    private double quantidade;

    public EstoqueMaterial() {
        this.id = -1L;
        this.nomeSetor = "";
        this.nomeMaterial = "";
        this.quantidade = -1;
    }

    public EstoqueMaterial(Long id, String nomeSetor, String nomeMaterial, double quantidade) {
        this.id = id;
        this.nomeSetor = nomeSetor;
        this.nomeMaterial = nomeMaterial;
        this.quantidade = quantidade;
    }

    public Material getMaterial(Estoque estoque) {
        return estoque.getMaterial();
        
    }
    /**
     * @return the nomeSetor
     */
    public String getNomeSetor() {
        return nomeSetor;
    }

    /**
     * @param nomeSetor the nomeSetor to set
     */
    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    /**
     * @return the nomeMaterial
     */
    public String getNomeMaterial() {
        return nomeMaterial;
    }

    /**
     * @param nomeMaterial the nomeMaterial to set
     */
    public void setNomeMaterial(String nomeMaterial) {
        this.nomeMaterial = nomeMaterial;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
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
    
}
