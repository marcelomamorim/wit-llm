/*
 * @test
 * @summary Test for getDouble method in UnsafeQualifiedStaticObjectFieldAccessorImpl
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsIllegalArgumentException();
    }

    public static void testGetDoubleThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo com reflexão, se necessário
            // Como o método não depende de estado interno, podemos invocá-lo diretamente
            // Como o método lança exceção imediatamente, apenas chamá-lo é suficiente
            jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl instance = new jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl(null, false);
            // Invoca o método com um objeto qualquer, por exemplo null
            instance.getDouble(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido: a exceção esperada foi lançada
        } catch (Throwable t) {
            // Se outra exceção for lançada, o teste falha
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
