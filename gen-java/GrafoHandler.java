//package grafo;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

//###############################

import java.util.HashMap;
import Grafo.*;
import java.util.ArrayList;
import java.security.MessageDigest;

import java.util.*;

import static java.lang.Math.abs;

public class GrafoHandler implements Operacoes.Iface{
    
    private int[] clients = {9090, 8129, 9092, 8978, 9056};
    
    public int selfPorta;
    
    private Grafo grafo = new Grafo(new ArrayList<Vertice>(), new ArrayList<Aresta>());
    
    //Adicionar Vertice a Lista Grafo.Vertices[]
    @Override
    public boolean novoVertice(int nome,int cor, double peso, String descricao)
    {
        System.out.println("############## Adicionar Vertice #################");
        int index = findResponsible(nome);
        int porta = clients[index];
        System.out.println("Eu sou o ["+selfPorta+"] e o responsável é["+index+"]:"+porta);
        
        if(selfPorta != porta){
            System.out.println("Eu NÃO SOU o responsável");
            
            TTransport transport = new TSocket("localHost",porta);
            try{
                transport.open();
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                System.out.println("Passei pra frente");
                System.out.println(imprimeVertices());
                System.out.println("Agora é esperar...");
                boolean retorno = client.novoVertice(nome, cor, peso, descricao);
                transport.close();
                System.out.println("#############################################");
                return retorno;
            }catch (Exception x) {
                x.printStackTrace();
            }
        }else{
            System.out.println("Eu SOU o responsável");
            synchronized(grafo.getVertices()) {
                
                if (grafo.getVertices() != null)
                {
                    for(Vertice aux : grafo.getVertices())
                    {
                        if(aux.getNome() == nome)
                        {
                            System.out.println("Vertice já existente");
                            System.out.println("#############################################");
                            return false;
                        }
                    }
                }
                
                Vertice vertice = new Vertice();
                vertice.setNome(nome);
                vertice.setCor(cor);
                vertice.setPeso(peso);
                vertice.setDescricao(descricao);
                grafo.addToVertices(vertice);
            }
            System.out.println("Deu bom\n");
            System.out.println(imprimeVertices());
            System.out.println("#############################################");
            return true;
        }
        System.out.println("Deu ruim");
        System.out.println("#############################################");
        return false;
    }
    
    //Adicionar Aresta a Lista Grafo.Arestas[].
    @Override
    public boolean novaAresta(int v1,int v2, double peso, int flag, String descricao)
    {
        System.out.println("############## Adicionar Aresta #################");
        if(v1 == v2){
            System.out.println("#############################################");
            return false;
        }
        
        int index = findResponsible(v1+v2);
        int porta = clients[index];
        System.out.println("Eu sou o ["+selfPorta+"] e o responsável é["+index+"]:"+porta);
        
        if(selfPorta != porta){
            System.out.println("Eu NÃO SOU o responsável");
            
            TTransport transport = new TSocket("localHost",porta);
            try{
                transport.open();
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                System.out.println("Passei pra frente");
                System.out.println(imprimeArestas());
                System.out.println("Agora é esperar...");
                boolean retorno = client.novaAresta(v1,v2,peso,flag,descricao);
                System.out.println(imprimeArestas());
                transport.close();
                System.out.println("#############################################");
                return retorno;
            }catch (Exception x) {
                x.printStackTrace();
            }
        }else{
            System.out.println("Eu SOU o responsável");
            
            Vertice existeV1 = null;
            Vertice existeV2 = null;
            
            // ----- V1 ------
            int index1 = findResponsible(v1);
            int porta1 = clients[index1];
            System.out.println("Eu sou o ["+selfPorta+"] e o responsável por V1 é["+index1+"]:"+porta1);
            if(selfPorta != porta1){
                System.out.println("Eu NÃO SOU o responsável por V1");
                
                TTransport transport = new TSocket("localHost",porta1);
                try{
                    transport.open();
                    TProtocol protocol = new  TBinaryProtocol(transport);
                    Operacoes.Client client = new Operacoes.Client(protocol);
                    System.out.println("Passei pra frente");
                    System.out.println(imprimeArestas());
                    existeV1 = client.retornaVertice(v1);
                    System.out.println("Chegou pra mim: "+ existeV1);
                    transport.close();
                }catch (Exception x) {
                    x.printStackTrace();
                }
            }else{
                existeV1 = retornaVertice(v1);
            }
            // ----- V2 ------
            int index2 = findResponsible(v2);
            int porta2 = clients[index2];
            System.out.println("Eu sou o ["+selfPorta+"] e o responsável por V2 é["+index2+"]:"+porta2);
            if(selfPorta != porta2){
                System.out.println("Eu NÃO SOU o responsável por V2");
                
                TTransport transport = new TSocket("localHost",porta2);
                try{
                    transport.open();
                    TProtocol protocol = new  TBinaryProtocol(transport);
                    Operacoes.Client client = new Operacoes.Client(protocol);
                    System.out.println("Passei pra frente");
                    System.out.println(imprimeArestas());
                    existeV2 = client.retornaVertice(v2);
                    System.out.println("Chegou pra mim: "+ existeV2);
                    transport.close();
                }catch (Exception x) {
                    x.printStackTrace();
                }
            }else{
                existeV2 = retornaVertice(v2);
            }
            // verificando se tanto o v1 quanto v2 existem para poder inserir a aresta
            if(existeV2 == null || existeV1 == null){
                System.out.println("#############################################");
                return false;
                
            }
            
            Aresta aresta = new Aresta();
            aresta.setV1(v1);
            aresta.setV2(v2);
            aresta.setPeso(peso);
            aresta.setFlag(flag);
            aresta.setDescricao(descricao);
            
            grafo.addToArestas(aresta);
            System.out.println("Eu sou o ["+selfPorta+"] adicionei a aresta:["+v1+"]["+v2+"]");
            System.out.println(imprimeArestas());
            System.out.println("#############################################");
            return true;
        }
        System.out.println("#############################################");
        return false;
    }
    
