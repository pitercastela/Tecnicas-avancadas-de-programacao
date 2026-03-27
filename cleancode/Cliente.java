package cleancode;

public abstract class Cliente {
    public int id;
    public String nome;
    public String email;

    abstract public String pegarTipoDesc();
    abstract public double pegarDesconto(double total);


} //2. Open/Closed Principle (OCP) (dívidido em múltiplos clientes)

