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
    try {
      grafo = new GrafoHandler();
      processor = new Operacoes.Processor(grafo);

      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();

    } catch (Exception x) 
    {
      x.printStackTrace();
    }
  }
}
