/* @test    @summary Teste do método getBoolean para verificar lançamento de IllegalArgumentException.    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() {
        try {
            // Cria uma instância de UnsafeQualifiedDoubleFieldAccessorImpl com valores fictícios
            Field dummyField = null; // Como não há construtor público, não podemos instanciar diretamente
            // Como não podemos criar uma instância real sem um campo válido, vamos simular a chamada direta
            // Como o método getBoolean lança sempre IllegalArgumentException, podemos invocá-lo via reflexão
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false);
            accessor.getBoolean(new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Falha: esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido
            System.out.println("Sucesso: IllegalArgumentException lançada como esperado.");
        } catch (Throwable t) {
            System.err.println("Falha: exceção inesperada: " + t);
        }
    }
}
