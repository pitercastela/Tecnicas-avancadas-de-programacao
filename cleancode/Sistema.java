package cleancode;

import java.util.Scanner;

public class Sistema {

    private Scanner sc = new Scanner(System.in);
    private PedidoRepositorio database;
    private Relatorio relatorio;

    public Sistema(PedidoRepositorio repositorio, Relatorio relatorio) {
        this.database = repositorio;
        this.relatorio = relatorio;
    }

    public void imprimirMenu() {
        System.out.println("==== SISTEMA ====");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Buscar pedido por id");
        System.out.println("4 - Relatorio");
        System.out.println("5 - Cancelar pedido");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }

    private int receberInput() {
        int op;
        try {
            op = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("erro");
            op = -1;
            return op;
        }
        return op;
    }
    private void selecionarMenu(int op) {
        if (op == 1) {
            criarNovoPedido();
        } else if (op == 2) {
            listarPedidos();
        } else if (op == 3) {
            buscarPedidos();
        } else if (op == 4) {
            gerarRelatorio(this.relatorio);
        } else if (op == 5) {
            cancelarPedido();
        } else if (op == 0) {
            System.out.println("fim");
        } else {
            System.out.println("opcao invalida");
        }
    }

    public void rodarPrograma() {
        int op = -1;

        while (op != 0) {
            imprimirMenu();
            op = receberInput();
            selecionarMenu(op);

        }
    }


    private Cliente iniciarCliente() {
        System.out.println("Nome cliente:");
        String nomeCliente = sc.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipoCliente;
        try {
            tipoCliente = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            tipoCliente = 1;
        }

        Cliente cliente = ClienteFactory.criar(tipoCliente, nomeCliente);
        cliente.id = database.pegarId();

        return cliente;
    }

    private void recolherInformacoesItem(Pedido pedido) {
        System.out.println("Nome item:");
        String nomeItem = sc.nextLine();

        System.out.println("Preco item:");
        double precoItem;
        try {
            precoItem = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            precoItem = 0;
        }

        System.out.println("Qtd:");
        int quantidade;
        try {
            quantidade = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            quantidade = 1;
        }
        Item item = ItemFactory.criar(nomeItem, precoItem, quantidade);
        pedido.itens.add(item);
    }

    private void adiconarItensPedido(Pedido pedido) {
        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            recolherInformacoesItem(pedido);
            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }
    }

    private void gerarMensagemConfirmacao(Pedido pedido) {
        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + pedido.id);
        System.out.println("Cliente: " + pedido.cliente.nome);
        System.out.println("Total: " + pedido.calcularPrecoPedido());

        if (pedido.pedidoGrande()) {
            System.out.println("Pedido importante!!!");
        }
    }

    private void criarNovoPedido() {
        Cliente cliente = iniciarCliente();

        int novoId = database.pegarId();
        Pedido pedido = PedidoFactory.criar(cliente, novoId);

        adiconarItensPedido(pedido);
        pedido.total = CalculadoraPreco.pegarTotal(pedido, cliente);
        database.adicionarPedido(pedido);

        gerarMensagemConfirmacao(pedido);
    }

    public void listarPedidos() {
        if (database.pegarTodosPedidos().isEmpty()) {
            System.out.println("sem pedidos");
        } else {
            for (Pedido pedido : database.pegarTodosPedidos()) {
                System.out.println("---------------");
                pedido.exibirPedido();
            }
        }
    }

    public void buscarPedidos() {
        System.out.println("Digite o id:");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Pedido pedidoEncontrado = database.pegarPedidoPorId(id);

        if (pedidoEncontrado != null) {
            System.out.println("=== Detalhes do Pedido Encontrado ===");
            pedidoEncontrado.exibirPedido();

            // Se ainda quiser mostrar o subtotal calculado na hora como no original:
            System.out.println("Subtotal (soma dos itens): " + pedidoEncontrado.calcularPrecoPedido());
        } else {
            System.out.println("Pedido não encontrado.");
        }
    }

    public void gerarRelatorio(Relatorio relatorio) {
        if (database.pegarTodosPedidos().isEmpty()) {
            System.out.println("Não há dados para gerar o relatório.");
            return;
        }
        relatorio.gerar(database.pegarTodosPedidos());
    }



    public void cancelarPedido() {
        System.out.println("Digite id do pedido:");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }
        Pedido pedido = database.pegarPedidoPorId(id);

        if (pedido == null) {
            System.out.println("Pedido não existe.");
            return;
        }

        if (pedido.cancelar()) {
            System.out.println("Pedido cancelado com sucesso!");
        } else {
            System.out.println("Este pedido já estava cancelado.");
        }
    }
}