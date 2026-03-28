package cleancode;

import java.util.List;

public class Pedido {
    public int id;
    public Cliente cliente;
    public List<Item> itens;
    public double total;
    public String status;

    public double calcularPrecoPedido() {
        double valorTotal = 0;
        for (Item iten : itens) {
            valorTotal += iten.calcularPrecoTotal();
        }
        return valorTotal;
    }

    public void exibirPedido() {
        System.out.println("id: " + id);
        System.out.println("cliente: " + cliente.nome);
        System.out.println("email: " + cliente.email);
        System.out.println("tipo: " + cliente.pegarTipoDesc());
        System.out.println("status: " + status);
        System.out.println("total: " + total);
        System.out.println("itens:");
        for (Item it : itens) {
            System.out.println(it.nome + " - " + it.qtd + " - " + it.preco);
        }
    }

    public boolean cancelar() {
        if ("CANCELADO".equals(this.status)) {
            return false; // Não foi possível cancelar porque já estava cancelado
        }
        this.status = "CANCELADO";
        return true; // Cancelado com sucesso
    }

    public boolean pedidoGrande () {
        return calcularPrecoPedido() > 500;
    }
}