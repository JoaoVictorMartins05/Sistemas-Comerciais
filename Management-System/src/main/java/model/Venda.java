/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author joaov
 */

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "idSetor", nullable = false)
    private Setor setor;
    
    @Column(nullable = false)
    private Date data;
    
    @Column(nullable = false)
    private double valorTotal;
    
    @Column(nullable = false)
    private String Descricao;

    
    public Venda() {
    }

    
    public Venda(int id, Setor setor, Date data, double valorTotal, String Descricao) {
        this.id = id;
        this.setor = setor;
        this.data = data;
        this.valorTotal = valorTotal;
        this.Descricao = Descricao;
    }
    
    
       
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the itemVenda
     */

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
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
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

    /**
     * @return the Descricao
     */
    public String getDescricao() {
        return Descricao;
    }

    /**
     * @param Descricao the Descricao to set
     */
    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }
    
}