    @Override
    public boolean removeVertice(int nome)
    {
        System.out.println("############## Remove Vertice #################");
        synchronized(grafo.getVertices()){
            if (grafo.getVertices() != null)
            {
                for(Vertice aux : grafo.getVertices())
                {
                    if(aux.getNome() == nome)
                    {
                        grafo.getVertices().remove(aux);
                        //Remover arestas relacionadas ao vertice caso exista
                        synchronized(grafo.getArestas()){
                            if (grafo.getArestas() != null)
                            {
                                for(Aresta aux2 : grafo.getArestas())
                                {
                                    if(aux2.getV1() == nome || aux2.getV2() == nome)
                                    {
                                        grafo.getArestas().remove(aux2);
                                        System.out.println("#############################################");
                                        return true;
                                    }
                                }
                            }
                        }
                        System.out.println("#############################################");
                        return true;
                    }
                }
            }
        }
        System.out.println("#############################################");
        return false;
    }
    
    @Override
    public boolean removeAresta(int v1,int v2)
    {
        System.out.println("############## Remove Aresta #################");
        int index = findResponsible(v1+v2);
        int porta = clients[index];
        System.out.println("Eu sou o ["+selfPorta+"] e o responsável é["+index+"]:"+porta);
        
        if(selfPorta != porta){
            System.out.println("Eu NÃO SOU o responsável");
            TTransport transport = new TSocket("localHost",porta);
            try{
                transport.open();
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                System.out.println("Passei pra frente");
                System.out.println(imprimeArestas());
                System.out.println("Agora é esperar...");
                boolean retorno = client.removeAresta(v1,v2);
                transport.close();
                System.out.println("#############################################");
                return retorno;
            }catch (Exception x) {
                x.printStackTrace();
            }
        }else{
            System.out.println("Eu SOU o responsável");
            synchronized(grafo.getArestas()){
                
                if (grafo.getArestas() != null)
                {
                    for(Aresta aux : grafo.getArestas())
                    {
                        if((aux.getV1() == v1 && aux.getV2() == v2) || (aux.getV1() == v2 && aux.getV2() == v1))
                        {
                            grafo.getArestas().remove(aux);
                            System.out.println("#############################################");
                            return true;
                        }
                    }
                }
                System.out.println(imprimeArestas());
            }
        }
        System.out.println("#############################################");
        return false;
        
    }
    
