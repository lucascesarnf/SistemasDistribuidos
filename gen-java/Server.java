//package chavevalor;

import java.util.Scanner;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
//###########
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.lang.*;
import java.lang.Math;
//###########

// Generated code
import Grafo.*;

import java.util.HashMap;

public class Server {
    
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream("setup.properties");
        props.load(file);
        return props;
        
    }
    
    public static int[][] generateFT(int mySelf){
        
        Properties prop = getProp();
        String porta;
        String p;
        
        int mBits = Integer.parseInt(prop.getProperty("mBits"));
        int numberOfServers = Integer.parseInt(prop.getProperty("numberOfServers"));
        
        int[][] ft = new int[mBits][2];
        int[][] servers = new int[numberOfServers][2];
        
        
        //Carrega todos os servidores da rede para montar a finger table
        for(int i = 0; i < servers.length ; i++){
            porta = prop.getProperty("server." + i+".port");
            p = prop.getProperty("server." + i+".k");
            servers[i][0] = Integer.parseInt(p);
            servers[i][1] = Integer.parseInt(porta);
        }
        
        //Monta a FingerTable
        for(int i = 0; i < ft.length ; i++){
          int succ = mySelf + Math.pow(2,i-1)
            for(int i = 0; i < servers.length - 1 ; i++){
                if(servers[i][0] <= succ && succ <=servers[i+1][0]){
                      ft[i][0] = servers[i+1][0]
                      ft[i][1] = servers[i+1][1]
                }
            }
        }
        //Ftp[i]=succ(p+2 i -1)
    }
    
    public static GrafoHandler grafo;
    
    public static Operacoes.Processor processor;
    
    
    public static void main(String [] args) {
        
        if (args.length != 1) {
            System.out.println("Por favor entre com um numero de identificação válido\nExemplo: java Server 0");
            System.exit(0);
        }
        
        try {
            Properties prop = getProp();
            String porta;
            String p;
            int index = Integer.parseInt(args[0]);
            porta = prop.getProperty("server."+index+".port");
            p = prop.getProperty("server."+index+".p");
            int port = Integer.parseInt(porta);
            
            grafo = new GrafoHandler();
            
            grafo.selfPorta = port;
            grafo.selfIndex = index;
            grafo.selfNo = Integer.parseInt(p);
            
            System.out.println("grafoHandler.porta["+grafo.selfNo+"] = " + grafo.selfPorta);
            processor = new Operacoes.Processor(grafo);
            TServerTransport serverTransport = new TServerSocket(port);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            
            System.out.println("Starting server...");
            server.serve();
            
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
