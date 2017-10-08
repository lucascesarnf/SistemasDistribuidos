//package grafo;

import org.apache.thrift.TException;
import java.util.HashMap;
import Grafo.*;
import java.util.ArrayList;

public class GrafoHandler implements Operacoes.Iface{
    
    private Grafo grafo = new Grafo(new ArrayList<Vertice>(), new ArrayList<Aresta>());
    
    //Adicionar Vertice a Lista Grafo.Vertices[]
    @Override
    public boolean novoVertice(int nome,int cor, double peso, String descricao)
    {
        
        synchronized(grafo.getVertices()) {
            
            if (grafo.getVertices() != null)
            {
                for(Vertice aux : grafo.getVertices())
                {
                    if(aux.getNome() == nome)
                    {
                        System.out.println("Vertice já existente");
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
        return true;
    }
    
    //Adicionar Aresta a Lista Grafo.Arestas[].
    @Override
    public boolean novaAresta(int v1,int v2, double peso, int flag, String descricao)
    {
        boolean existeVertice1 = false;
        boolean existeVertice2 = false;
        
        synchronized( grafo.getArestas()){
            if ((grafo.getVertices() != null) && (v1 != v2))
            {
                synchronized(grafo.getVertices()){
                    for(Vertice aux : grafo.getVertices())
                    {
                        if(aux.getNome() == v1)
                        {
                            existeVertice1 = true;
                        }
                        if(aux.getNome() == v2)
                        {
                            existeVertice2 = true;
                        }
                        if(existeVertice1 && existeVertice2)
                            break;
                        
                    }
                }
            }
            // verificando se tanto o v1 quanto v2 existem para poder inserir a aresta
            if(!existeVertice1 || !existeVertice2)
                return false;
            
            Aresta aresta = new Aresta();
            aresta.setV1(v1);
            aresta.setV2(v2);
            aresta.setPeso(peso);
            aresta.setFlag(flag);
            aresta.setDescricao(descricao);
            
            grafo.addToArestas(aresta);
            
        }
        return true;
    }
    
    @Override
    public boolean removeVertice(int nome)
    {
        
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
                                        return true;
                                    }
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean removeAresta(int v1,int v2)
    {
        synchronized(grafo.getArestas()){
            
            if (grafo.getArestas() != null)
            {
                for(Aresta aux : grafo.getArestas())
                {
                    if((aux.getV1() == v1 && aux.getV2() == v2) || (aux.getV1() == v2 && aux.getV2() == v1))
                    {
                        grafo.getArestas().remove(aux);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean updateVertice(Vertice v,int nome)
    {
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
                        return true;
                    }
                    i++;
                }
            }
        }
        return false;
    }
    
    
    @Override
    public boolean updateAresta(Aresta a,int v1,int v2)
    {
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
                        return true;
                    }
                    i++;
                }
            }
        }
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
        return new Vertice();
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
        return new Aresta();
    }
}
