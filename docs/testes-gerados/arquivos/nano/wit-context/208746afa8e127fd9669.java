/*
 * @test
 * @summary Test for getShort method in UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Cria um objeto de UnsafeFloatFieldAccessorImpl com um campo fictício
            Field dummyField = null; // Como não há construtor público, não podemos instanciar diretamente
            // Como não há construtor público, não podemos criar uma instância válida, então testamos o método de forma isolada
            // Invoca o método getShort com null, que deve lançar a exceção
            jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(dummyField);
            accessor.getShort(null);
            System.err.println("Erro: esperado uma exceção, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
        }
    }
}
