import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Aresta {
    private String nome;
    private Vertice origem;
    private Vertice destino;
    private double peso;

    public Aresta(Vertice origem, Vertice destino, double peso){
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString(){
        String nomeASerPrintado = nome == null ? "" : nome;
        return nomeASerPrintado + "(" + origem + ", " + destino + ")";
    }
}
