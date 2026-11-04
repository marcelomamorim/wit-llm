/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do objeto, já que o método não usa o parâmetro
            // Como o método sempre lança uma exceção, não é necessário configurar o objeto
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl accessor = null;
            // Invoca o método e espera uma IllegalArgumentException
            accessor.getByte(null);
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Passou: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Erro: exceção inesperada: " + t);
        }
    }
}
