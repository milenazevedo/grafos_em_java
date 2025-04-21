import java.util.List;

public class Grafo {
    private boolean isDirecionado;
    private List<Vertice> vertices;
    private List<Aresta> arestas;
    private int ordem;
    private int tamanho;

    public Grafo(boolean isDirecionado) {
        this.isDirecionado = isDirecionado;
    }

    public Grafo() {

    }

    public boolean isDirecionado() {
        return isDirecionado;
    }

    public void setDirecionado(boolean direcionado) {
        isDirecionado = direcionado;
    }

    public List<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
        this.ordem = vertices.size();
    }

    public List<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
        this.tamanho = arestas.size();
        if (!isDirecionado) {
            verificaDirecionado();
        }
        preencherAdjacencias();
    }

    public int getOrdem() {
        return ordem;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void preencherAdjacencias() {
        for (Aresta aresta : arestas) {
            Vertice origem = aresta.getOrigem();
            Vertice destino = aresta.getDestino();
            avaliarGrausDosVertices(origem, destino);

            destino.addAdjacencia(origem);
            if (!isDirecionado) {
                origem.addAdjacencia(destino);
            }
        }
    }

    private void avaliarGrausDosVertices(Vertice origem, Vertice destino) {
        if (isDirecionado) {
            origem.incrementaOutDegree();
            origem.incrementaInDegree();
        }
        destino.incrementaGrau();
        origem.incrementaGrau();
    }

    public void verificaDirecionado() {
        for (int i = 0; i < arestas.size(); i++) {
            if (isSelfLoop(arestas.get(i))) {
                defineComoDirecionado();
                return;
            }
            for (int j = 0; j < arestas.size(); j++) {
                if (naoEaMesmaAresta(i, j)) {
                    if (isViaMaoDupla(arestas.get(i), arestas.get(j))) {
                        defineComoDirecionado();
                    } else if (isMesmoSentido(arestas.get(i), arestas.get(j))) {
                        defineComoDirecionado();
                        return;
                    }
                }
            }
        }
    }

    private static boolean naoEaMesmaAresta(int i, int j) {
        return i != j;
    }

    private boolean isMesmoSentido(Aresta i, Aresta j) {
        return i.getOrigem() == j.getOrigem() && i.getDestino() == j.getDestino();
    }

    private void defineComoDirecionado() {
        isDirecionado = true;
    }

    private static boolean isViaMaoDupla(Aresta arestaAlvo, Aresta arestaInterna) {
        return arestaAlvo.getOrigem() == arestaInterna.getDestino() && arestaAlvo.getDestino() == arestaInterna.getOrigem();
    }

    private static boolean isSelfLoop(Aresta arestaAlvo) {
        return arestaAlvo.getOrigem() == arestaAlvo.getDestino();
    }

    @Override
    public String toString() {
        return "\nisDirecionado: " + isDirecionado + "\nvertices: " + vertices + "\narestas: " + arestas + "\nordem:" + ordem + "\ntamanho: " + tamanho;
    }

    public void exibirMatrizAdjacencia() {
        int n = vertices.size();
        int[][] matriz = new int[n][n];

        for (Aresta aresta : arestas) {
            int i = vertices.indexOf(aresta.getOrigem());
            int j = vertices.indexOf(aresta.getDestino());
            matriz[i][j] = 1;

            if (!isDirecionado) {
                matriz[j][i] = 1;
            }
        }

        System.out.println("Matriz de Adjacência:");
        System.out.println("     ");
        for (Vertice v : vertices) {
            System.out.print(v.getNome() + " ");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.print(vertices.get(i).getNome() + " | ");
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void exibirMatrizIncidencia() {
        int numVertices = vertices.size();
        int numArestas = arestas.size();
        int[][] matriz = new int[numVertices][numArestas];

        for (int j = 0; j < numArestas; j++) {
            Aresta aresta = arestas.get(j);
            int origemIndex = vertices.indexOf(aresta.getOrigem());
            int destinoIndex = vertices.indexOf(aresta.getDestino());

            if (isDirecionado) {
                matriz[origemIndex][j] = -1;
                matriz[destinoIndex][j] = 1;
            } else {
                matriz[origemIndex][j] = 1;
                matriz[destinoIndex][j] = 1;
            }
        }

        System.out.println("Matriz de Incidência:");
        System.out.print("    ");
        for (int j = 0; j < numArestas; j++) {
            String nomeAresta = arestas.get(j).getNome();
            System.out.print((nomeAresta != null ? nomeAresta : "e" + (j + 1)) + " ");
        }
        System.out.println();

        for (int i = 0; i < numVertices; i++) {
            System.out.print(vertices.get(i).getNome() + " | ");
            for (int j = 0; j < numArestas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }
}
