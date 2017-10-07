//package chavevalor;

import Grafo.*;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class Client {
    public static void main(String [] args) {
        if (args.length != 2) {
            System.out.println("Por favor entre com um host e um numero de porta \nExemplo: java Client localhost 9090");
            System.exit(0);
        }
        try {
            
            int port = Integer.parseInt(args[1]);
            if(port > 0 && port < 65535){
                TTransport transport = new TSocket(args[0], port);
                transport.open();
                
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                //Faça as operações AQUI
                
            }else{
                System.out.println("Escolha uma porta válida, entre 0 e 65535");
                System.exit(0);
            }
            
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
