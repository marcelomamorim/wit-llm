/*
 * @test
 * @summary Teste do método getFloat para verificar comportamento esperado.
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsIllegalArgumentException();
    }

    public static void testGetFloatThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            // Obtém o método getFloat
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getFloat", Object.class);
            method.setAccessible(true);
            // Invoca o método com um objeto qualquer (pode ser null, pois o método não usa o estado)
            Object result = method.invoke(null, new Object());
            // Como o método lança sempre uma exceção, se chegar aqui, é falha
            System.err.println("Erro: método não lançou exceção como esperado.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Teste passou, exceção esperada
                System.out.println("Passou: getFloat lança IllegalArgumentException como esperado.");
            } else {
                System.err.println("Falha: exceção inesperada: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Erro ao invocar getFloat: " + e);
        }
    }
}