    @Override
    public boolean updateVertice(Vertice v,int nome)
    {
        System.out.println("############## Update Vertice #################");
        int index = findResponsible(nome);
        int porta = clients[index];
        System.out.println("Eu sou o ["+selfPorta+"] e o responsável é["+index+"]:"+porta);
        
        if(selfPorta != porta){
            System.out.println("Eu NÃO SOU o responsável");
            
            TTransport transport = new TSocket("localHost",porta);
            try{
                transport.open();
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                System.out.println("Passei pra frente");
                System.out.println(imprimeVertices());
                System.out.println("Agora é esperar...");
                boolean retorno = client.updateVertice(v,nome);
                transport.close();
                System.out.println("#############################################");
                return retorno;
            }catch (Exception x) {
                x.printStackTrace();
            }
        }else{
            System.out.println("Eu SOU o responsável");
            int i = 0;
            synchronized(grafo.getVertices()){
                if (grafo.getVertices() != null)
                {
                    for(Vertice aux : grafo.getVertices())
                    {
                        if(aux.getNome() == nome)
                        {
                            grafo.getVertices().get(i).setPeso(v.getPeso());
                            grafo.getVertices().get(i).setCor(v.getCor());
                            grafo.getVertices().get(i).setDescricao(v.getDescricao());
                            System.out.println(imprimeVertices());
                            System.out.println("#############################################");
                            return true;
                        }
                        i++;
                    }
                }
                System.out.println(imprimeVertices());
            }
        }
        System.out.println("#############################################");
        return false;
    }
    
    
    @Override
    public boolean updateAresta(Aresta a,int v1,int v2)
    {
        System.out.println("############## Update Aresta #################");
        int index = findResponsible(v1+v2);
        int porta = clients[index];
        System.out.println("Eu sou o ["+selfPorta+"] e o responsável é["+index+"]:"+porta);
        
        if(selfPorta != porta){
            System.out.println("Eu NÃO SOU o responsável");
            TTransport transport = new TSocket("localHost",porta);
            try{
                transport.open();
                TProtocol protocol = new  TBinaryProtocol(transport);
                Operacoes.Client client = new Operacoes.Client(protocol);
                System.out.println("Passei pra frente");
                System.out.println(imprimeArestas());
                System.out.println("Agora é esperar...");
                boolean retorno = client.updateAresta(a,v1,v2);
                transport.close();
                System.out.println("#############################################");
                return retorno;
            }catch (Exception x) {
                x.printStackTrace();
            }
        }else{
            System.out.println("Eu SOU o responsável");
            int i = 0;
            synchronized(grafo.getArestas()){
                
                if (grafo.getArestas() != null)
                {
                    for(Aresta aux : grafo.getArestas())
                    {
                        if(aux.getV1() == v1 && aux.getV2() == v2)
                        {
                            grafo.getArestas().get(i).setPeso(a.getPeso());
                            grafo.getArestas().get(i).setFlag(a.getFlag());
                            grafo.getArestas().get(i).setDescricao(a.getDescricao());
                            System.out.println(imprimeVertices());
                            System.out.println("#############################################");
                            return true;
                        }
                        i++;
                    }
                }
                System.out.println(imprimeArestas());
            }
        }
        System.out.println("#############################################");
        return false;
    }
    
    
    //retorna string para view imprimir. Utiliza impressão de vertices e Arestas ja criada
    @Override
    public String imprimeGrafo()
    {
        
        String resultado = "GRAFO:\n";
        synchronized(grafo){
            resultado = imprimeVertices() + imprimeArestas();
        }
        return resultado;
    }
    
    //retorna string para view imprimir. Retorna string já com uma linha por Vertice
    @Override
    public String imprimeVertices()
    {
        String resultado = "";
        synchronized(grafo.getVertices()){
            if (grafo.getVertices() != null)
            {
                resultado = "VERTICES:\n";
                for(Vertice aux : grafo.getVertices())
                {
                    resultado += " Nome: " + aux.getNome() + " Cor: " + aux.getCor() + " Peso: " + aux.getPeso() + " Descricao: " + aux.getDescricao() + "\n";
                }
            }
        }
        return resultado;
    }
    
