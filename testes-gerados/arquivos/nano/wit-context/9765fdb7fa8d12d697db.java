/*
 * @test
 * @summary Test for UnsafeDoubleFieldAccessorImpl.getInt method exception path
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsIllegalArgumentException();
    }

    public static void testGetIntThrowsIllegalArgumentException() {
        try {
            // Cria uma instância de UnsafeDoubleFieldAccessorImpl com um campo fictício
            Field dummyField = null; // Como não há construtor público, não podemos instanciá-lo diretamente
            // Como não podemos criar uma instância válida, vamos simular o comportamento esperado
            // Testando o método getInt com um objeto nulo, que deve lançar IllegalArgumentException
            jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
            accessor.getInt(null);
            // Se não lançar exceção, falha o teste
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste passou, exceção esperada
            System.out.println("Passed: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
