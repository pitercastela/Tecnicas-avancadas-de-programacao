package cleancode;

import java.util.List;

public class EstatisticasPedidos {
    public double somaTotal = 0;
    public int totalPedidos = 0;
    public int totalCancelados = 0;
    public int comuns = 0, premiums = 0, vips = 0;

    public EstatisticasPedidos(List<Pedido> pedidos) {
        this.totalPedidos = pedidos.size();
        for (Pedido p : pedidos) {
            somaTotal += p.total;
            if ("CANCELADO".equals(p.status)) totalCancelados++;

            // Usando polimorfismo/tipo em vez de Strings mágicas no if
            if (p.cliente instanceof ClienteVip) vips++;
            else if (p.cliente instanceof ClientePremium) premiums++;
            else comuns++;
        }
    }

    public double calcularMedia() {
        return totalPedidos > 0 ? somaTotal / totalPedidos : 0;
    }

    public String avaliarDesempenho() {
        if (somaTotal > 1000) return "Resultado muito bom";
        if (somaTotal > 500) return "Resultado ok";
        return "Resultado fraco";
    }
}