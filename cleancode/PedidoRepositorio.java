package cleancode;

import java.util.List;

public interface PedidoRepositorio {
    void adicionarPedido(Pedido pedido);
    List<Pedido> pegarTodosPedidos();
    Pedido pegarPedidoPorId(int id);
    int pegarId();
}