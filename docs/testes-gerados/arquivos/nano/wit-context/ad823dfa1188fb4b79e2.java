/*
 * @test
 * @summary Teste do método getFloat de UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsIllegalArgumentException();
    }

    private static void testGetFloatThrowsIllegalArgumentException() {
        // Cria um objeto fictício para passar como parâmetro
        Object obj = new Object();
        // Cria uma instância de UnsafeQualifiedObjectFieldAccessorImpl com um Field nulo
        // Como não há construtor público, não podemos instanciar diretamente; portanto, este teste
        // é uma simulação para verificar o comportamento esperado ao chamar getFloat.
        // Como o método getFloat sempre lança a exceção, podemos invocá-lo diretamente.
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = null;
        try {
            // Como não podemos criar uma instância real sem um Field válido, simulamos a chamada
            // e verificamos se a exceção é lançada.
            // Para isso, criamos uma subclasse anônima que sobrescreve o método para fins de teste.
            accessor = new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(null, false) {
                @Override
                public float getFloat(Object obj) {
                    return super.getFloat(obj);
                }
            };
            accessor.getFloat(obj);
            // Se não lançar exceção, o teste falha
            System.err.println("Falha: esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Sucesso: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
        }
    }
}
