package cleancode;

import java.util.ArrayList;

public class PedidoFactory {

    public static Pedido criar(Cliente cliente, int proximoId) {
        Pedido pedido = new Pedido();

        pedido.id = proximoId;
        pedido.cliente = cliente;
        pedido.status = "NOVO";
        pedido.itens = new ArrayList<>();
        pedido.total = 0;

        return pedido;
    }
} // Open/Closed Principle (OCP);