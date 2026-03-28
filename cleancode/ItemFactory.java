package cleancode;

public class ItemFactory {

    public static Item criar(String nome, double preco, int quantidade) {
        Item item = new Item();

        item.nome = (nome == null || nome.isEmpty()) ? "Item sem nome" : nome;
        item.preco = (preco < 0) ? 0 : preco;
        item.qtd = (quantidade <= 0) ? 1 : quantidade;

        return item;
    }
}// Open/Closed Principle (OCP);