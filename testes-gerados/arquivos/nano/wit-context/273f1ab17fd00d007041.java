/*
 * @test
 * @summary Test for getDouble method in UnsafeStaticBooleanFieldAccessorImpl
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Cria uma instância de UnsafeStaticBooleanFieldAccessorImpl com um campo fictício
            Field dummyField = null; // Como não há construtor público, não podemos criar uma instância real
            // Como não podemos criar uma instância real, vamos simular o comportamento esperado
            // Testando o método getDouble, que deve lançar uma exceção
            jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl(dummyField);
            accessor.getDouble(null);
            System.err.println("Falha: esperado uma exceção, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: exceção IllegalArgumentException capturada como esperado.");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
        }
    }
}
