import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@Setter
@Getter
public class Grafo {
    private boolean isDirecionado;
    private List<Vertice> vertices;
    private List<Aresta> arestas;
    private int ordem;
    private int tamanho;

    public Grafo(boolean isDirecionado) {
        this.isDirecionado = isDirecionado;
    }

    public void setVertices(List<Vertice> vertices) {
        this.vertices = vertices;
        ordem = vertices.size();
    }

    public void setArestas(List<Aresta> arestas) {
        this.arestas = arestas;
        tamanho = arestas.size();
        if (!isDirecionado) {
            verificaDirecionado();
        }
        preencherAdjacencias();
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
            destino.incrementaInDegree();
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
                        return;
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
        return arestaAlvo.getOrigem() == arestaInterna.getDestino()
                && arestaAlvo.getDestino() == arestaInterna.getOrigem();
    }

    private static boolean isSelfLoop(Aresta arestaAlvo) {
        return arestaAlvo.getOrigem() == arestaAlvo.getDestino();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nisDirecionado: ").append(isDirecionado);
        sb.append("\nvertices:\n");
        for (Vertice v : vertices) {
            sb.append("  ").append(v.toString(isDirecionado)).append("\n");
        }
        sb.append("arestas: ").append(arestas);
        sb.append("\nordem: ").append(ordem);
        sb.append("\ntamanho: ").append(tamanho);
        return sb.toString();
    }

    public int[][] matrizAdjacencia() {
        int n = vertices.size();
        int[][] matriz = new int[n][n];

        for (Aresta aresta : arestas) {
            int i = vertices.indexOf(aresta.getOrigem());
            int j = vertices.indexOf(aresta.getDestino());

            if (isDirecionado) {
                matriz[i][j] = (int) aresta.getPeso();  // Preencher com o peso da aresta
            } else {
                matriz[i][j] = (int) aresta.getPeso();  // Para grafos não direcionados
                matriz[j][i] = (int) aresta.getPeso();
            }
        }
        return matriz;
    }

    public void imprimirMatrizAdjacencia() {
        int[][] matriz = matrizAdjacencia();
        System.out.println("Matriz de Adjacência:");
        for (int[] linha : matriz) {
            for (int val : linha) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public int[][] matrizIncidencia() {
        int n = vertices.size();
        int m = arestas.size();
        int[][] matriz = new int[n][m];

        for (int i = 0; i < m; i++) {
            Aresta aresta = arestas.get(i);
            int origemIndex = vertices.indexOf(aresta.getOrigem());
            int destinoIndex = vertices.indexOf(aresta.getDestino());


            if (isDirecionado) {
                matriz[origemIndex][i] = (int) aresta.getPeso();
                matriz[destinoIndex][i] = - (int) aresta.getPeso();
            } else {
                matriz[origemIndex][i] = (int) aresta.getPeso();
                matriz[destinoIndex][i] = (int) aresta.getPeso();
            }
        }
        return matriz;
    }

    public void imprimirMatrizIncidencia() {
        int[][] matriz = matrizIncidencia();
        System.out.println("Matriz de Incidência:");
        for (int[] linha : matriz) {
            for (int val : linha) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    public void dfs(int origem, int destino) {
        if (vertices == null || origem < 0 || destino < 0 ||
                origem >= vertices.size() || destino >= vertices.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        List<List<Integer>> adjVertices = construirListaAdjacenciaPorIndice();
        boolean[] isVisited = new boolean[vertices.size()];

        if (!dfsComDestino(origem, destino, isVisited, adjVertices)) {
            System.out.println("Caminho de " + vertices.get(origem).getNome() +
                    " até " + vertices.get(destino).getNome() + " não encontrado.");
        }
    }


    private void dfsRecursive(int current, boolean[] isVisited, List<List<Integer>> adjVertices) {
        isVisited[current] = true;
        visit(current); // você pode mudar isso se quiser fazer outra coisa com o vértice

        for (int dest : adjVertices.get(current)) {
            if (!isVisited[dest]) {
                dfsRecursive(dest, isVisited, adjVertices);
            }
        }
    }

    private boolean dfsComDestino(int current, int destino, boolean[] isVisited, List<List<Integer>> adjVertices) {
        isVisited[current] = true;
        visit(current);

        if (current == destino) {
            System.out.println("Destino encontrado!");
            return true;
        }

        for (int adj : adjVertices.get(current)) {
            if (!isVisited[adj]) {
                if (dfsComDestino(adj, destino, isVisited, adjVertices)) {
                    return true; // já encontrou, não precisa continuar
                }
            }
        }

        return false;
    }

    private List<List<Integer>> construirListaAdjacenciaPorIndice() {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            adj.add(new ArrayList<>());
        }

        for (Aresta aresta : arestas) {
            int origem = vertices.indexOf(aresta.getOrigem());
            int destino = vertices.indexOf(aresta.getDestino());

            adj.get(origem).add(destino);
            if (!isDirecionado) {
                adj.get(destino).add(origem);
            }
        }

        return adj;
    }

    private void visit(int index) {
        System.out.println("Visitando: " + vertices.get(index).getNome());
    }

    public Vertice buscarVertice(String nome) {
        for (Vertice v : vertices) {
            if (v.getNome().equalsIgnoreCase(nome)) {
                return v;
            }
        }
        return null;
    }

    public int indiceVertice(String nome) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getNome().equalsIgnoreCase(nome)) {
                return i;
            }
        }
        return -1;
    }

    public String descricao() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nGrafo ").append(isDirecionado ? "direcionado" : "não direcionado");
        sb.append("\nVértices:\n");
        for (Vertice v : vertices) {
            sb.append("  ").append(v.toString(isDirecionado)).append("\n");
        }
        sb.append("Arestas:\n");
        for (Aresta a : arestas) {
            sb.append("  ").append(a.toString()).append(" peso: ").append(a.getPeso()).append("\n");
        }
        return sb.toString();
    }

    public int grauTotal() {
        int grau = 0;
        for (Vertice v : vertices) {
            grau += v.getGrau(); // para grafos direcionados ou não
        }
        return grau;
    }








}
