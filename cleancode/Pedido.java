package cleancode;

import java.util.List;

public class Pedido {
    public int id;
    public Cliente cliente;
    public List<Item> itens;
    public double total;
    public String status;

    public double calculaPrecoDoPedido() {
        double valorTotal = 0;
        for (Item iten : itens) {
            valorTotal += iten.calculaPrecoTotal();
        }
        return valorTotal;
    }

    public void exibePedido() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.nome);
        for (Item iten : itens) {
            System.out.println(iten.nome);
        }
        System.out.println(total);
    }
}