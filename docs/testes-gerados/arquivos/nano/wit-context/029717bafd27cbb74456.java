/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedObjectFieldAccessorImpl
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteThrowsIllegalArgumentException();
    }

    private static void testGetByteThrowsIllegalArgumentException() throws Exception {
        // Cria um campo fictício para passar como argumento
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Caso não exista, cria um campo fictício usando reflexão
            // Como não podemos criar um Field real, este teste é uma simulação
            // e não será executado na prática, mas serve como exemplo.
        }
        // Instancia a classe alvo com o campo fictício
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor = 
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(dummyField, false);
        // Invoca getByte com um objeto que não é compatível
        try {
            accessor.getByte(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Teste passou, exceção esperada
        }
    }
}
