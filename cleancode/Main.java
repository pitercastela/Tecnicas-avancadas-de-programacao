package cleancode;

public class Main {
    public static void main(String[] args) {
        Db database = new Db();
        RelatorioPedidos relatorioPedidos = new RelatorioPedidos();
        Sistema sistema = new Sistema(database, relatorioPedidos);
        sistema.rodarPrograma();
    }
}
