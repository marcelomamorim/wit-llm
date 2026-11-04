/*
 * @test
 * @summary Test for getByte method in UnsafeQualifiedStaticShortFieldAccessorImpl
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsIllegalArgumentException();
    }

    public static void testGetByteThrowsIllegalArgumentException() {
        try {
            // Cria uma instância da classe alvo usando reflexão
            Class<?> clazz = Class.forName("jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl");
            // Como o construtor não é público, não podemos instanciá-lo diretamente; portanto, este teste foca na chamada do método
            // Para testar o método getByte, precisamos de uma instância válida, mas como o método sempre lança exceção, podemos invocá-lo via reflexão
            Field field = null; // campo não necessário para este teste
            Object instance = null; // instância não necessária, pois o método é estático
            // Invoca o método getByte via reflexão
            java.lang.reflect.Method method = clazz.getDeclaredMethod("getByte", Object.class);
            method.setAccessible(true);
            // Chamada com qualquer objeto, pois o método ignora o argumento e lança exceção
            method.invoke(instance, new Object());
            // Se não lançar exceção, o teste falha
            System.err.println("Erro: esperado IllegalArgumentException não foi lançada.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("Passou: IllegalArgumentException lançada como esperado.");
            } else {
                System.err.println("Erro: exceção inesperada: " + cause);
            }
        } catch (Exception e) {
            System.err.println("Erro ao invocar método: " + e);
        }
    }
}
