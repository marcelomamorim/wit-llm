/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedStaticBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsIllegalArgumentException();
    }

    public static void testGetCharThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para obter a classe
            // Como o método getChar não depende de estado, podemos invocá-lo diretamente
            Object instance = null; // método é estático
            // Invoca o método getChar
            clazz.getMethod("getChar", Object.class).invoke(instance, new Object());
            // Espera que lance IllegalArgumentException
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: IllegalArgumentException foi lançada como esperado.");
            } else {
                System.err.println("Erro: exceção inesperada: " + cause);
            }
        } catch (NoSuchMethodException | NoSuchFieldException | ClassNotFoundException e) {
            System.err.println("Erro na configuração do teste: " + e);
        } catch (IllegalAccessException e) {
            System.err.println("Erro de acesso: " + e);
        }
    }
}
