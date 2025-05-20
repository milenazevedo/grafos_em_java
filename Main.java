import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Grafo grafo = new Grafo();
        grafo.setVertices(new ArrayList<>());
        grafo.setArestas(new ArrayList<>());

        int opcao;

        do {
            System.out.println("\n-------- MENU --------");
            System.out.println("1. Adicionar vértice");
            System.out.println("2. Adicionar aresta");
            System.out.println("3. Descrição do grafo");
            System.out.println("4. Grau, ordem e tamnho");
            System.out.println("5. Matriz de Adjacência");
            System.out.println("6. Matriz de Incidência");
            System.out.println("7. Caminhada");
            System.out.println("8. Busca em profundidade");
            System.out.println("0. Sair");
            System.out.println("Escolha uma opção: ");

            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Nome do vértice: ");
                    String nome = entrada.nextLine();
                    Vertice novo = new Vertice(nome);
                    grafo.getVertices().add(novo);
                    grafo.setVertices(grafo.getVertices());
                    break;
                case 2:
                    System.out.println("Vértice de origem: ");
                    String origemNome = entrada.nextLine();
                    System.out.println("Vértice de destino: ");
                    String destinoNome = entrada.nextLine();
                    System.out.println("Peso da aresta: ");
                    double peso = entrada.nextDouble();
                    entrada.nextLine();

                    Vertice origem = grafo.buscarVertice(origemNome);
                    Vertice destino = grafo.buscarVertice(destinoNome);

                    if (origem != null && destino != null) {
                        Aresta aresta = new Aresta(origem, destino, peso);
                        grafo.getArestas().add(aresta);
                        grafo.setArestas(grafo.getArestas());
                    } else {
                        System.out.println("Vértice(s) não encontrado(s)!");
                    }
                    break;
                case 3:
                    System.out.println(grafo.descricao());
                    break;
                case 4:
                    System.out.println("Ordem do grafo (número de vértices): " + grafo.getOrdem());
                    System.out.println("Tamanho do grafo (número de arestas): " + grafo.getTamanho());
                    System.out.println("Grau do grafo (soma dos graus dos vértices): " + grafo.grauTotal());
                    break;
                case 5:
                    grafo.imprimirMatrizAdjacencia();
                    break;
                case 6:
                    grafo.imprimirMatrizIncidencia();
                    break;
                case 7:
                    System.out.println("Nome do vértice inicial: ");
                    String nomeV = entrada.nextLine();
                    int indice = grafo.indiceVertice(nomeV);
                    if (indice != -1) {
                        grafo.dfs(indice, -1);
                    } else {
                        System.out.println("Vértice não encontrado.");
                    }
                    break;
                case 8:
                    System.out.println("Vértice de origem: ");
                    String nomeOrigem = entrada.nextLine();
                    System.out.println("Vértice de destino: ");
                    String nomeDestino = entrada.nextLine();
                    int indiceOrigem = grafo.indiceVertice(nomeOrigem);
                    int indiceDestino = grafo.indiceVertice(nomeDestino);

                    if (indiceOrigem != -1 && indiceDestino != -1) {
                        grafo.dfs(indiceOrigem, indiceDestino);
                    } else {
                        System.out.println("Um dos vértices não foi enconntrado.");
                    }
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

        } while (opcao != 0);

    }
}
