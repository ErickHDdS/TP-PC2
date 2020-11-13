/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author erick
 */
public class ContaPoupanca extends Conta{
    
    private double rendimento;
    private double saldoInvestido;

    public ContaPoupanca() {
        super();
        this.rendimento = 10;
        this.saldoInvestido = 0;
    }
    
    void renderDinheiro()
    {
        double acrescimo = (this.rendimento*getSaldoInvestido())/100;
        
        setSaldoInvestido(getSaldoInvestido()+acrescimo);
    }
    
    public double getRendimento() {
        return rendimento;
    }

    public void setRendimento(double rendimento) {
        this.rendimento = rendimento;
    }

    public double getSaldoInvestido() {
        return saldoInvestido;
    }

    public void setSaldoInvestido(double saldoInvestido) {
        this.saldoInvestido = saldoInvestido;
    }
}