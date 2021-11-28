package tpsit.clientservermonothread.client;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class Client {
    
    private int porta; //porta su cui si aspetta i client (1 client se è monothread)
    private String indirizzoServer; //indirizzo del server

    private Socket server; //il socket per il server

    //cose per la comunicazione
    private BufferedReader ricevi; //qui si riceverà ciò verrà inviato dal server
    private DataOutputStream invia; //inviare i messaggi al server

    public Client(int porta, String indirizzoServer) {
        this.porta = porta;
        this.indirizzoServer = indirizzoServer;
    }
    
    public void connetti(){
        try {
            System.out.println("Connessione al server...");
            server = new Socket(indirizzoServer, porta);
            
            ricevi = new BufferedReader(new InputStreamReader(server.getInputStream()));
            invia = new DataOutputStream(server.getOutputStream());
            
        } catch (IOException ex) {
            System.out.println("Errore nella connessione verso il server\n" + ex.getMessage());
        }
    }
    
    public void comunica(){
        System.out.println("-------------Inizio comunicazione-------------");
        Scanner tastiera = new Scanner(System.in);
        String input;
        
        while(true){
            try {
                System.out.println(ricevi.readLine());
                input = tastiera.nextLine();
                
                invia.writeBytes(input + '\n');
                
                if(input.equalsIgnoreCase("chiudi")){
                    break;
                }
                
            } catch (IOException ex) { System.out.println("Errore nella comunicazione \n " + ex.getMessage());}
        }
        tastiera.close();
        chiudi();
    }
    
    public void chiudi(){
        System.out.println("Chiusura in corso");
        try{
            ricevi.close();
            invia.close();
            server.close();
        }catch (IOException ex){System.out.println("Errore durante la chiusura\n" + ex.getMessage());}
    }
    
}


