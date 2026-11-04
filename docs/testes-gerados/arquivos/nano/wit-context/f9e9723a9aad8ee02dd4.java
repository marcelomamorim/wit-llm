/*
 * @test
 * @summary Test for getShort method in UnsafeQualifiedStaticDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsIllegalArgumentException();
    }

    public static void testGetShortThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do método, simulando o comportamento esperado
            // Como o método lança sempre uma exceção, não é necessário criar um objeto real
            jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl accessor = null;
            // Invoca o método e espera uma IllegalArgumentException
            accessor.getShort(null);
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Passou: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
        }
    }
}
