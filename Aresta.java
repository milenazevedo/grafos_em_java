public class Aresta {
    private String nome;
    private Vertice origem;
    private Vertice destino;

    public Aresta(String nome, Vertice origem, Vertice destino) {
        this.nome = nome;
        this.origem = origem;
        this.destino = destino;
    }

    public Aresta(Vertice origem, Vertice destino) {
        this.origem = origem;
        this.destino = destino;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Vertice getOrigem() {
        return origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        String nomeASerPrintado = nome == null ? "" : nome;
        return nomeASerPrintado + "(" + origem + ", " + destino + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Aresta aresta = (Aresta) obj;
        return origem.equals(aresta.origem) && destino.equals(aresta.destino);
    }

    @Override
    public int hashCode() {
        return origem.hashCode() * destino.hashCode();
    }
}
