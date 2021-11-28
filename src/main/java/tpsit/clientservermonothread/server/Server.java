package tpsit.clientservermonothread.server;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
    
    private int porta; //porta su cui si aspetta i client (1 client se è monothread)
    
    private ServerSocket server; //Il server socket aspetterà l'arrivo dei client (del client)
    private Socket client; //il socket del client
    
    //cose per la comunicazione
    private BufferedReader ricevi; //qui si riceverà ciò verrà inviato dal client
    private DataOutputStream invia; //inviare i dati al client

    public Server(int porta) {
        this.porta = porta;
        try {
            server = new ServerSocket(porta);
        } catch (IOException ex) { System.out.println("Errore nell'inizializzazione del server\n" + ex.getMessage() ); }
    }
    
    public void connetti(){
        
        try {
            System.out.println("Server in attesa dell'arrivo di un client...");
            client = server.accept();
            System.out.println("Client arrivato: " + client.toString());
            
            ricevi = new BufferedReader(new InputStreamReader(client.getInputStream()));
            invia = new DataOutputStream(client.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("Errore nella connessione");  
            System.err.println(ex.getMessage());
        }
        
    }
    
    public void comunica(){
        String messaggio = "";
        System.out.println("Inizio comunicazione");
        
        
        try {
            invia.writeBytes("Benvenuto nel server, inviami un messaggio" + '\n');
            
            while(true){
                messaggio = ricevi.readLine();
                
                if(messaggio.equalsIgnoreCase("chiudi")){
                    break;
                }
                
                invia.writeBytes("Hai inviato il messaggio \"" + messaggio + "\"" + '\n'); // hai inviato il messaggio "jhkasbfkajb"
            }
        } catch (IOException ex) { 
            System.out.println("Errore nella comunicazione"); 
            System.err.println(ex.getMessage());
            chiudi(); 
        }
        
        chiudi();
    }
    
    public void chiudi(){
        System.out.println("Chiusura in corso...");
        try {
            ricevi.close();
            invia.close();
            client.close();
            server.close();
        } catch (IOException ex) { 
            System.out.println("Errore durante la chiusura"); 
            System.err.println(ex.getMessage());
        }
    }
    
}