import Grafo.*;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
//###########
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.lang.*;
import java.lang.Math;
//###########

public class Client {
    
    public static Properties getProp() throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(
                                                   "setup.properties");
        props.load(file);
        return props;
        
    }
    
    public static void main(String [] args) {
        
        if (args.length != 1) {
            System.out.println("Por favor entre com um numero de identificação de servidor válido\nExemplo: java Client 0");
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
            TTransport transport = new TSocket("localhost", port);
            transport.open();
            TProtocol protocol = new  TBinaryProtocol(transport);
            Operacoes.Client client = new Operacoes.Client(protocol);
            //Variáveis auxiliares:
            boolean escolher = true;
            int menu = 0;
            int v1, v2, cor;
            String descricao;
            double peso;
            Scanner scan = new Scanner(System.in);
            //Inserções:
            while(escolher){
                System.out.println("#########################################");
                System.out.println("##        Banco de Dados de Grafos     ##");
                System.out.println("##   selecione uma das opções abaixo:  ##");
                System.out.println("##         1 - Inserir Vertice         ##");
                System.out.println("##         2 - Inserir Aresta          ##");
                System.out.println("##         3 - Remover Vertice         ##");
                System.out.println("##         4 - Remover Aresta          ##");
                System.out.println("##         5 - Atualizar Vertice       ##");
                System.out.println("##         6 - Atualizar Aresta        ##");
                System.out.println("##         7 - Imprimir Grafo          ##");
                System.out.println("##         8 - Imprimir Vertices       ##");
                System.out.println("##         9 - Imprimir Arestas        ##");
                System.out.println("##  10 - Listar Arestas de um Vertice  ##");
                System.out.println("##  11 - Listar Vertices de uma Aresta ##");
                System.out.println("##         12 - Listar Vizinhos        ##");
                System.out.println("##         13 - Pegar um Vertice       ##");
                System.out.println("##         14 - Pegar uma Aresta       ##");
                System.out.println("##         15 - Menor Caminho          ##");
                System.out.println("##         16 - Sair                   ##");
                System.out.println("#########################################");
                
                menu = scan.nextInt();
                
                switch (menu) {
                    case 1:
                        System.out.println("\n\nAdicionar Vertice:");
                        System.out.println("Entre com um inteiro para o Nome:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com um inteiro para Cor: ");
                        cor = scan.nextInt();
                        System.out.println("Escreva uma descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Entre com o peso do vertice:");
                        peso = scan.nextDouble();
                        if (client.novoVertice(v1, cor, peso, descricao)){
                            System.out.println("Vertice inserido com sucesso!");
                            System.out.println(client.imprimeTodosVertices());
                        } else {
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 2:
                        System.out.println("\n\nAdicionar Aresta:");
                        System.out.println("Entre com um inteiro para o Nome do vertice V1:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com um inteiro para o Nome do vertice V2:");
                        v2 = scan.nextInt();
                        System.out.println("Entre com o peso da Aresta:");
                        peso = scan.nextDouble();
                        System.out.println("Entre com 1 para direcionado e 0 para não-direcionado: ");
                        cor = scan.nextInt();
                        System.out.println("Escreva uma descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        
                        if (client.novaAresta(v1, v2, peso, cor, descricao)){
                            System.out.println("Aresta inserida com sucesso!");
                            System.out.println(client.imprimeTodosArestas());
                        } else {
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 3:
                        System.out.println("\n\nRemover Vertice:");
                        System.out.println("Entre com o inteiro do Nome do vertice:");
                        v1 = scan.nextInt();
                        if (client.removeVertice(v1)){
                            System.out.println("Vértice Removido com sucesso!");
                            System.out.println(client.imprimeGrafo());
                        } else {
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 4:
                        System.out.println("\n\nRemover Arestas:");
                        System.out.println("Entre com o inteiro do Nome do vertice1:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com o inteiro do Nome do vertice2:");
                        v2 = scan.nextInt();
                        if (client.removeAresta(v1,v2)){
                            System.out.println("Arestas Removido com sucesso!");
                            System.out.println(client.imprimeGrafo());
                        } else {
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 5:
                        System.out.println("\n\nAtualizar Vertice:");
                        System.out.println("Entre com o inteiro do Nome do vertice a ser atualizado:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com um inteiro para nova Cor: ");
                        cor = scan.nextInt();
                        System.out.println("Escreva a nova descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        System.out.println("Entre com o peso do novo vertice:");
                        peso = scan.nextDouble();
                        Vertice vertice = new Vertice();
                        vertice.setNome(v1);
                        vertice.setCor(cor);
                        vertice.setPeso(peso);
                        vertice.setDescricao(descricao);
                        if (client.updateVertice(vertice,v1)){
                            System.out.println("Vertice atualizado com sucesso!");
                            System.out.println(client.imprimeTodosVertices());
                        } else {
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 6:
                        System.out.println("\n\nAtualizar Aresta:");
                        System.out.println("Entre com o inteiro do Nome do vertice 1:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com o inteiro do Nome do vertice 2:");
                        v2 = scan.nextInt();
                        System.out.println("Entre com o novo peso da Aresta:");
                        peso = scan.nextDouble();
                        System.out.println("Entre com 1 para direcionado e 0 para não-direcionado: ");
                        cor = scan.nextInt();
                        System.out.println("Escreva a nova descrição: ");
                        scan.nextLine();
                        descricao = scan.nextLine();
                        
                        Aresta aresta = new Aresta();
                        aresta.setV1(v1);
                        aresta.setV2(v2);
                        aresta.setPeso(peso);
                        aresta.setFlag(cor);
                        aresta.setDescricao(descricao);
                        
                        if (client.updateAresta(aresta, v1, v2)){
                            System.out.println("Aresta atualizada com sucesso!");
                            System.out.println(client.imprimeTodosArestas());
                        }else{
                            System.out.println("Erro inesperado!");
                        }
                        break;
                    case 7:
                        System.out.println("\n\nImprimir Grafo:");
                        System.out.println(client.imprimeGrafo());
                        break;
                    case 8:
                        System.out.println("\n\nImprimir Vertices");
                        System.out.println(client.imprimeTodosVertices());
                        break;
                    case 9:
                        System.out.println("\n\nImprimir Arestas");
                        System.out.println(client.imprimeTodosArestas());
                        break;
                    case 10:
                        System.out.println("\n\nImprimir Arestas de um Vertice:");
                        System.out.println("Entre com o inteiro do Nome do vertice 1:");
                        v1 = scan.nextInt();
                        System.out.println(client.imprimeTodosArestaVertice(v1));
                        break;
                    case 11:
                        System.out.println("\n\nImprimir Vertices de uma Aresta:");
                        System.out.println("Entre com o inteiro do Nome do vertice 1 da aresta:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com o inteiro do Nome do vertice 2 da aresta:");
                        v2 = scan.nextInt();
                        System.out.println(client.imprimeVerticeAresta(v1,v2));
                        break;
                    case 12:
                        System.out.println("\n\nImprimir vizinhos de um Vertice:");
                        System.out.println("Entre com o inteiro do Nome do vertice 1:");
                        v1 = scan.nextInt();
                        System.out.println(client.imprimeTodosVizinhos(v1));
                        break;
                    case 13:
                        System.out.println("\n\nPegar um Vertice:");
                        System.out.println("Entre com o inteiro do Nome do vertice :");
                        v1 = scan.nextInt();
                        Vertice vert = client.retornaVertice(v1);
                        if(vert != null)
                            System.out.println(" Nome: " + vert.getNome() + " Cor: " + vert.getCor() + " Peso: " + vert.getPeso() + " Descricao: " + vert.getDescricao() + "\n");
                        break;
                    case 14:
                        System.out.println("\n\nPegar uma Aresta:");
                        System.out.println("Entre com o inteiro do Nome do vertice 1 da aresta:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com o inteiro do Nome do vertice 2 da aresta:");
                        v2 = scan.nextInt();
                        Aresta a = client.retornaAresta(v1,v2);
                        if(a != null)
                            System.out.println(" V1: " + a.getV1() + " V2: " + a.getV2() + " Flag: " + a.getFlag() + " Peso: " + a.getPeso() + " Descricao: " + a.getDescricao() + "\n");
                        break;
                    case 15:
                        System.out.println("Entre com o inteiro do Nome do vertice inicial:");
                        v1 = scan.nextInt();
                        System.out.println("Entre com o inteiro do Nome do vertice final:");
                        v2 = scan.nextInt();
                        System.out.println(client.menorCaminho(v1,v2));
                        break;
                    case 16:
                        System.out.println("Desconectando...");
                        escolher = false;
                        return;
                    default:
                        System.out.println("Escolha uma opção válida!");
                }
            }
            
            transport.close();
            
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
