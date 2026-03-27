package cleancode;

public class ClienteComum extends Cliente{

    @Override
    public String pegarTipoDesc(){
        return "Comum";
    }

    @Override
    public double pegarDesconto(double total){
        if (total > 300) {
            total = total - (total * 0.05);
        }
        return total;
    } //3. Liskov Substitution Principle (LSP)

}
