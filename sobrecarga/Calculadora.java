package sobrecarga;

public class Calculadora {
    /**
     *
     * @param a valor 1 da soma
     * @param  b valor 2 da soma
     * @return Resultado da soma
     */
    public int somar(final int a, final int b) {
        return a + b;
    }

    /**
     * @param a valor 1 da soma
     * @param b valor 2 da soma
     * @param c valor 3 da soma
     * @return Resultado da soma
     */
    public int somar(final int a, final int b, final int c) {
        return a + b + c;
    }

    /**
     * @param a valor 1 da soma
     * @param b valor 2 da soma
     * @return Resultado da soma
     */
    public double somar(final double a, final double b) {
        return a + b;
    }
}
