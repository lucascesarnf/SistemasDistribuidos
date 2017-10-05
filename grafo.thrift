
include "shared.thrift"

namespace java grafo

typedef i32 MyInteger

const i32 INT32CONSTANT = 9853

const map<string,string> MAPCONSTANT = {'hello':'world', 'goodnight':'moon'}

enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}
enum VerticeOP {
    CREATE = 1,
    READ = 2,
    UPDATE = 3,
    DELETE = 4,
    LIST = 5
}
enum ArestaOP {
    CREATE = 1,
    READ = 2,
    UPDATE = 3,
    DELETE = 4,
    LIST = 5
}
struct Work {
  1: i32 num1 = 0,
  2: i32 num2,
  3: Operation op,
  4: optional string comment,
}
exception InvalidOperation {
  1: i32 whatOp,
  2: string why
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

service Operacoes extends shared.SharedService {

    void ping(),

    bool operacaoVertice(1:i32 logid, 2:Vertice v) throws (1:InvalidOperation ouch),

    bool operacaoAresta(1:i32 logid, 2:Aresta a) throws (1:InvalidOperation ouch),

    oneway void zip()

}

service Calculator extends shared.SharedService {

   void ping(),

   i32 add(1:i32 num1, 2:i32 num2),

   i32 calculate(1:i32 logid, 2:Work w) throws (1:InvalidOperation ouch),

   oneway void zip()

}
