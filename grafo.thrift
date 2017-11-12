namespace java Grafo

exception ArestaNotFound
{

}
exception VerticeNotFound
{

}

struct Vertice
{
	1:i32 nome,
	2:i32 cor,
	3:string descricao,
	4:double peso
}
struct Aresta
{
	1:i32 v1,
	2:i32 v2,
	3:double peso,
	4:string descricao,
	5:i32 flag
}
struct Grafo
{
	1:list<Vertice> vertices,
	2:list<Aresta> arestas
}

service Operacoes
{
    bool novoVertice (1:i32 nome,2:i32 cor, 3:double peso, 4:string descricao),
	bool novaAresta(1:i32 v1,2:i32 v2, 3:double peso, 4:i32 flag, 5:string descricao),

    bool removeVertice(1:i32 nome),
	bool removeAresta(1:i32 v1,2:i32 v2),

    bool updateVertice(1:Vertice v,2:i32 nome),
	bool updateAresta(1:Aresta a,2:i32 v1,3:i32 v2),

	string imprimeGrafo (),
	string imprimeVertices(),
	string imprimeArestas(),
	string imprimeTodosVertices(),
	string imprimeTodosArestas(),

	string imprimeArestaVertice(1:i32 nome),
	string imprimeTodosArestaVertice(1:i32 nome),
	string imprimeVerticeAresta(1:i32 v1, 2:i32 v2),
	string imprimeVizinhos(1:i32 nome),
	string imprimeTodosVizinhos(1:i32 nome),

	Vertice retornaVertice(1:i32 nome),
	Aresta  retornaAresta(1:i32 v1, 2:i32 v2)
 }
