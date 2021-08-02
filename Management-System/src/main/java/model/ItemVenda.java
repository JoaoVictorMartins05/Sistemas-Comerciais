/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "itemVenda")
public class ItemVenda {
    
    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "idVenda", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "idEstoque", nullable = false)
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "idSetor", nullable = false)
    private Setor setor;

    @Column(nullable = false)
    private double quantidade;

    public ItemVenda() {
    }

    public ItemVenda(long id, Venda venda, Estoque material, Setor setor, double quantidade) {
        this.id = id;
        this.venda = venda;
        this.estoque = material;
        this.setor = setor;
        this.quantidade = quantidade;
    }



    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * @return the material
     */
    public Estoque getMaterial() {
        return estoque;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Estoque material) {
        this.estoque = material;
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
    
}
