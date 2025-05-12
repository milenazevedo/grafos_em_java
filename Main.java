import java.util.List;

public class Main {
    public static void main(String[] args) {
        Grafo g = new Grafo();
        Vertice v1 = new Vertice("A");
        Vertice v2 = new Vertice("B");
        Vertice v3 = new Vertice("C");

        // Agora as arestas têm pesos
        Aresta a1 = new Aresta(v2, v1, 2.5);  // Peso 2.5
        Aresta a2 = new Aresta(v1, v3, 4.0);  // Peso 4.0

        g.setVertices(List.of(v1, v2, v3));
        g.setArestas(List.of(a1, a2));

        System.out.println(g);

        // Exibindo as matrizes
        g.imprimirMatrizAdjacencia();
        g.imprimirMatrizIncidencia();

        System.out.println("Existe caminho simples de v2 para v3? " + g.existeCaminhoSimples(v2, v3));
        System.out.println("Existe caminho simples de v3 para v2? " + g.existeCaminhoSimples(v3, v2));

        double comprimento = g.comprimentoDoCaminho(v2, v3);
        System.out.println("Comprimento do caminho de v2 até v3: " + comprimento);

        List<Vertice> caminhoMaisCurto = g.identificarCaminhoMaisCurto(v2, v3);

        System.out.println("Caminho mais curto de B até C: ");
        for (Vertice v : caminhoMaisCurto) {
            System.out.print(v.getNome() + " ");
        }
    }
}
