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
import javax.persistence.Table;

/**
 *
 * @author joaov
 */

@Entity
@Table(name = "transferencia")
public class Transferencia {
    @Id
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "idSetorOrigem", nullable = false)
    private Setor setorOrigem;
    
    @ManyToOne
    @JoinColumn(name = "idSetorDestino", nullable = false)
    private Setor setorDestino;
    
    @ManyToOne
    @JoinColumn(name = "idFuncionario", nullable = false)
    private Funcionario funcionario;
    
    @Column(nullable = false)
    private String status;
    
    @Column(nullable = false)
    private String Descricao;
    
    @Column(nullable = false)   
    private Date data;

    public Transferencia() {
    }

    public Transferencia(long id, Setor setorOrigem, Setor setorDestino, String status, String Descricao, Date data) {
        this.id = id;
        this.setorOrigem = setorOrigem;
        this.setorDestino = setorDestino;
        this.status = status;
        this.Descricao = Descricao;
        this.data = data;
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
     * @return the setorOrigem
     */
    public Setor getSetorOrigem() {
        return setorOrigem;
    }

    /**
     * @param setorOrigem the setorOrigem to set
     */
    public void setSetorOrigem(Setor setorOrigem) {
        this.setorOrigem = setorOrigem;
    }

    /**
     * @return the setorDestino
     */
    public Setor getSetorDestino() {
        return setorDestino;
    }

    /**
     * @param setorDestino the setorDestino to set
     */
    public void setSetorDestino(Setor setorDestino) {
        this.setorDestino = setorDestino;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the funcionario
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * @param funcionario the funcionario to set
     */
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
}
