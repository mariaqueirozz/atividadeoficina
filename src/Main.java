import model.*;
import service.Oficina;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final Oficina oficina = new Oficina();

    public static void main(String[] args) {
        criarDadosIniciais();
        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarOrdem();
                    case 2 -> associarMecanico();
                    case 3 -> atribuirOrdem();
                    case 4 -> oficina.exibirOrdensDoBox(escolherBox());
                    case 5 -> oficina.exibirFinalizadasPorBox();
                    case 6 -> buscarPorStatus();
                    case 7 -> detalhesOrdem();
                    case 8 -> oficina.finalizarOrdem(escolherOrdem());
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void criarDadosIniciais() {
        oficina.adicionarMecanico(new Mecanico("Carlos", "111.111.111-11", "Motor", "3199999-0001"));
        oficina.adicionarMecanico(new Mecanico("Ana", "222.222.222-22", "Freios", "3199999-0002"));
        oficina.adicionarMecanico(new Mecanico("Bruno", "333.333.333-33", "Revisão", "3199999-0003"));

        oficina.adicionarBox(new Box(1, "Motor", 2, "Setor A"));
        oficina.adicionarBox(new Box(2, "Freios", 2, "Setor B"));
        oficina.adicionarBox(new Box(3, "Revisão", 3, "Setor C"));
    }

    private static void exibirMenu() {
        System.out.println("\n===== OFICINA =====");
        System.out.println("1 - Cadastrar ordem de serviço");
        System.out.println("2 - Associar mecânico a um box");
        System.out.println("3 - Atribuir ordem a um box");
        System.out.println("4 - Exibir ordens de um box");
        System.out.println("5 - Ordens finalizadas por box");
        System.out.println("6 - Buscar ordens por status");
        System.out.println("7 - Detalhes de uma ordem");
        System.out.println("8 - Finalizar ordem");
        System.out.println("0 - Sair");
    }

    private static void cadastrarOrdem() {
        System.out.print("Nome do cliente: ");
        String cliente = sc.nextLine();
        System.out.print("Modelo do veículo: ");
        String modelo = sc.nextLine();
        System.out.print("Placa: ");
        String placa = sc.nextLine();
        System.out.print("Nome do serviço: ");
        String nomeServ = sc.nextLine();
        int tempo = lerInt("Tempo estimado (min): ");
        double valor = lerDouble("Valor: ");
        System.out.print("Categoria (Motor, Freios, Revisão): ");
        String categoria = sc.nextLine();

        Servico s = new Servico(nomeServ, tempo, valor, categoria);
        OrdemServico o = oficina.cadastrarOrdem(cliente, modelo, placa, s);
        System.out.println("Ordem cadastrada com código " + o.getCodigo());
    }

    private static void associarMecanico() {
        System.out.println("Mecânicos:");
        for (int i = 0; i < oficina.getMecanicos().size(); i++)
            System.out.println((i + 1) + " - " + oficina.getMecanicos().get(i));
        int idx = lerInt("Escolha o mecânico: ") - 1;
        if (idx < 0 || idx >= oficina.getMecanicos().size())
            throw new IllegalArgumentException("Mecânico inválido.");
        Box box = escolherBox();
        oficina.associarMecanicoABox(oficina.getMecanicos().get(idx), box);
        System.out.println("Mecânico associado ao box " + box.getNumero());
    }

    private static void atribuirOrdem() {
        OrdemServico o = escolherOrdem();
        Box b = escolherBox();
        oficina.atribuirOrdemABox(o, b);
        System.out.println("Ordem atribuída ao box " + b.getNumero());
    }

    private static void buscarPorStatus() {
        System.out.println("1 - Aberta | 2 - Em execução | 3 - Finalizada");
        int op = lerInt("Status: ");
        StatusOrdem st = switch (op) {
            case 1 -> StatusOrdem.ABERTA;
            case 2 -> StatusOrdem.EM_EXECUCAO;
            case 3 -> StatusOrdem.FINALIZADA;
            default -> throw new IllegalArgumentException("Status inválido.");
        };
        oficina.buscarPorStatus(st);
    }

    private static void detalhesOrdem() {
        escolherOrdem().exibirDetalhes();
    }

    private static Box escolherBox() {
        System.out.println("Boxes:");
        for (Box b : oficina.getBoxes()) System.out.println(b);
        Box box = oficina.buscarBox(lerInt("Número do box: "));
        if (box == null) throw new IllegalArgumentException("Box não encontrado.");
        return box;
    }

    private static OrdemServico escolherOrdem() {
        OrdemServico o = oficina.buscarOrdem(lerInt("Código da ordem: "));
        if (o == null) throw new IllegalArgumentException("Ordem não encontrada.");
        return o;
    }

    private static int lerInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}