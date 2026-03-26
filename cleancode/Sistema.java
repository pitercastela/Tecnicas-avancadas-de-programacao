package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner sc = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db database = new Db();

    public void imprimeMenu() {
        System.out.println("==== SISTEMA ====");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Listar pedidos");
        System.out.println("3 - Buscar pedido por id");
        System.out.println("4 - Relatorio");
        System.out.println("5 - Cancelar pedido");
        System.out.println("0 - Sair");
        System.out.print("Opcao: ");
    }

    private int recebeInput(int op) {
        try {
            op = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("erro");
            op = -1;
            return op;
        }
        return op;
    }
    private void selecionaMenu(int op) {
        if (op == 1) {
            novoPedido();
        } else if (op == 2) {
            listar();
        } else if (op == 3) {
            buscar();
        } else if (op == 4) {
            relatorio();
        } else if (op == 5) {
            cancelar();
        } else if (op == 0) {
            System.out.println("fim");
        } else {
            System.out.println("opcao invalida");
        }
    }

    public void run() {
        int op = -1;

        while (op != 0) {
            imprimeMenu();
            op = recebeInput(op);
            selecionaMenu(op);

        }
    }


    private Cliente iniciaCliente() {
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

        return geraCliente(tipoCliente,nomeCliente);
    }

    private Cliente geraCliente(int tipoCliente, String nomeCliente) {
        Cliente novoCliente = new Cliente();
        novoCliente.id = database.pegarTodosPedidos().size() + 1;
        novoCliente.nome = nomeCliente;
        novoCliente.tipo = tipoCliente;
        novoCliente.email = nomeCliente.replace(" ", "").toLowerCase() + "@email.com";

        return novoCliente;
    }

    private Pedido geraPedido(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.id = database.pegarTodosPedidos().size() + 1;
        pedido.cliente = cliente;
        pedido.status = "NOVO";
        pedido.itens = new ArrayList<>();

        return pedido;
    }

    private void recolheInformacoesItem(Pedido pedido) {
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

        Item item = geraItem(nomeItem, precoItem, quantidade);
        pedido.itens.add(item);

    }

    private Item geraItem(String nomeItem, double precoItem, int quantidade) {
        Item item = new Item();
        item.nome = nomeItem;
        item.preco = precoItem;
        item.qtd = quantidade;

        return item;
    }

    private void adiconaItensNoPedido(Pedido pedido) {
        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            recolheInformacoesItem(pedido);
            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }
    }

    private void mensagemDeConfirmacao(Pedido pedido) {
        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + pedido.id);
        System.out.println("Cliente: " + pedido.cliente.nome);
        System.out.println("Total: " + pedido.total);

        if (pedido.total > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    private void novoPedido() {
        Cliente cliente = iniciaCliente();
        Pedido pedido = geraPedido(cliente);

        adiconaItensNoPedido(pedido);
        pedido.total = getTotal(pedido, cliente);
        database.adicionaPedido(pedido);

        mensagemDeConfirmacao(pedido);
    }

    private static double calculaDesconto(double total, Cliente cliente) {
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

    private static double calculaFrete(double total) {
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        }

        return total;
    }

    private static double getTotal(Pedido pedido, Cliente cliente) {
        double total = pedido.calculaPrecoDoPedido();

        total = calculaDesconto(total, cliente);
        total = calculaFrete(total);
        return total;
    }

    public void listar() {
        if (database.pegarTodosPedidos().isEmpty()) {
            System.out.println("sem pedidos");
        } else {
            for (Pedido p : database.pegarTodosPedidos()) {
                System.out.println("---------------");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("email: " + p.cliente.email);
                System.out.println("tipo: " + p.cliente.getTipoDesc());
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);
                System.out.println("itens:");
                for (int j = 0; j < p.itens.size(); j++) {
                    Item it = p.itens.get(j);
                    System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
                }
            }
        }
    }

    public void buscar() {
        System.out.println("Digite o id:");
        int id = Integer.parseInt(sc.nextLine());
        boolean achou = false;

        Pedido pedidoEcontrado = database.pegarPedidoPorId(id);

            if (pedidoEcontrado.id == id) {
                achou = true;
                System.out.println("Pedido encontrado");
                System.out.println("id: " + pedidoEcontrado.id);
                System.out.println("cliente: " + pedidoEcontrado.cliente.nome);
                System.out.println("status: " + pedidoEcontrado.status);
                System.out.println("total: " + pedidoEcontrado.total);

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

    public void relatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.gerar(database.pegarTodosPedidos());
    }

    public void cancelar() {
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

