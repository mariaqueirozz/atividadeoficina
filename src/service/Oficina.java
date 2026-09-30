package service;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class Oficina {
    private List<Mecanico> mecanicos = new ArrayList<>();
    private List<Box> boxes = new ArrayList<>();
    private List<OrdemServico> ordens = new ArrayList<>();
    private int proximoCodigo = 1;

    public void adicionarMecanico(Mecanico m) { mecanicos.add(m); }
    public void adicionarBox(Box b) { boxes.add(b); }
    public List<Mecanico> getMecanicos() { return mecanicos; }
    public List<Box> getBoxes() { return boxes; }

    public Box buscarBox(int numero) {
        for (Box b : boxes) if (b.getNumero() == numero) return b;
        return null;
    }

    public OrdemServico buscarOrdem(int codigo) {
        for (OrdemServico o : ordens) if (o.getCodigo() == codigo) return o;
        return null;
    }

    public OrdemServico cadastrarOrdem(String cliente, String modelo, String placa, Servico servico) {
        OrdemServico o = new OrdemServico(proximoCodigo++, cliente, modelo, placa, servico);
        ordens.add(o);
        return o;
    }

    public void associarMecanicoABox(Mecanico m, Box box) {
        for (Box outro : boxes) {
            if (outro != box && outro.getMecanico() == m) {
                throw new IllegalStateException(
                        m.getNome() + " já é responsável pelo box " + outro.getNumero());
            }
        }
        box.setMecanico(m);
    }

    public void atribuirOrdemABox(OrdemServico ordem, Box box) {
        if (ordem.getStatus() != StatusOrdem.ABERTA)
            throw new IllegalStateException("Só ordens abertas podem ser atribuídas a um box.");
        if (box.getMecanico() == null)
            throw new IllegalStateException("O box não tem mecânico responsável.");
        if (!box.getTipoServicoPermitido().equalsIgnoreCase(ordem.getServico().getCategoria()))
            throw new IllegalStateException("Este box não aceita serviços da categoria "
                    + ordem.getServico().getCategoria() + ".");
        if (contarEmExecucao(box) >= box.getCapacidadeMaxima())
            throw new IllegalStateException("O box atingiu a capacidade máxima.");
        ordem.atribuirBox(box);
    }

    private int contarEmExecucao(Box box) {
        int c = 0;
        for (OrdemServico o : ordens)
            if (o.getBox() == box && o.getStatus() == StatusOrdem.EM_EXECUCAO) c++;
        return c;
    }

    public void finalizarOrdem(OrdemServico ordem) {
        if (ordem.getStatus() != StatusOrdem.EM_EXECUCAO)
            throw new IllegalStateException("Só ordens em execução podem ser finalizadas.");
        ordem.finalizar();
    }

    public void exibirOrdensDoBox(Box box) {
        int total = 0;
        for (OrdemServico o : ordens) {
            if (o.getBox() == box) {
                o.exibirDetalhes();
                total++;
            }
        }
        System.out.println("---------------------------------");
        System.out.println("Total de ordens no box " + box.getNumero() + ": " + total);
    }

    public void exibirFinalizadasPorBox() {
        for (Box b : boxes) {
            int c = 0;
            for (OrdemServico o : ordens)
                if (o.getBox() == b && o.getStatus() == StatusOrdem.FINALIZADA) c++;
            System.out.println("Box " + b.getNumero() + ": " + c + " ordem(ns) finalizada(s)");
        }
    }

    public void buscarPorStatus(StatusOrdem status) {
        int achadas = 0;
        for (OrdemServico o : ordens) {
            if (o.getStatus() == status) {
                o.exibirDetalhes();
                achadas++;
            }
        }
        if (achadas == 0) System.out.println("Nenhuma ordem com status " + status + ".");
    }
}