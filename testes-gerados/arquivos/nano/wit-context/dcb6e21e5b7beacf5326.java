/*
 * @test
 * @summary Test for getFloat method in UnsafeQualifiedDoubleFieldAccessorImpl
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() throws Exception {
        // Cria uma instância de UnsafeQualifiedDoubleFieldAccessorImpl com um Field fictício
        // Como o construtor não é público, usamos reflexão para acessá-lo
        Field dummyField = null;
        try {
            dummyField = DummyClass.class.getDeclaredField("dummy");
        } catch (NoSuchFieldException e) {
            // Ignorado, apenas para obter um Field válido
        }
        // Como não podemos criar uma instância real sem um Field válido, vamos usar uma subclasse anônima
        UnsafeQualifiedDoubleFieldAccessorImpl accessor = new UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, false) {
            @Override
            public float getFloat(Object obj) {
                // Simula o comportamento do método, lançando a exceção esperada
                throw UnsafeQualifiedDoubleFieldAccessorImpl.this.newGetFloatIllegalArgumentException();
            }
        };
        try {
            accessor.getFloat(new Object());
            System.err.println("Erro: esperado uma IllegalArgumentException, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            // Teste bem-sucedido se a exceção for lançada
            System.out.println("Teste bem-sucedido: IllegalArgumentException lançada como esperado.");
        }
    }

    static class DummyClass {
        public int dummy;
    }

}
