import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@Setter
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

    public boolean existeCaminhoSimples(Vertice origem, Vertice destino) {
        Set<Vertice> visitados = new HashSet<>();
        return dfs(origem, destino, visitados);
    }

    private boolean dfs(Vertice atual, Vertice destino, Set<Vertice> visitados) {
        if (atual.equals(destino)) {
            return true;
        }

        visitados.add(atual);

        for (Vertice vizinho : atual.getAdjacencias()) {
            if (!visitados.contains(vizinho)) {
                if (dfs(vizinho, destino, visitados)) {
                    return true;
                }
            }
        }

        return false;
    }

    public double comprimentoDoCaminho(Vertice origem, Vertice destino) {
        if (isDirecionado || contemPesosReais()) {
            return caminhoPonderado(origem, destino);
        } else {
            return caminhoNaoPonderado(origem, destino);
        }
    }

    private boolean contemPesosReais() {
        return arestas.stream().anyMatch(a -> a.getPeso() != 1.0);
    }

    private int caminhoNaoPonderado(Vertice origem, Vertice destino) {
        Queue<Vertice> fila = new LinkedList<>();
        Map<Vertice, Integer> distancias = new HashMap<>();

        fila.add(origem);
        distancias.put(origem, 0);

        while (!fila.isEmpty()) {
            Vertice atual = fila.poll();
            int distAtual = distancias.get(atual);

            if (atual.equals(destino)) {
                return distAtual;
            }

            for (Vertice vizinho : atual.getAdjacencias()) {
                if (!distancias.containsKey(vizinho)) {
                    distancias.put(vizinho, distAtual + 1);
                    fila.add(vizinho);
                }
            }
        }

        return -1;
    }

    private double caminhoPonderado(Vertice origem, Vertice destino) {
        Map<Vertice, Double> distancias = new HashMap<>();
        PriorityQueue<VerticeDistancia> fila = new PriorityQueue<>(Comparator.comparingDouble(v -> v.distancia));
        Set<Vertice> visitados = new HashSet<>();

        for (Vertice v : vertices) {
            distancias.put(v, Double.MAX_VALUE);
        }

        distancias.put(origem, 0.0);
        fila.add(new VerticeDistancia(origem, 0.0));

        while (!fila.isEmpty()) {
            VerticeDistancia atual = fila.poll();
            if (!visitados.add(atual.vertice)) continue;

            if (atual.vertice.equals(destino)) {
                return atual.distancia;
            }

            for (Vertice vizinho : atual.vertice.getAdjacencias()) {
                double peso = pesoDaAresta(atual.vertice, vizinho);
                double novaDist = atual.distancia + peso;

                if (novaDist < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDist);
                    fila.add(new VerticeDistancia(vizinho, novaDist));
                }
            }
        }

        return -1;
    }

    private double pesoDaAresta(Vertice origem, Vertice destino) {
        for (Aresta a : arestas) {
            if (a.getOrigem().equals(origem) && a.getDestino().equals(destino)) {
                return a.getPeso();
            }
            if (!isDirecionado && a.getOrigem().equals(destino) && a.getDestino().equals(origem)) {
                return a.getPeso();
            }
        }
        return Double.MAX_VALUE;
    }

    private static class VerticeDistancia {
        Vertice vertice;
        double distancia;

        public VerticeDistancia(Vertice vertice, double distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }
    }

    public List<Vertice> identificarCaminhoMaisCurto(Vertice origem, Vertice destino) {
        Map<Vertice, Double> distancias = new HashMap<>();
        Map<Vertice, Vertice> predecessores = new HashMap<>();
        PriorityQueue<VerticeDistancia> fila = new PriorityQueue<>(Comparator.comparingDouble(v -> v.distancia));
        Set<Vertice> visitados = new HashSet<>();

        for (Vertice v : vertices) {
            distancias.put(v, Double.MAX_VALUE);
        }

        distancias.put(origem, 0.0);
        fila.add(new VerticeDistancia(origem, 0.0));

        while (!fila.isEmpty()) {
            VerticeDistancia atual = fila.poll();
            if (!visitados.add(atual.vertice)) continue;

            if (atual.vertice.equals(destino)) {
                return reconstruirCaminho(predecessores, destino);
            }

            for (Vertice vizinho : atual.vertice.getAdjacencias()) {
                double peso = pesoDaAresta(atual.vertice, vizinho);
                double novaDist = atual.distancia + peso;

                if (novaDist < distancias.get(vizinho)) {
                    distancias.put(vizinho, novaDist);
                    predecessores.put(vizinho, atual.vertice);
                    fila.add(new VerticeDistancia(vizinho, novaDist));
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Vertice> reconstruirCaminho(Map<Vertice, Vertice> predecessores, Vertice destino) {
        List<Vertice> caminho = new ArrayList<>();
        for (Vertice v = destino; v != null; v = predecessores.get(v)) {
            caminho.add(v);
        }
        Collections.reverse(caminho);
        return caminho;
    }

}
