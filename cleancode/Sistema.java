package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner sc = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db database = new Db();

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

    private int receberInput(int op) {
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
            gerarRelatorio();
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
            op = receberInput(op);
            selecionarMenu(op);

        }
    }


    private Cliente iniciarCliente() {
        System.out.println("Nome cliente:");
        String nomeCliente = sc.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int tipoCliente = 0;
        try {
            tipoCliente = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            tipoCliente = 1;
        }

        return gerarCliente(tipoCliente,nomeCliente);
    }

    private Cliente gerarCliente(int tipoCliente, String nomeCliente) {
        Cliente novoCliente = new Cliente();
        novoCliente.id = database.pegarTodosPedidos().size() + 1;
        novoCliente.nome = nomeCliente;
        novoCliente.tipo = tipoCliente;
        novoCliente.email = nomeCliente.replace(" ", "").toLowerCase() + "@email.com";

        return novoCliente;
    }

    private Pedido gerarPedido(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.id = database.pegarTodosPedidos().size() + 1;
        pedido.cliente = cliente;
        pedido.status = "NOVO";
        pedido.itens = new ArrayList<>();

        return pedido;
    }

    private void recolherInformacoesItem(Pedido pedido) {
        System.out.println("Nome item:");
        String nomeItem = sc.nextLine();

        System.out.println("Preco item:");
        double precoItem = 0;
        try {
            precoItem = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            precoItem = 0;
        }

        System.out.println("Qtd:");
        int quantidade = 0;
        try {
            quantidade = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            quantidade = 1;
        }

        Item item = gerarItem(nomeItem, precoItem, quantidade);
        pedido.itens.add(item);

    }

    private Item gerarItem(String nomeItem, double precoItem, int quantidade) {
        Item item = new Item();
        item.nome = nomeItem;
        item.preco = precoItem;
        item.qtd = quantidade;

        return item;
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
        System.out.println("Total: " + pedido.total);

        if (pedido.total > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    private void criarNovoPedido() {
        Cliente cliente = iniciarCliente();
        Pedido pedido = gerarPedido(cliente);

        adiconarItensPedido(pedido);
        pedido.total = pegarTotal(pedido, cliente);
        database.adicionarPedido(pedido);

        gerarMensagemConfirmacao(pedido);
    }

    private static double calcularDesconto(double total, Cliente cliente) {
        if (cliente.tipo == 1) {
            if (total > 300) {
                total = total - (total * 0.05);
            }
        } else if (cliente.tipo == 2) {
            if (total > 200) {
                total = total - (total * 0.10);
            } else {
                total = total - (total * 0.03);
            }
        } else {
            total = total - (total * 0.15);
        }

        return total;
    }

    private static double calcularFrete(double total) {
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        }

        return total;
    }

    private static double pegarTotal(Pedido pedido, Cliente cliente) {
        double total = pedido.calcularPrecoPedido();

        total = calcularDesconto(total, cliente);
        total = calcularFrete(total);
        return total;
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
        int id = Integer.parseInt(sc.nextLine());
        boolean achou = false;

        Pedido pedidoEcontrado = database.pegarPedidoPorId(id);

            if (pedidoEcontrado.id == id) {
                achou = true;


                double subtotal = 0;
                for (int j = 0; j < pedidoEcontrado.itens.size(); j++) {
                    subtotal = subtotal + (pedidoEcontrado.itens.get(j).preco * pedidoEcontrado.itens.get(j).qtd);
                }
                System.out.println("subtotal calculado novamente: " + subtotal);

                if (pedidoEcontrado.cliente.tipo == 1) {
                    System.out.println("cliente comum");
                } else if (pedidoEcontrado.cliente.tipo == 2) {
                    System.out.println("cliente premium");
                } else if (pedidoEcontrado.cliente.tipo == 3) {
                    System.out.println("cliente vip");
                } else {
                    System.out.println("cliente desconhecido");
                }

                for (int j = 0; j < pedidoEcontrado.itens.size(); j++) {
                    Item it = pedidoEcontrado.itens.get(j);
                    System.out.println("item " + (j + 1) + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                }
            }


        if (!achou) {
            System.out.println("nao achou");
        }
    }

    public void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.gerarRealtorios(database.pegarTodosPedidos());
    }

    public void cancelarPedido() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(sc.nextLine());

        for (Pedido pedido : database.pegarTodosPedidos()) {
            if (pedido.id == id) {
                if (pedido.status.equals("CANCELADO")) {
                    System.out.println("ja cancelado");
                } else {
                    pedido.status = "CANCELADO";
                    System.out.println("cancelado");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }
}

