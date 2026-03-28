package cleancode;

import java.util.ArrayList;
import java.util.List;

public class Db implements PedidoRepositorio {

    public static List<Pedido> banco = new ArrayList<>();

    @Override
    public void adicionarPedido(Pedido pedido) {
        try {
            banco.add(pedido);
            System.out.println("salvou no banco");
        } catch (Exception e) {
            System.out.println("erro ao salvar");
        }
    }
    @Override
    public List<Pedido> pegarTodosPedidos() {
        return banco;
    }

    @Override
    public Pedido pegarPedidoPorId(int id) {
        for (Pedido pedido : banco) {
            if (pedido.id == id) {
                return pedido;
            }
        }
        return null;
    }

    @Override
    public  int pegarId() {
        return this.pegarTodosPedidos().size() + 1;
    }
}
