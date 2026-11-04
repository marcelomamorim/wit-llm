/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedStaticDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do método, pois não há construtor público;
            // neste teste, apenas invocamos o método diretamente para verificar o comportamento.
            // Como o método lança sempre uma exceção, não é necessário criar um objeto real.
            jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl instance = null;
            // Invoca o método, esperando uma IllegalArgumentException
            instance.getChar(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t);
        }
    }
}
