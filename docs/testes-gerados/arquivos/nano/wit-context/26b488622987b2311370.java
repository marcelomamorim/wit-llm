/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedStaticLongFieldAccessorImpl
 * @run main UnsafeQualifiedStaticLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticLongFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para obter a classe
            // Como o método getBoolean lança exceção sempre, apenas invoca e verifica se lança
            Object instance = null; // método é estático
            clazz.getMethod("getBoolean", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
            System.exit(1);
        } catch (Throwable t) {
            Throwable cause = t.getCause() != null ? t.getCause() : t;
            if (cause instanceof IllegalArgumentException) {
                // Teste passou
                System.out.println("getBoolean lança IllegalArgumentException como esperado.");
            } else {
                System.err.println("Erro: exceção inesperada: " + cause);
                System.exit(1);
            }
        }
    }
}
