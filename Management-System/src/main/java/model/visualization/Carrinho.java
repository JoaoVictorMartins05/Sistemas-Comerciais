/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visualization;

/**
 *
 * @author elisson
 */
public class Carrinho {
    private Long codigoMaterial;
    private String nomeMaterial;
    private double quantidade;
    private String unidadeMedidaMaterial;
    private double valorMaterial;
    private double valorTotal;    

    public Carrinho() {
        this.codigoMaterial = -1L;
        this.nomeMaterial = "";
        this.quantidade = -1;
        this.unidadeMedidaMaterial = "";
        this.valorMaterial = -1;
        this.valorTotal = -1;
    }

    public Carrinho(Long codigoMaterial, String nomeMaterial, double quantidade, String unidadeMedidaMaterial, double valorMaterial, double valorTotal) {
        this.codigoMaterial = codigoMaterial;
        this.nomeMaterial = nomeMaterial;
        this.quantidade = quantidade;
        this.unidadeMedidaMaterial = unidadeMedidaMaterial;
        this.valorMaterial = valorMaterial;
        this.valorTotal = valorTotal;
    }        

    /**
     * @return the codigoMaterial
     */
    public Long getCodigoMaterial() {
        return codigoMaterial;
    }

    /**
     * @param codigoMaterial the codigoMaterial to set
     */
    public void setCodigoMaterial(Long codigoMaterial) {
        this.codigoMaterial = codigoMaterial;
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
     * @return the unidadeMedidaMaterial
     */
    public String getUnidadeMedidaMaterial() {
        return unidadeMedidaMaterial;
    }

    /**
     * @param unidadeMedidaMaterial the unidadeMedidaMaterial to set
     */
    public void setUnidadeMedidaMaterial(String unidadeMedidaMaterial) {
        this.unidadeMedidaMaterial = unidadeMedidaMaterial;
    }

    /**
     * @return the valorMaterial
     */
    public double getValorMaterial() {
        return valorMaterial;
    }

    /**
     * @param valorMaterial the valorMaterial to set
     */
    public void setValorMaterial(double valorMaterial) {
        this.valorMaterial = valorMaterial;
    }

    /**
     * @return the valorTotal
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * @param valorTotal the valorTotal to set
     */
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    
}
