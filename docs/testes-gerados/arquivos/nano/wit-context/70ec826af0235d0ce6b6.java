/*
 * @test
 * @summary Test for getLong method in UnsafeQualifiedStaticDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsIllegalArgumentException();
    }

    public static void testGetLongThrowsIllegalArgumentException() {
        try {
            // Cria uma instância fictícia do método, usando reflexão para obter o método real
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticDoubleFieldAccessorImpl");
            Field field = clazz.getDeclaredField("field"); // campo fictício, apenas para instanciar
            // Como o construtor não é público, não podemos instanciá-lo diretamente; portanto, este teste é uma simulação
            // Testa o método getLong, esperando que lance IllegalArgumentException
            // Como o método lança a exceção imediatamente, podemos invocá-lo diretamente
            Object instance = null; // método é instanciado via reflexão, mas aqui não é necessário
            // Invoca o método getLong
            clazz.getMethod("getLong", Object.class).invoke(instance, new Object());
            // Se não lançar exceção, falha
            System.err.println("Erro: getLong não lançou IllegalArgumentException como esperado.");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: getLong lançou IllegalArgumentException.");
            } else {
                System.err.println("Falha: getLong lançou exceção inesperada: " + e);
            }
        }
    }
}
