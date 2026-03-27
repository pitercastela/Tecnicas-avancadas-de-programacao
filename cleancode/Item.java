package cleancode;

public class Item {
    public String nome;
    public double preco;
    public int qtd;

    public double calcularPrecoTotal() {
        return preco * qtd;
    }
}