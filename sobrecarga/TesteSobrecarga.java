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
        Scanner teclado = new Scanner(System.in);
        int a = teclado.nextInt();
        int b = teclado.nextInt();
        int c = teclado.nextInt();
        System.out.println(calc.somar(teclado.nextInt(), teclado.nextInt()));
        System.out.println(calc.somar(a, b, c));
        System.out.println(calc.somar(teclado.nextInt(), teclado.nextInt()));
    }
}
