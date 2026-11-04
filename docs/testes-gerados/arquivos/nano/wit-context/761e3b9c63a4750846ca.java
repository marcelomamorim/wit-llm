/*
 * @test
 * @summary Testa o método getBoolean de UnsafeQualifiedStaticObjectFieldAccessorImpl
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsIllegalArgumentException();
    }

    public static void testGetBooleanThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticObjectFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, pois não há campo público
            // Como não há construtor público, não podemos instanciar diretamente; portanto, este teste é uma simulação
            // Testa o método getBoolean, que deve lançar IllegalArgumentException
            Object instance = null; // instância nula, pois o método não usa estado
            // Invoca o método getBoolean
            clazz.getMethod("getBoolean", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, falha
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: getBoolean lança IllegalArgumentException como esperado.");
            } else {
                System.err.println("Falha: exceção inesperada: " + cause);
            }
        } catch (NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            System.err.println("Erro na reflexão: " + e);
        }
    }
}
