package dominio;

import Exception.NaoExisteContaException;
import Exception.NaoExisteDadosException;
import Exception.NaoTemDinheiroException;
import java.util.ArrayList;
import java.util.Iterator;

public class Banco {
    
    private ArrayList<Pessoa> clientes;

    public Banco() {
        this.clientes = new ArrayList<>();
    }

    public ArrayList<Pessoa> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Pessoa> clientes) {
        this.clientes = clientes;
    }
    
    public String abrirConta(Pessoa pessoa, Conta conta) throws NaoExisteDadosException
    {
        if(!((pessoa.getNome().equals("")) || (pessoa.getContato().getEmail().equals("")) 
                || (pessoa.getEndereo().getCidade().equals("")) || (pessoa.getEndereo().getEstado().equals("")) ))
        {
            pessoa.setContas(conta);
            clientes.add(pessoa);

            String mensage = String.format("SEJA BEM VINDO AO NOSSO BANCO %s, SUA CONTA FOI CRIADA\nLOGIN: %s\nSENHA: %d",pessoa.getNome(),pessoa.getLoginDaConta(),pessoa.getSenhaDaConta());
            return mensage; 
        }
        
        else
        {
            throw new NaoExisteDadosException();
        }

    }
    
    public void fecharConta(String nomeDoCliente, Conta conta, String numeroContaASerFechada)
    {
        Iterator<Pessoa> it_pessoa = clientes.iterator();
        
        while(it_pessoa.hasNext())
        {
            Pessoa aux_pessoa = it_pessoa.next();
            
            if(aux_pessoa.getNome().equals(nomeDoCliente))
            {
                Iterator<Conta> it_conta = aux_pessoa.getContas().iterator();
                while(it_conta.hasNext())
                {
                    Conta aux_conta = it_conta.next();
                    if(aux_conta.getNumero().equals(numeroContaASerFechada))
                    {
                        it_conta.remove();
                        it_pessoa.remove();                                              
                    }
                    
                }
            }
        }
    }
    
    public String verificaLogin(String login, String senha)
    {
        Iterator<Pessoa> it = clientes.iterator();
        
        while(it.hasNext())
        {
            Pessoa aux = it.next();
            if(aux.getLoginDaConta().equals(login))
            {
                if(aux.getSenhaDaConta() == Integer.parseInt(senha))
                {
                    return aux.getNome();
                }
            }
        }
        return null;
    }
    
    public double saldoContaCliente(String numeroDaConta)
    {
        Iterator<Pessoa> it_pessoas = clientes.iterator();
        
        while(it_pessoas.hasNext())
        {
            Pessoa aux_pessoas = it_pessoas.next();
            if(aux_pessoas.getLoginDaConta().equals(numeroDaConta))
            {
                Iterator<Conta> it_contas = aux_pessoas.getContas().iterator();
                while(it_contas.hasNext())
                {
                    Conta conta_aux = it_contas.next();
                    
                    if(conta_aux.getNumero().equals(numeroDaConta))
                        return conta_aux.getSaldoTotal();
                }
            }
        }
        return 0;
    }
    
    public void saque(String numeroDaConta, double valorDoSaque) throws NaoTemDinheiroException
    {
        double saldo = saldoContaCliente(numeroDaConta);
        if(saldo >= valorDoSaque)
        {
            Iterator<Pessoa> it_pessoas = clientes.iterator();
        
            while(it_pessoas.hasNext())
            {
                Pessoa aux_pessoas = it_pessoas.next();
                if(aux_pessoas.getLoginDaConta().equals(numeroDaConta))
                {
                    Iterator<Conta> it_contas = aux_pessoas.getContas().iterator();
                    while(it_contas.hasNext())
                    {
                        Conta conta_aux = it_contas.next();

                        if(conta_aux.getNumero().equals(numeroDaConta))
                        {
                            conta_aux.setSaldoTotal(conta_aux.getSaldoTotal()-valorDoSaque);
                        }
                    }
                }
            }    
        }
        else
        {
            throw new NaoTemDinheiroException();
        }
    }
    
    public void deposito(String numeroDaConta, double valorDeposito) 
    {
        Iterator<Pessoa> it_pessoas = clientes.iterator();

        while (it_pessoas.hasNext()) {
            Pessoa aux_pessoas = it_pessoas.next();
            if (aux_pessoas.getLoginDaConta().equals(numeroDaConta)) {
                Iterator<Conta> it_contas = aux_pessoas.getContas().iterator();
                while (it_contas.hasNext()) {
                    Conta conta_aux = it_contas.next();

                    if (conta_aux.getNumero().equals(numeroDaConta)) {
                        conta_aux.setSaldoTotal(conta_aux.getSaldoTotal() + valorDeposito);
                    }
                }
            }
        }
    }
    
    public void transferir(String numeroContaOrigem, String numeroDaContaDestino, double valorTransferencia) throws NaoExisteContaException, NaoTemDinheiroException
    {
        int registrador = 0;
        //se conseguir sacar, ele pode transferir
        this.saque(numeroContaOrigem,valorTransferencia);
        
            Iterator<Pessoa> it_pessoas = clientes.iterator();

            while (it_pessoas.hasNext()) 
            {
                Pessoa aux_pessoas = it_pessoas.next();
                if (aux_pessoas.getLoginDaConta().equals(numeroDaContaDestino)) {
                    Iterator<Conta> it_contas = aux_pessoas.getContas().iterator();
                    while (it_contas.hasNext()) 
                    {
                        Conta conta_aux = it_contas.next();
                        if (conta_aux.getNumero().equals(numeroDaContaDestino)) 
                        {
                                conta_aux.setSaldoTotal(conta_aux.getSaldoTotal() + valorTransferencia);//realizada
                                registrador ++;
                        }
                    }
                }
            }
            
            if(registrador == 0)
                throw new NaoExisteContaException();
    }
}
