/* @test    @summary Teste do método add de FixedList que lança UnsupportedOperationException    @run main FixedListWitupTest */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        FixedList<Object> list = new FixedList<Object>(5);
        try {
            list.add(new Object());
            System.err.println("Erro: esperado UnsupportedOperationException") ;
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // esperado
            System.out.println("Passou: UnsupportedOperationException lançada") ;
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
            System.exit(1);
        }
    }
}
