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
            int[][] ft;
        try{
            Properties prop = getProp();
            String porta;
            String p;
            
            int mBits = Integer.parseInt(prop.getProperty("mBits"));
            int numberOfServers = Integer.parseInt(prop.getProperty("numberOfServers"));
            
            ft = new int[mBits][2]; 
            int[][] servers = new int[numberOfServers][2];
            
            
            //Carrega todos os servidores da rede para montar a finger table
            for(int i = 0; i < servers.length ; i++){
                porta = prop.getProperty("server." + i+".port");
                p = prop.getProperty("server." + i+".p");
                servers[i][0] = Integer.parseInt(p);
                servers[i][1] = Integer.parseInt(porta);
            }
            
            //Monta a FingerTable
            boolean passou = false;
            for(int i = 0; i < ft.length ; i++){
              double succ = mySelf + Math.pow(2,i);
              succ = succ % Math.pow(2,mBits);
              passou = false;
                for(int j = 0; j < servers.length - 1 ; j++){


                    if(servers[j][0] <= succ && succ <=servers[j+1][0]){
                          ft[i][0] = servers[j+1][0];
                          ft[i][1] = servers[j+1][1];
                          j = servers.length;
                          passou = true;
                    }
                }
                if(!passou){
                    ft[i][0] = servers[0][0];
                          ft[i][1] = servers[0][1];
                }
            }

        return ft;
        }
        catch(Exception e){
            e.printStackTrace();    
        }
        return  new int[5][2];
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
            
            int mBits = Integer.parseInt(prop.getProperty("mBits"));
            int index = Integer.parseInt(args[0]);
            porta = prop.getProperty("server."+index+".port");
            p = prop.getProperty("server."+index+".p");
            int port = Integer.parseInt(porta);
            
            grafo = new GrafoHandler();
            
            grafo.selfPorta = port;
            grafo.selfIndex = index;
            grafo.selfNo = Integer.parseInt(p);
            grafo.ft = generateFT(grafo.selfNo);
            grafo.modulo = mBits;

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
