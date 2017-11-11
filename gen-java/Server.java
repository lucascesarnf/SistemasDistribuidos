//package chavevalor;

import java.util.Scanner;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;

// Generated code
import Grafo.*;

import java.util.HashMap;

public class Server {
    
    public static GrafoHandler grafo;
    
    public static Operacoes.Processor processor;
    
    public static void main(String [] args) {
        
        int[] clients = {9090, 8129, 9092, 8978, 9056};
        
        if (args.length != 1) {
            System.out.println("Por favor entre com um numero de identificação entre 0-4 para o servidor \nExemplo: java Server 0");
            System.exit(0);
        }
        try {
            int index = Integer.parseInt(args[0]);
            int port = 0;
            if(index >= 0 && index < 5){
                port = clients[index];
                grafo = new GrafoHandler();
                grafo.selfPorta = port;
                System.out.println("grafoHandler.porta = " + grafo.selfPorta);
                processor = new Operacoes.Processor(grafo);
                TServerTransport serverTransport = new TServerSocket(port);
                TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
                
                System.out.println("Starting server...");
                server.serve();
                
            }else{
                System.out.println("Escolha uma identificação válida, entre 0 e 4");
                System.exit(0);
            }
            
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
