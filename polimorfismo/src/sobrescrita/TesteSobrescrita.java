package sobrescrita;

public class TesteSobrescrita {
    public static void main(String[] args) {

        Cachorro cachorro = new Cachorro();
        Animal gato = new Gato();
        Animal John = new Animal();

        cachorro.emitir_som("2");
        gato.emitir_som();
        John.emitir_som();
    }
}
