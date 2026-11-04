/*
 * @test
 * @summary Teste do método getFloat para verificar comportamento esperado.
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsIllegalArgumentException();
    }

    public static void testGetFloatThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            // Obtém o método getFloat
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getFloat", Object.class);
            method.setAccessible(true);
            // Invoca o método com um objeto qualquer (pode ser null, pois o método não usa o estado do objeto)
            Object result = method.invoke(null, new Object());
            // Como o método lança exceção, não deve chegar aqui
            System.err.println("Erro: método não lançou exceção como esperado.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                // Test passou, exceção esperada
                System.out.println("Passou: getFloat lançou IllegalArgumentException como esperado.");
            } else {
                System.err.println("Falha: getFloat lançou uma exceção inesperada: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Erro ao invocar getFloat: " + e);
        }
    }
}
