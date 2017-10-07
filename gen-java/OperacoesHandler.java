import org.apache.thrift.TException;
import grafo.*;

public class OperacoesHandler implements Operacoes.Iface{
    
    private Grafo grafo = new Grafo();
    
    public boolean criarVertice(int nome, int cor, double peso,String desc) throws org.apache.thrift.TException{
        System.out.println("criar Vertice");
        Vertice v = new Vertice();
        v.nome = nome;
        v.cor = cor;
        v.peso = peso;
        v.desc = desc;
        grafo.addToV(v);
        return true;
    }
    
    public boolean criarAresta(int v1, int v2, double peso, int flag, String desc) throws org.apache.thrift.TException{
        System.out.println("criar Aresta");
        Aresta a = new Aresta();
        return true;
    }
    
    public boolean removerVertice(int nome) throws org.apache.thrift.TException{
        System.out.println("remove Vertice");
        
        return true;
    }
    
    public boolean removerAresta(int v1, int v2) throws org.apache.thrift.TException{
        System.out.println("remove Aresta");
        
        return true;
    }
    
    public boolean updateVertice(Vertice v, int nome) throws org.apache.thrift.TException{
        System.out.println("update Vertice");
        
        return true;
    }
    
    public boolean updateAresta(Aresta a, int v1, int v2) throws org.apache.thrift.TException{
        System.out.println("update Aresta");
        
        return true;
    }
    
    public String verGrafo() throws org.apache.thrift.TException{
        String string = "vi um grafo";
        System.out.println("ver Grafo");
        return string;
    }
    
    public String listarVertices() throws org.apache.thrift.TException{
        String string = "";
        for (int i = 0; i < grafo.getVSize(); i++){
            Vertice v = grafo.getVIntoI(i);
            String vert = "vertice["+v.nome+"](Descricao:"+v.desc+")";
            System.out.println(vert);
            string = string + "\n" +vert;
        }
        return string;
    }
    
    public String listarArestas() throws org.apache.thrift.TException{
        String string = "vi uma Aresta";
        System.out.println("listar Arestas");
        return string;
    }
    
    public String listarArestasDoVertice(int name) throws org.apache.thrift.TException{
        String string = "vi uma Aresta de um Vertice";
        System.out.println("listar Arestas do Vertice");
        return string;
    }
    
    public String listarVerticesDaAresta(int v1, int v2) throws org.apache.thrift.TException{
        String string = "vi um Vertice de uma Aresta";
        System.out.println("listar Vertices das Arestas");
        return string;
    }
    
    public String listarVisinhosDoVertice(int name) throws org.apache.thrift.TException{
        String string = "vi um visinho";
        System.out.println("listar visinhos");
        return string;
    }
    
    public Vertice getVertice(int name) throws VerticeNaoEncontrado, org.apache.thrift.TException{
        System.out.println("pega Vertices:" + name);
        Vertice v = new Vertice();
        v.desc = "Vertice Teste";
        return v;
    }
    
    public Aresta getAresta(int v1, int v2) throws ArestaNaoEncontrada, org.apache.thrift.TException{
        System.out.println("pega Aresta:" + v1 + v2);
        Aresta a = new Aresta();
        return a;
    }
    
}
