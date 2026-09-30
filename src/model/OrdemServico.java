package model;

import java.time.LocalDate;

public class OrdemServico {
    private int codigo;
    private String nomeCliente;
    private String modeloVeiculo;
    private String placa;
    private LocalDate data;
    private StatusOrdem status;
    private double valorEstimado;
    private Servico servico;
    private Box box;

    public OrdemServico(int codigo, String nomeCliente, String modeloVeiculo, String placa, Servico servico) {
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        this.modeloVeiculo = modeloVeiculo;
        this.placa = placa;
        this.servico = servico;
        this.valorEstimado = servico.getValor();
        this.data = LocalDate.now();
        this.status = StatusOrdem.ABERTA;
    }

    public void atribuirBox(Box box) {
        this.box = box;
        this.status = StatusOrdem.EM_EXECUCAO;
    }

    public void finalizar() {
        this.status = StatusOrdem.FINALIZADA;
    }

    public int getCodigo() { return codigo; }
    public StatusOrdem getStatus() { return status; }
    public Box getBox() { return box; }
    public Servico getServico() { return servico; }

    public void exibirDetalhes() {
        System.out.println("---------------------------------");
        System.out.println("Ordem #" + codigo + " | Status: " + status);
        System.out.println("Cliente: " + nomeCliente);
        System.out.println("Veículo: " + modeloVeiculo + " - Placa: " + placa);
        System.out.println("Data: " + data);
        System.out.println("Serviço: " + servico);
        System.out.println("Valor estimado: R$ " + String.format("%.2f", valorEstimado));
        if (box != null) {
            System.out.println("Box: " + box.getNumero());
            Mecanico m = box.getMecanico();
            System.out.println("Mecânico: " + (m != null ? m.getNome() : "não definido"));
        } else {
            System.out.println("Box: não atribuído");
        }
    }
}