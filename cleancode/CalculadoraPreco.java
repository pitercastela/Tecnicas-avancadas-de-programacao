package cleancode;

public class CalculadoraPreco {
    private static double calcularFrete(double total) {
        if (total < 100) {
            total = total + 25;
        } else if (total >= 100 && total < 300) {
            total = total + 15;
        }

        return total;
    }

    private static double calcularDesconto(double total, Cliente cliente) {
        return cliente.pegarDesconto(total);
    }

    public static double pegarTotal(Pedido pedido, Cliente cliente) {
        double total = pedido.calcularPrecoPedido();

        total = calcularDesconto(total, cliente);
        total = calcularFrete(total);
        return total;
    }
} //1. Single Responsibility Principle (SRP)
