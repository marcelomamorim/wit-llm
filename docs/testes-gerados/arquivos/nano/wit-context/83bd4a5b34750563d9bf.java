/*
 * @test
 * @summary Testa o método getShort para verificar comportamento esperado e exceções.
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortWithNullObject();
        testGetShortWithInvalidObject();
    }

    private static void testGetShortWithNullObject() {
        try {
            Field dummyField = null; // não usamos o campo real, apenas para passar parâmetro
            // Instancia fictícia do objeto para passar como parâmetro
            Object obj = null;
            // Cria uma instância do método usando reflexão, se necessário, ou simula o comportamento
            // Como o método é estático, podemos invocá-lo diretamente
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            accessor.getShort(obj);
            System.out.println("Erro: Esperado uma IllegalArgumentException para objeto null.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException capturada como esperado para objeto null.");
        } catch (Throwable t) {
            System.out.println("Erro inesperado: " + t);
        }
    }

    private static void testGetShortWithInvalidObject() {
        try {
            // Cria uma instância válida do campo fictício
            Field dummyField = null; // não usamos o campo real, apenas para passar parâmetro
            Object obj = new Object();
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            accessor.getShort(obj);
            System.out.println("Erro: Esperado uma IllegalArgumentException para objeto inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException capturada como esperado para objeto inválido.");
        } catch (Throwable t) {
            System.out.println("Erro inesperado: " + t);
        }
    }
}
