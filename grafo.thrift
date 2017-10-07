namespace java grafo


exception ArestaNaoEncontrada
{

}
exception VerticeNaoEncontrado
{

}

struct Vertice
{
    1:i32 nome,
    2:i32 cor,
    3:string desc,
    4:double peso
}

struct Aresta
{
    1:i32 v1,
    2:i32 v2,
    3:double peso,
    4:string desc,
    5:i32 flag
}

struct Grafo
{
    1:list<Vertice> v,
    2:list<Aresta> a
}


service Operacoes {

        bool criarVertice (1:i32 nome,2:i32 cor, 3:double peso, 4:string desc),
        bool criarAresta(1:i32 v1,2:i32 v2, 3:double peso, 4:i32 flag, 5:string desc),

        bool removerVertice(1:i32 nome),
        bool removerAresta(1:i32 v1,2:i32 v2),

        bool updateVertice(1:Vertice v,2:i32 nome),
        bool updateAresta(1:Aresta a,2:i32 v1,3:i32 v2),

        string verGrafo (),

        string listarVertices(),
        string listarArestas(),

        string listarArestasDoVertice(1:i32 name),
        string listarVerticesDaAresta(1:i32 v1, 2:i32 v2),
        string listarVisinhosDoVertice(1:i32 name),

        Vertice getVertice(1:i32 name) throws (1:VerticeNaoEncontrado vne),
        Aresta getAresta(1:i32 v1,2:i32 v2) throws (1:ArestaNaoEncontrada ane)

}
