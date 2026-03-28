package cleancode;

import java.util.List;

public class RelatorioPedidos implements Relatorio {

    @Override
    public void gerar(List<Pedido> pedidos) {
        EstatisticasPedidos stats = new EstatisticasPedidos(pedidos);

        System.out.println("======= RELATORIO =======");

        for (Pedido p : pedidos) {
            exibirLinhaPedido(p);
        }

        System.out.println("--------------------");
        System.out.println("Qtd pedidos: " + stats.totalPedidos);
        System.out.println("Valor total: " + stats.somaTotal);
        System.out.println("Média: " + stats.calcularMedia());
        System.out.println("Cancelados: " + stats.totalCancelados);
        System.out.println("Clientes (C/P/V): " + stats.comuns + "/" + stats.premiums + "/" + stats.vips);
        System.out.println("Avaliação: " + stats.avaliarDesempenho());
    }

    private void exibirLinhaPedido(Pedido pedido) {
        System.out.print("Pedido " + pedido.id + " | Status: " + pedido.status + " | ");
        System.out.println("Cliente: " + pedido.cliente.nome + " | Total: " + pedido.total);
    }
}