package servicos;

import dominio.Banco;
import dominio.Conta;
import dominio.Pessoa;
import java.io.FileNotFoundException;         
import java.util.ArrayList;
import java.util.Formatter;               
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;               

public class CreateTextFile{
    
   private static Formatter output; // outputs text to a file       

   // open file agenda.txt
   public static void openFile(String URL){
      try{
         output = new Formatter(URL); // open the file
      } catch (SecurityException securityException) {
         System.err.println("Write permission denied. Terminating.");
         System.exit(1); // terminate the program
      } catch (FileNotFoundException fileNotFoundException) {
         System.err.println("Error opening file. Terminating.");
         System.exit(1); // terminate the program
      } 
   } 

   // add records to file
   public static void addRecords(Banco b){
        try {
            ArrayList<Pessoa> clientes = b.getClientes();
            String URL = null;
            for(Pessoa cliente : clientes){
                URL = cliente.getLoginDaConta() + ".txt";
                openFile(URL);
                
                String senha = String.valueOf(cliente.getSenhaDaConta());
                String nome = cliente.getNome();
                
                output.format("%s\n", senha); 
                output.format("Dados do Usuario:\n%s\n", nome); 
                
                String telefone = cliente.getContato().getTelefone();
                String email = cliente.getContato().getEmail();
                
                output.format("%s\n%s\n", telefone, email); 
                
                String rua = cliente.getEndereco().getRua();
                String numero = cliente.getEndereco().getNumero();
                String bairro = cliente.getEndereco().getBairro();
                String cidade = cliente.getEndereco().getCidade();
                String estado = cliente.getEndereco().getEstado();
                
                output.format("%s\n%s\n%s\n%s\n%s\n", rua, numero, bairro, cidade, estado);
                
                ArrayList<Conta> contas = cliente.getContas();
                
                String conta = "";
                
                for(Conta c : contas){
                    conta = c.toString();
                    output.format("%s", conta);
                }
                
                //Arquivo de cada Cliente
                //Nome do arquivo: loginDaConta.txt
                //Linhas:
                //$ senha x
                //$ Dados do Usuario:
                //$ nome do cliente x
                //$ Contato: 
                //$ telefone x
                //$ email x
                //$ Endereço:
                //$ rua x
                //$ numero x
                //$ bairro x
                //$ cidade x
                //$ estado x
                //$ Contas (Loop) x
                    //$ agencia
                    //$ numero
                    //$ numeroDaContaDoNovoCliente
                    //$ numeroDaContaDoUltimoCliente
                    //$ saldoTotal
                    //$ tipo(?)
            }
                                        
        } 
        catch (FormatterClosedException formatterClosedException)
        {
            System.err.println("Error writing to file. Terminating.");
        } 
        catch (NoSuchElementException elementException)
        {
            System.err.println("Invalid input. Please try again.");
        }
        
        closeFile();

   }

   // close file
   public static void closeFile(){
      if (output != null)
         output.close();
   } 
} // end class CreateTextFile