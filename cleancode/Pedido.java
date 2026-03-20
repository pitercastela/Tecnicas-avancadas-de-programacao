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
        for (int i = 0; i < itens.size(); i++) {
            valorTotal += itens.get(i).calculaPrecoTotal();
        }
        return valorTotal;
    }

    public void exibePedido() {
        System.out.println("Pedido " + id);
        System.out.println("Cliente " + cliente.nome);
        for (int i = 0; i < itens.size(); i++) {
            System.out.println(itens.get(i).nome);
        }
        System.out.println(total);
    }
}