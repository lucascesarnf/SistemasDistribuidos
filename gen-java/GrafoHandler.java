//package grafo;

import org.apache.thrift.TException;
import java.util.HashMap;
import Grafo.*;

public class GrafoHandler implements Operacoes.Iface{

	private Grafo grafo = new Grafo();

	//Adicionar Vertice a Lista Grafo.Vertices[]
	public boolean novoVertice(int nome,int cor, double peso, String descricao)
	{	
		java.util.List<Vertice> aux1 = grafo.getVertices();
		if (aux1 != null)
		{		
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == nome)
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
		return true;
	}

	//Adicionar Aresta a Lista Grafo.Arestas[].
	public boolean novaAresta(int v1,int v2, double peso, int flag, String descricao)
	{
		boolean existeVertice1 = false;
		boolean existeVertice2 = false;
		java.util.List<Vertice> aux1 = grafo.getVertices();
	        if ((aux1 != null) && (v1 != v2))
		{		
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == v1)
				{
					existeVertice1 = true;			
				}
				if(aux2.getNome() == v2)
				{
					existeVertice2 = true;			
				}
				if(existeVertice1 && existeVertice2)
					break;
				
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
	

		return true;
	}
	
	public boolean removeVertice(int nome)
	{
		java.util.List<Vertice> aux1 = grafo.getVertices();
		if (aux1 != null)
		{		
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == nome)
				{
					aux1.remove(aux2);			
				}
			}
		}
		//Remover arestas relacionadas ao vertice caso exista
		java.util.List<Aresta> aux3 = grafo.getArestas();
		if (aux3 != null)
		{		
			for(Aresta aux4 : aux3)	
			{
				if(aux4.getV1() == nome || aux4.getV2() == nome)
				{
					aux3.remove(aux4);
					return true;			
				}
			}
		}
		return false;

	}
	
	
	public boolean removeAresta(int v1,int v2)
	{	java.util.List<Aresta> aux1 = grafo.getArestas();
		if (aux1 != null)
		{		
			for(Aresta aux2 : aux1)	
			{
				if((aux2.getV1() == v1 && aux2.getV2() == v2) || (aux2.getV1() == v2 && aux2.getV2() == v1))
				{
					aux1.remove(aux2);
					return true;			
				}
			}
		}
		return false;	
	}

	public boolean updateVertice(Vertice v,int nome)
	{
		java.util.List<Vertice> aux1 = grafo.getVertices();
		int i = 0;
	        if (aux1 != null)
		{		
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == nome)
				{
					aux1.get(i).setPeso(v.getPeso());
					aux1.get(i).setCor(v.getCor());
					aux1.get(i).setDescricao(v.getDescricao());
					return true;
				}
				i++;
			}
		}

		return false;
	}

	public boolean updateAresta(Aresta a,int v1,int v2)
	{
		java.util.List<Aresta> aux1 = grafo.getArestas();
		int i = 0;
	        if (aux1 != null)
		{		
			for(Aresta aux2 : aux1)	
			{
				if(aux2.getV1() == v1 && aux2.getV2() == v2)
				{
					aux1.get(i).setPeso(a.getPeso());
					aux1.get(i).setFlag(a.getFlag());
					aux1.get(i).setDescricao(a.getDescricao());
					return true;
				}
				i++;
			}
		}

		return false;	
	}

	
	//retorna string para view imprimir. Utiliza impressão de vertices e Arestas ja criada
	public String imprimeGrafo()
	{
		String resultado = "GRAFO:\n";	
		resultado = imprimeVertices() + imprimeArestas();
		return resultado;
	}

	//retorna string para view imprimir. Retorna string já com uma linha por Vertice
	public String imprimeVertices()
	{
		java.util.List<Vertice> aux1 = grafo.getVertices();
		String resultado = "";
	    if (aux1 != null)
		{	
			resultado = "VERTICES:\n";	
			for(Vertice aux2 : aux1)	
			{
				resultado += " Nome: " + aux2.getNome() + " Cor: " + aux2.getCor() + " Peso: " + aux2.getPeso() + " Descri??o: " + aux2.getDescricao() + "\n";				
			}
		}
		return resultado;
	}
	//retorna string para view imprimir. Retorna string já com uma linha por Vertice
	public String imprimeArestas()
	{
		java.util.List<Aresta> aux1 = grafo.getArestas();
		String resultado = "";
	    if (aux1 != null)
		{	
			resultado = "ARESTAS:\n";	
			for(Aresta aux2 : aux1)	
			{
				resultado += " V1: " + aux2.getV1() + " V2: " + aux2.getV2() + " Flag: " + aux2.getFlag() + " Peso: " + aux2.getPeso() + " Descri??o: " + aux2.getDescricao() + "\n";				
			}
		}
		return resultado;
	}
	
	public String imprimeArestaVertice(int nome)
	{
		java.util.List<Aresta> aux1 = grafo.getArestas();
		String resultado = "";
	    if (aux1 != null)
		{	
			resultado = "ARESTAS DO VERTICE:" + nome + "\n";	
			for(Aresta aux2 : aux1)	
			{
				if(aux2.getV1() == nome || aux2.getV2() == nome)
					resultado += " V1: " + aux2.getV1() + " V2: " + aux2.getV2() + " Flag: " + aux2.getFlag() + " Peso: " + aux2.getPeso() + " Descri??o: " + aux2.getDescricao() + "\n";				
			}
		}
		return resultado;
	}
	public String imprimeVerticeAresta(int v1, int v2)
	{
		java.util.List<Vertice> aux1 = grafo.getVertices();
		String resultado = "";
	    if (aux1 != null)
		{	
			resultado = "VERTICES DA ARESTA:(" + v1 + "," + v2 + ")\n";	
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == v1 || aux2.getNome() == v2)
					resultado += " Nome: " + aux2.getNome() + " Cor: " + aux2.getCor() + " Peso: " + aux2.getPeso() + " Descri??o: " + aux2.getDescricao() + "\n";				
			}
		}
		return resultado;
		
	}
	public String imprimeVizinhos(int nome)
	{
		java.util.List<Aresta> aux1 = grafo.getArestas();
		Vertice aux3;
		String resultado = "";
	    if (aux1 != null)
		{	
			resultado = "VIZINHOS DO VERTICE:" + nome + "\n";	
			for(Aresta aux2 : aux1)	
			{
				if(aux2.getV1() == nome)
				{
					aux3 = retornaVertice(aux2.getV2());
					resultado += " Nome: " + aux3.getNome() + " Cor: " + aux3.getCor() + " Peso: " + aux3.getPeso() + " Descri??o: " + aux3.getDescricao() + "\n";
				}
				// para o vertice 2 necessário verificar se a aresta é orientada (flag = 1), pois caso seja v1 nao é vizinho de v2
				if(aux2.getV2() == nome && aux2.getFlag() == 0)
				{
					aux3 = retornaVertice(aux2.getV1());
					resultado += " Nome: " + aux3.getNome() + " Cor: " + aux3.getCor() + " Peso: " + aux3.getPeso() + " Descri??o: " + aux3.getDescricao() + "\n";
				}
								
			}
		}
		return resultado;
	}

	//retorna Vertice conforme nome	
	public Vertice retornaVertice(int nome){
		java.util.List<Vertice> aux1 = grafo.getVertices();
	        if (aux1 != null)
		{		
			for(Vertice aux2 : aux1)	
			{
				if(aux2.getNome() == nome)
				{
					return aux2;		
				}
				
			}
		}
		return new Vertice();
	}
	
	//retorna Aresta conforme vertices
	public Aresta retornaAresta(int v1,int v2){
		java.util.List<Aresta> aux1 = grafo.getArestas();
	        if (aux1 != null)
		{		
			for(Aresta aux2 : aux1)	
			{
				if((aux2.getV1() == v1) && (aux2.getV2() == v2))
				{
					return aux2;		
				}
				
			}
		}
		return new Aresta();
	}
}
