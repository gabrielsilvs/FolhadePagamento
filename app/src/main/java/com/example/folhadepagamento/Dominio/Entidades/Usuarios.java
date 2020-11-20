package com.example.folhadepagamento.Dominio.Entidades;

import java.util.Date;

public class Usuarios {

    private String uid, nome, empresa, email;
    private Date data_admissao;
    private Double salario_bruto;

    public Usuarios(){

    }

    public Usuarios(String uid, String nome, String empresa, String email, Date data_admissao, Double salario_bruto) {
        this.uid = uid;
        this.nome = nome;
        this.empresa = empresa;
        this.email = email;
        this.data_admissao = data_admissao;
        this.salario_bruto = salario_bruto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getData_admissao() {
        return data_admissao;
    }

    public void setData_admissao(Date data_admissao) {
        this.data_admissao = data_admissao;
    }

    public Double getSalario_bruto() {
        return salario_bruto;
    }

    public void setSalario_bruto(Double salario_bruto) {
        this.salario_bruto = salario_bruto;
    }
}
