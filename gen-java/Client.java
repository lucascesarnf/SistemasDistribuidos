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
    try {
      TTransport transport = new TSocket("localhost", 9090);
      transport.open();

      TProtocol protocol = new  TBinaryProtocol(transport);
      Operacoes.Client client = new Operacoes.Client(protocol);

      int k1 = 1;
      String v1 = "lalaal";

      if(client.novoVertice(k1,k1,2.0,v1))
	      System.out.println("Adicionou Vertice");
      if(client.novoVertice(k1+1,k1,2.0,v1))
	      System.out.println("Adicionou Vertice");
      if(client.novaAresta(k1,k1+1,2.0,2,"Teste"))
	      System.out.println("Adicionou Aresta");
      if(client.novaAresta(k1,3,2.0,4,"nao adiciona"))
	      System.out.println("Adicionou Aresta");
      
      Aresta a = client.retornaAresta(k1,k1+1);
      System.out.println("V1: " + a.getV1() + " V2: " + a.getV2() + " Peso: " + a.getPeso() + " Descricao: " + a.getDescricao() + " Flag:" + a.getFlag());
	
      Vertice v = client.retornaVertice(k1);
      System.out.println("Nome: " + v.getNome() + " Peso: " + v.getPeso() + " Cor: " + v.getCor() + " Descricao: " + v.getDescricao());
       v.setCor(99999);
      client.updateVertice(v,k1);
	v = client.retornaVertice(k1);
      System.out.println("Nome: " + v.getNome() + " Peso: " + v.getPeso() + " Cor: " + v.getCor() + " Descricao: " + v.getDescricao());
      
System.out.println(client.imprimeGrafo());

	System.out.println(client.imprimeVizinhos(k1));
	System.out.println(client.imprimeVertices());

	System.out.println(client.imprimeArestas());
	

    } catch (TException x) {
      x.printStackTrace();
    } 
  }
}
