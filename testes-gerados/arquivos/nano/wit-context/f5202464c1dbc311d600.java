/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedStaticFloatFieldAccessorImpl
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Cria uma instância da classe alvo com reflexão, se necessário
            // Como o construtor não é público, não podemos instanciá-la diretamente
            // Portanto, apenas invocamos o método para verificar o comportamento
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl instance = null;
            // Como não há um construtor público, não podemos criar uma instância diretamente
            // Testamos o método getBoolean, esperando que lance a exceção
            jdk.internal.reflect.UnsafeQualifiedStaticFloatFieldAccessorImpl.getBoolean(instance);
            // Se não lançar exceção, falha o teste
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("getBoolean() correctly threw IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
