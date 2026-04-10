package sobrescrita;

public class Cachorro extends Animal{

    @Override
    void emitir_som(){
        System.out.println("Au Au");
    }
    void emitir_som(String a){

    }
}
