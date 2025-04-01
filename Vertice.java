import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Vertice {
    private String nome;
    private List<Vertice> adjacencias = new ArrayList<>();
    private int grau;
    private int inDegree;
    private int outDegree;

    public Vertice(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Vertice> getAdjacencias() {
        return adjacencias;
    }

    public void setAdjacencias(List<Vertice> adjacencias) {
        this.adjacencias = adjacencias;
    }

    public int getGrau() {
        return grau;
    }

    public void setGrau(int grau) {
        this.grau = grau;
    }

    public int getInDegree() {
        return inDegree;
    }

    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public void setOutDegree(int outDegree) {
        this.outDegree = outDegree;
    }

    public void addAdjacencia(Vertice vertice) {
        adjacencias.add(vertice);
    }

    public void incrementaGrau() {
        grau++;
    }

    public void incrementaInDegree() {
        inDegree++;
    }

    public void incrementaOutDegree() {
        outDegree++;
    }

    @Override
    public String toString() {
        String adjacenciasStr = adjacencias.stream()
                .map(Vertice::getNome)
                .collect(Collectors.joining(", "));

        String grauInfo = "grau: " + grau;
        if (isDirecionado()) {
            grauInfo += ", inDegree: " + inDegree + ", outDegree: " + outDegree;
        }

        return nome + " (" + grauInfo + ", adjacencias: [" + adjacenciasStr + "])";
    }

    private boolean isDirecionado() {
        return inDegree > 0 || outDegree > 0;
    }
}
