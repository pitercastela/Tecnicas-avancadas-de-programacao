package cleancode;

public class ClienteFactory {

    public static Cliente criar(int tipoCliente, String nomeCliente) {
        Cliente novoCliente;

        if (tipoCliente == 2) {
            novoCliente = new ClientePremium();
        } else if (tipoCliente == 3) {
            novoCliente = new ClienteVip();
        } else {
            novoCliente = new ClienteComum();
        }

        novoCliente.nome = nomeCliente;
        novoCliente.email = gerarEmail(nomeCliente);

        return novoCliente;
    }

    private static String gerarEmail(String nome) {
        return nome + "@email.com";
    }
} // Open/Closed Principle (OCP);