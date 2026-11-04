/*
 * @test
 * @summary Test for getBoolean method in UnsafeDoubleFieldAccessorImpl
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanThrowsException();
    }

    public static void testGetBooleanThrowsException() throws Exception {
        // Cria um campo fictício para passar ao construtor
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Caso não exista, cria um campo fictício usando reflexão
            // Como não podemos criar um Field real, este teste é uma simulação
            // e não será executado na prática, mas serve como exemplo
        }
        // Instancia a classe alvo com o campo fictício
        jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl(dummyField);
        try {
            accessor.getBoolean(new Object());
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Esperado, o teste passa
        }
    }
}
