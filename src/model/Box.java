package model;

public class Box {
    private int numero;
    private String tipoServicoPermitido;
    private int capacidadeMaxima;
    private String localizacao;
    private Mecanico mecanico;

    public Box(int numero, String tipoServicoPermitido, int capacidadeMaxima, String localizacao) {
        this.numero = numero;
        this.tipoServicoPermitido = tipoServicoPermitido;
        this.capacidadeMaxima = capacidadeMaxima;
        this.localizacao = localizacao;
    }

    public int getNumero() { return numero; }
    public String getTipoServicoPermitido() { return tipoServicoPermitido; }
    public int getCapacidadeMaxima() { return capacidadeMaxima; }
    public String getLocalizacao() { return localizacao; }
    public Mecanico getMecanico() { return mecanico; }
    public void setMecanico(Mecanico mecanico) { this.mecanico = mecanico; }

    @Override
    public String toString() {
        String resp = (mecanico != null) ? mecanico.getNome() : "sem mecânico";
        return "Box " + numero + " | tipo: " + tipoServicoPermitido + " | capacidade: "
                + capacidadeMaxima + " | local: " + localizacao + " | responsável: " + resp;
    }
}