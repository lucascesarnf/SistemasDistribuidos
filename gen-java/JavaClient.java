
import grafo.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
      
      public static void main(String [] args) {
          try {
              TTransport transport = new TSocket("localhost", 9090);
              
              transport.open();
              
              TProtocol protocol = new  TBinaryProtocol(transport);
              Operacoes.Client client = new Operacoes.Client(protocol);
              
              System.out.println(client.verGrafo());
              //System.out.println(client.listarVertices());
              System.out.println(client.listarArestas());
              Vertice v = client.getVertice(1);
              //Cria Vertice :
              int nome = 1;
              int cor = 1;
              double peso = 1.0;
              String desc = "Vertice 1";
              
              

              int nome2 = 2;
              int cor2 = 2;
              double peso2 = 2.0;
              String desc2 = "Vertice 2";

              int nome3 = 3;
              int cor3 = 3;
              double peso3 = 3.0;
              String desc3 = "Vertice 3";

              int nome4 = 4;
              int cor4 = 4;
              double peso4 = 4.0;
              String desc4 = "Vertice 4";
              
              if (client.criarVertice(nome,cor,peso,desc)){
                  System.out.println("\n\nTeste 1:\n");
                  System.out.println(client.listarVertices());

                  if (client.criarVertice(nome2,cor2,peso2,desc2)){
                      System.out.println("\n\nTeste 2:\n");
                      System.out.println(client.listarVertices());

                      if (client.criarVertice(nome3,cor3,peso3,desc3)){
                          System.out.println("\n\nTeste 3:\n");
                          System.out.println(client.listarVertices());

                      }
                  }
              }

              System.out.println("Descrição do vertice: " + v.desc);
         
              transport.close();
          } catch (TException x) {
              x.printStackTrace();
          }
      }
      
      
  }
    /*
 perform(client);
  private static void perform(Calculator.Client client) throws TException
  {
    client.ping();
    System.out.println("ping()");

    int sum = client.add(1,1);
    System.out.println("1+1=" + sum);

    Work work = new Work();

    work.op = Operation.DIVIDE;
    work.num1 = 1;
    work.num2 = 0;
    try {
      int quotient = client.calculate(1, work);
      System.out.println("Whoa we can divide by 0");
    } catch (InvalidOperation io) {
      System.out.println("Invalid operation: " + io.why);
    }

    work.op = Operation.SUBTRACT;
    work.num1 = 15;
    work.num2 = 10;
    try {
      int diff = client.calculate(1, work);
      System.out.println("15-10=" + diff);
    } catch (InvalidOperation io) {
      System.out.println("Invalid operation: " + io.why);
    }

    SharedStruct log = client.getStruct(1);
    System.out.println("Check log: " + log.value);
  }
}
*/
