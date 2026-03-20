package cleancode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    Scanner sc = new Scanner(System.in);
    List<Pedido> pedidos = new ArrayList<>();
    Db db = new Db();

    public void run() {
        int op = -1;

        while (op != 0) {
            System.out.println("==== SISTEMA ====");
            System.out.println("1 - Novo pedido");
            System.out.println("2 - Listar pedidos");
            System.out.println("3 - Buscar pedido por id");
            System.out.println("4 - Relatorio");
            System.out.println("5 - Cancelar pedido");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");

            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("erro");
                op = -1;
            }

            if (op == 1) {
                novoPedido();
            } else if (op == 2) {
                listar();
            } else if (op == 3) {
                buscar();
            } else if (op == 4) {
                rel();
            } else if (op == 5) {
                cancelar();
            } else if (op == 0) {
                System.out.println("fim");
            } else {
                System.out.println("opcao invalida");
            }
        }
    }

    public void novoPedido() {
        System.out.println("Nome cliente:");
        String n = sc.nextLine();

        System.out.println("Tipo cliente (1 comum, 2 premium, 3 vip):");
        int t = 0;
        try {
            t = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("tipo errado, vai comum");
            t = 1;
        }

        Cliente c = new Cliente();
        c.id = pedidos.size() + 1;
        c.nome = n;
        c.tipo = t;
        c.email = n.replace(" ", "").toLowerCase() + "@email.com";

        Pedido p = new Pedido();
        p.id = pedidos.size() + 1;
        p.cliente = c;
        p.status = "NOVO";
        p.itens = new ArrayList<>();

        String continua = "s";
        while (continua.equalsIgnoreCase("s")) {
            System.out.println("Nome item:");
            String ni = sc.nextLine();

            System.out.println("Preco item:");
            double pr = 0;
            try {
                pr = Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                pr = 0;
            }

            System.out.println("Qtd:");
            int q = 0;
            try {
                q = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                q = 1;
            }

            Item i = new Item();
            i.nome = ni;
            i.preco = pr;
            i.qtd = q;
            p.itens.add(i);

            System.out.println("Adicionar mais item? s/n");
            continua = sc.nextLine();
        }

        double total = 0;
        for (int i = 0; i < p.itens.size(); i++) {
            total = total + (p.itens.get(i).preco * p.itens.get(i).qtd);
        }

        // regra de desconto
        if (c.tipo == 1) {
            if (total > 300) {
                total = total - (total * 0.05);
            }
        } else if (c.tipo == 2) {
            if (total > 200) {
                total = total - (total * 0.10);
            } else {
                total = total - (total * 0.03);
            }
        } else if (c.tipo == 3) {
            total = total - (total * 0.15);
        } else {
            total = total;
        }

        // frete
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        } else {
            total = total + 0;
        }

        p.total = total;

        pedidos.add(p);
        db.adicionaPedido(p);

        System.out.println("Pedido criado com sucesso");
        System.out.println("Id: " + p.id);
        System.out.println("Cliente: " + p.cliente.nome);
        System.out.println("Total: " + p.total);

        if (p.total > 500) {
            System.out.println("Pedido importante!!!");
        }
    }

    public void listar() {
        if (pedidos.size() == 0) {
            System.out.println("sem pedidos");
        } else {
            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                System.out.println("---------------");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("email: " + p.cliente.email);
                System.out.println("tipo: " + p.cliente.tipo);
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

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            if (p.id == id) {
                achou = true;
                System.out.println("Pedido encontrado");
                System.out.println("id: " + p.id);
                System.out.println("cliente: " + p.cliente.nome);
                System.out.println("status: " + p.status);
                System.out.println("total: " + p.total);

                double subtotal = 0;
                for (int j = 0; j < p.itens.size(); j++) {
                    subtotal = subtotal + (p.itens.get(j).preco * p.itens.get(j).qtd);
                }
                System.out.println("subtotal calculado novamente: " + subtotal);

                if (p.cliente.tipo == 1) {
                    System.out.println("cliente comum");
                } else if (p.cliente.tipo == 2) {
                    System.out.println("cliente premium");
                } else if (p.cliente.tipo == 3) {
                    System.out.println("cliente vip");
                } else {
                    System.out.println("cliente desconhecido");
                }

                for (int j = 0; j < p.itens.size(); j++) {
                    Item it = p.itens.get(j);
                    System.out.println("item " + (j + 1) + ": " + it.nome + " / " + it.qtd + " / " + it.preco);
                }
            }
        }

        if (achou == false) {
            System.out.println("nao achou");
        }
    }

    public void rel() {
        Relatorio r = new Relatorio();
        r.gerar(pedidos);
    }

    public void cancelar() {
        System.out.println("Digite id do pedido");
        int id = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).id == id) {
                if (pedidos.get(i).status.equals("CANCELADO")) {
                    System.out.println("ja cancelado");
                } else {
                    pedidos.get(i).status = "CANCELADO";
                    System.out.println("cancelado");
                }
                return;
            }
        }

        System.out.println("pedido nao existe");
    }
}
