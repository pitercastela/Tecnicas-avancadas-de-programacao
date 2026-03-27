package cleancode;

public class ClientePremium extends Cliente{

    @Override
    public String pegarTipoDesc(){
        return "Premium";
    }

    @Override
    public double pegarDesconto(double total){
        if (total > 200) {
            total = total - (total * 0.10);
        } else {
            total = total - (total * 0.03);
        }
        return total;
    }// 3. Liskov Substitution Principle (LSP)
}