    //retorna string para view imprimir. Retorna string já com uma linha por Vertice
    @Override
    public String imprimeArestas()
    {
        String resultado = "";
        synchronized(grafo.getArestas()){
            if (grafo.getArestas() != null)
            {
                resultado = "ARESTAS:\n";
                for(Aresta aux : grafo.getArestas())
                {
                    resultado += " V1: " + aux.getV1() + " V2: " + aux.getV2() + " Flag: " + aux.getFlag() + " Peso: " + aux.getPeso() + " Descricao: " + aux.getDescricao() + "\n";
                }
            }
        }
        return resultado;
    }
    
    @Override
    public String imprimeArestaVertice(int nome)
    {
        String resultado = "";
        synchronized(grafo.getArestas()){
            
            if (grafo.getArestas() != null)
            {
                resultado = "ARESTAS DO VERTICE:" + nome + "\n";
                for(Aresta aux : grafo.getArestas())
                {
                    if(aux.getV1() == nome || aux.getV2() == nome)
                        resultado += " V1: " + aux.getV1() + " V2: " + aux.getV2() + " Flag: " + aux.getFlag() + " Peso: " + aux.getPeso() + " Descricao: " + aux.getDescricao() + "\n";
                }
            }
        }
        return resultado;
    }
    
    @Override
    public String imprimeVerticeAresta(int v1, int v2)
    {
        String resultado = "";
        synchronized(grafo.getVertices()){
            if (grafo.getVertices() != null)
            {
                resultado = "VERTICES DA ARESTA:(" + v1 + "," + v2 + ")\n";
                for(Vertice aux : grafo.getVertices())
                {
                    if(aux.getNome() == v1 || aux.getNome() == v2)
                        resultado += " Nome: " + aux.getNome() + " Cor: " + aux.getCor() + " Peso: " + aux.getPeso() + " Descricao: " + aux.getDescricao() + "\n";
                }
            }
        }
        return resultado;
        
    }
    
    @Override
    public String imprimeVizinhos(int nome)
    {
        Vertice aux1;
        String resultado = "";
        synchronized(grafo.getArestas()){
            if (grafo.getArestas() != null)
            {
                resultado = "VIZINHOS DO VERTICE:" + nome + "\n";
                for(Aresta aux :  grafo.getArestas())
                {
                    if(aux.getV1() == nome)
                    {
                        aux1 = retornaVertice(aux.getV2());
                        resultado += " Nome: " + aux1.getNome() + " Cor: " + aux1.getCor() + " Peso: " + aux1.getPeso() + " Descricaoo: " + aux1.getDescricao() + "\n";
                    }
                    // para o vertice 2 necessário verificar se a aresta é orientada (flag = 1), pois caso seja v1 nao é vizinho de v2
                    if(aux.getV2() == nome && aux.getFlag() == 0)
                    {
                        aux1 = retornaVertice(aux.getV1());
                        resultado += " Nome: " + aux1.getNome() + " Cor: " + aux1.getCor() + " Peso: " + aux1.getPeso() + " Descricao: " + aux1.getDescricao() + "\n";
                    }
                    
                }
            }
        }
        return resultado;
    }
    
    //retorna Vertice conforme nome
    @Override
    public Vertice retornaVertice(int nome){
        synchronized(grafo.getVertices()){
            if (grafo.getVertices() != null)
            {
                for(Vertice aux : grafo.getVertices())
                {
                    if(aux.getNome() == nome)
                    {
                        return aux;
                    }
                    
                }
            }
        }
        return null;
    }
    
    //retorna Aresta conforme vertices
    @Override
    public Aresta retornaAresta(int v1,int v2){
        synchronized(grafo.getArestas()){
            if (grafo.getArestas() != null)
            {
                for(Aresta aux : grafo.getArestas())
                {
                    if((aux.getV1() == v1) && (aux.getV2() == v2))
                    {
                        return aux;
                    }
                    
                }
            }
        }
        return null;
    }
    
    private int findResponsible(int i) {
        System.out.println("********** Encontrar Responsável **********");
        byte[] theDigest = null;
        System.out.println("Encontrar responsável para:"+ i);
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            theDigest = md.digest( Integer.toString(i).getBytes("UTF-8") );
        }catch(Exception e){
            e.printStackTrace();
        }
        int responsavel = abs(theDigest[theDigest.length-1] % this.clients.length);
        
        System.out.println("O responsável é:"+ responsavel);
        
        System.out.println("********************************************");
        return responsavel;
    }
    
}
