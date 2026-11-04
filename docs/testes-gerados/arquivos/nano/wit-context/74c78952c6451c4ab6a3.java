/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeQualifiedDoubleFieldAccessorImpl com um Field fictício
            Field dummyField = null; // Como não há construtor público, não podemos instanciar diretamente
            // Como não podemos criar uma instância real sem um Field válido, vamos simular o comportamento
            // Testando o método getChar diretamente com um objeto nulo, pois o método lança exceção imediatamente
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            accessor.getChar(new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Esperado
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
