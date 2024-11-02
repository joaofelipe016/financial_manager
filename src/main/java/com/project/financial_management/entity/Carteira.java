package com.project.financial_management.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCarteira;

    @Column
    private double vlSaldo;

    @Column
    private Date dtMovimentacao;

    @OneToOne
    private Usuario usuario;

    public UUID getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(UUID idCarteira) {
        this.idCarteira = idCarteira;
    }

    public Date getDtMovimentacao() {
        return dtMovimentacao;
    }

    public void setDtMovimentacao(Date dtMovimentacao) {
        this.dtMovimentacao = dtMovimentacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getVlSaldo() {
        return vlSaldo;
    }

    public void setVlSaldo(double vlSaldo) {
        this.vlSaldo = vlSaldo;
    }
}
