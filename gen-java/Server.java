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
        if (args.length != 1) {
            System.out.println("Por favor entre com um numero de porta \nExemplo: java Server 9090");
            System.exit(0);
        }
        try {
            
            int port = Integer.parseInt(args[0]);
            if(port > 0 && port < 65535){
                
                grafo = new GrafoHandler();
                processor = new Operacoes.Processor(grafo);
                
                TServerTransport serverTransport = new TServerSocket(port);
                TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
                
                System.out.println("Starting server...");
                server.serve();
                
            }else{
                System.out.println("Escolha uma porta válida, entre 0 e 65535");
                System.exit(0);
            }
            
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
