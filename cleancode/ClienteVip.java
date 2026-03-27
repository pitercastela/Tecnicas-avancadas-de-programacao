package cleancode;

public class ClienteVip extends Cliente{

    @Override
    public String pegarTipoDesc(){
        return "Vip";
    }

    @Override
    public double pegarDesconto(double total){
        total = total - (total * 0.15);
        return total;
    }// 3. Liskov Substitution Principle (LSP)
}
