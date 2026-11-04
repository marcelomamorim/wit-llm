/*
 * @test
 * @summary Teste do método getDouble para verificar comportamento com entrada inválida.
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleThrowsIllegalArgumentException();
    }

    public static void testGetDoubleThrowsIllegalArgumentException() throws Exception {
        // Preparar um objeto de teste
        Object obj = new Object();
        // Obter um campo qualquer (não utilizado na implementação, apenas para passar como parâmetro)
        Field field = Object.class.getDeclaredField("hashCode");
        // Criar uma instância de UnsafeQualifiedObjectFieldAccessorImpl
        jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl(field, false);
        // Invocar o método e verificar se lança IllegalArgumentException
        try {
            accessor.getDouble(obj);
            System.err.println("Erro: Esperado IllegalArgumentException não foi lançada.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido
            System.out.println("Passou: IllegalArgumentException lançada como esperado.");
        }
    }
}
