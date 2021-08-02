/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.*;

/**
 *
 * @author joaov
 */

@Entity
@Table(name = "itemTransferencia")
public class ItemTransferencia {

 
    @Id
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "idTransferencia", nullable = false)
    private Transferencia tranferencia;
    
    
    @ManyToOne
    @JoinColumn(name = "idEstoque", nullable = false)
    private Estoque estoque;
    
    
    @Column(nullable = false)
    private double quantidade;

    
    
    public ItemTransferencia() {
    }

    
    public ItemTransferencia(long id, Transferencia tranferencia, Estoque estoque, double quantidade) {
        this.id = id;
        this.tranferencia = tranferencia;
        this.estoque = estoque;
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
     * @return the tranferencia
     */
    public Transferencia getTranferencia() {
        return tranferencia;
    }

    /**
     * @param tranferencia the tranferencia to set
     */
    public void setTranferencia(Transferencia tranferencia) {
        this.tranferencia = tranferencia;
    }

    /**
     * @return the estoque
     */
    public Estoque getEstoque() {
        return estoque;
    }

    /**
     * @param estoque the estoque to set
     */
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
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
