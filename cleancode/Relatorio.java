package cleancode;

import java.util.List;

public class Relatorio {

    private int atualizaCancelados(Pedido pedido, int cancelados) {
        if (pedido.status.equals("CANCELADO")) {
            cancelados++;
        }
        return cancelados;
    }

    private double atualizaSoma(Pedido pedido, double soma){
        return soma + pedido.total;
    }

    private void escrevePedidos(Pedido pedido) {
        System.out.println("Pedido " + pedido.id + " - " + pedido.cliente.nome + " - " + pedido.total + " - " + pedido.status);

        for (int j = 0; j < pedido.itens.size(); j++) {
            Item it = pedido.itens.get(j);
            System.out.println("   item: " + it.nome + " qtd:" + it.qtd + " preco:" + it.preco);
        }
    }

    private void escreveResultado(double soma, int qtd){
        if (qtd > 0) {
            System.out.println("media: " + (soma / qtd));
        } else {
            System.out.println("media: 0");
        }

        if (soma > 1000) {
            System.out.println("resultado muito bom");
        } else if (soma > 500) {
            System.out.println("resultado ok");
        } else {
            System.out.println("resultado fraco");
        }
    }

    public void gerar(List<Pedido> ps) {
        System.out.println("======= RELATORIO =======");

        int qtd = 0;
        double soma = 0;
        int cancelados = 0;
        int comuns = 0;
        int premiums = 0;
        int vips = 0;

        for (Pedido pedido : ps) {

            soma = atualizaSoma(pedido, soma);
            qtd++;
            cancelados = atualizaCancelados(pedido, cancelados);

            if (pedido.cliente.tipo == 1) {
                comuns++;
            } else if (pedido.cliente.tipo == 2) {
                premiums++;
            } else if (pedido.cliente.tipo == 3) {
                vips++;
            }

            escrevePedidos(pedido);

        }

        System.out.println("--------------------");
        System.out.println("qtd pedidos: " + qtd);
        System.out.println("valor total: " + soma);
        System.out.println("cancelados: " + cancelados);
        System.out.println("clientes comuns: " + comuns);
        System.out.println("clientes premium: " + premiums);
        System.out.println("clientes vip: " + vips);

        escreveResultado(soma, qtd);
    }
}
