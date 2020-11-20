package com.example.folhadepagamento.Dominio.Entidades;

import java.io.Serializable;
import java.util.Date;

public class FolhaPagamento implements Serializable {

    private String empresa, mesAno, nome;
    private Date data_admissao;
    private Integer mes, ano, faltas, dias_dsr, dias_trabalhados;
    private Double salario_bruto, base_inss, inss, base_fgts, fgts, total_vencimentos, total_descontos, salario_liquido, valor_faltas, valor_dsr;

    public FolhaPagamento() {
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_admissao() {
        return data_admissao;
    }

    public void setData_admissao(Date data_admissao) {
        this.data_admissao = data_admissao;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getFaltas() {
        return faltas;
    }

    public void setFaltas(Integer faltas) {
        this.faltas = faltas;
    }

    public Integer getDias_dsr() {
        return dias_dsr;
    }

    public void setDias_dsr(Integer dias_dsr) {
        this.dias_dsr = dias_dsr;
    }

    public Double getSalario_bruto() {
        return salario_bruto;
    }

    public void setSalario_bruto(Double salario_bruto) {
        this.salario_bruto = salario_bruto;
    }

    public Double getBase_inss() {
        return base_inss;
    }

    public void setBase_inss(Double base_inss) {
        this.base_inss = base_inss;
    }

    public Double getInss() {
        return inss;
    }

    public void setInss(Double inss) {
        this.inss = inss;
    }

    public Double getBase_fgts() {
        return base_fgts;
    }

    public void setBase_fgts(Double base_fgts) {
        this.base_fgts = base_fgts;
    }

    public Double getFgts() {
        return fgts;
    }

    public void setFgts(Double fgts) {
        this.fgts = fgts;
    }

    public Double getTotal_vencimentos() {
        return total_vencimentos;
    }

    public void setTotal_vencimentos(Double total_vencimentos) {
        this.total_vencimentos = total_vencimentos;
    }

    public Double getTotal_descontos() {
        return total_descontos;
    }

    public void setTotal_descontos(Double total_descontos) {
        this.total_descontos = total_descontos;
    }

    public Double getSalario_liquido() {
        return salario_liquido;
    }

    public void setSalario_liquido(Double salario_liquido) {
        this.salario_liquido = salario_liquido;
    }

    public Double getValor_faltas() {
        return valor_faltas;
    }

    public void setValor_faltas(Double valor_faltas) {
        this.valor_faltas = valor_faltas;
    }

    public Double getValor_dsr() {
        return valor_dsr;
    }

    public void setValor_dsr(Double valor_dsr) {
        this.valor_dsr = valor_dsr;
    }

    public Integer getDias_trabalhados() {
        return dias_trabalhados;
    }

    public void setDias_trabalhados(Integer dias_trabalhados) {
        this.dias_trabalhados = dias_trabalhados;
    }
}
