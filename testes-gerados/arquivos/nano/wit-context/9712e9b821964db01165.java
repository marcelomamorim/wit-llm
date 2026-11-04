/*
 * @test
 * @summary Test for getLong method in UnsafeQualifiedStaticBooleanFieldAccessorImpl.
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsIllegalArgumentException();
    }

    public static void testGetLongThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl");
            // Obtém o método getLong
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getLong", Object.class);
            method.setAccessible(true);
            // Invoca o método com um objeto qualquer (pode ser null, pois o método não usa o parâmetro)
            Object result = method.invoke(null, (Object) null);
            // Como o método lança exceção, não deve chegar aqui
            System.err.println("Erro: método não lançou exceção como esperado.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passou, exceção esperada
                System.out.println("Passou: getLong lançou IllegalArgumentException como esperado.");
            } else {
                System.err.println("Falha: getLong lançou uma exceção inesperada: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Erro ao invocar getLong: " + e);
        }
    }
}
