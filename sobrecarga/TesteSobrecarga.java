package sobrecarga;
import java.util.Scanner;

public final class TesteSobrecarga {
    private TesteSobrecarga() {
    }

    /**
     * metodo da função main.
     * @param args args
     */
    public static void main(final String[] args) {
        Calculadora calc = new Calculadora();
        Scanner tec = new Scanner(System.in);

        System.out.println(calc.somar(tec.nextInt(), tec.nextInt()));
        int a = tec.nextInt();
        int b = tec.nextInt();
        int c = tec.nextInt();
        System.out.println(calc.somar(a, b, c));
        System.out.println(calc.somar(tec.nextDouble(), tec.nextDouble()));
    }
}
