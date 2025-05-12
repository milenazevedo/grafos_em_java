import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Vertice {
    private String nome;
    private List<Vertice> adjacencias = new ArrayList<>();
    private int grau;
    private int inDegree;
    private int outDegree;

    public Vertice(String nome) {
        this.nome = nome;
    }

    public void addAdjacencia(Vertice vertice) {
        adjacencias.add(vertice);
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
        return toString(false); // default se chamado sem argumento
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