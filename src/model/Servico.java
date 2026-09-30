package model;

public class Servico {
    private String nome;
    private int tempoEstimado;
    private double valor;
    private String categoria;

    public Servico(String nome, int tempoEstimado, double valor, String categoria) {
        this.nome = nome;
        this.tempoEstimado = tempoEstimado;
        this.valor = valor;
        this.categoria = categoria;
    }

    public String getNome() { return nome; }
    public int getTempoEstimado() { return tempoEstimado; }
    public double getValor() { return valor; }
    public String getCategoria() { return categoria; }

    @Override
    public String toString() {
        return nome + " [" + categoria + "] - " + tempoEstimado + " min - R$ " + String.format("%.2f", valor);
    }
}