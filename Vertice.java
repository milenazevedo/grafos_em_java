// importações
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Vertice {
    private String nome;
    private List<Vertice> adjacencias = new ArrayList<>(); // cria uma lista que guarda os vértices ligados a esse vértice
    private int grau; // numero total de conexões
    private int inDegree; // vertices que chegam a este vertice
    private int outDegree; // vertices que saem do vértice

    // construtor do vértice
    public Vertice(String nome) {
        this.nome = nome;
    }

    // metodo para adicionar um novo vértice a lista de adjacencias
    public void addAdjacencia(Vertice vertice) {
        if (!adjacencias.contains(vertice)) {
            adjacencias.add(vertice);
        }
    }

    public void incrementaGrau(){
        grau++;
    }
    public void incrementaInDegree(){
        inDegree++;
    }
    public void incrementaOutDegree(){
        outDegree++;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean isDirecionado) {
        List<String> nomesAdjacentes = adjacencias.stream()
                .map(Vertice::getNome)
                .toList();
        if (isDirecionado) {
            return nome + " (in: " + inDegree + ", out: " + outDegree + ", grau: " + grau + ", adjacencias: " + nomesAdjacentes + ")";
        } else {
            return nome + " (grau: " + grau + ", adjacencias: " + nomesAdjacentes + ")";
        }
    }

}