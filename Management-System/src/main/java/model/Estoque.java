/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author joaov
 */

@Entity
@Table(name = "Estoque")
public class Estoque {
    
    @Id
    private Long id;


    @ManyToOne
    @JoinColumn(name = "idSetor", nullable = false)
    private Setor setor;
    

    @ManyToOne
    @JoinColumn(name = "idMaterial", nullable = false)
    private Material material;
    
    @Column(nullable = false)
    private double quantidade;


    public Estoque() {
        
    }

    public Estoque(Setor setor, Material material, double quantidade) {
        this.setor = setor;
        this.material = material;
        this.quantidade = quantidade;
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
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material the material to set
     */
    public void setMaterial(Material material) {
        this.material = material;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
