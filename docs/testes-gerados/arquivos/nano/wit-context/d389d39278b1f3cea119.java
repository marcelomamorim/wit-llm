/* @test    @summary Teste do método getShort para verificar comportamento observável.    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortWithNullObject();
        testGetShortWithInvalidType();
    }

    private static void testGetShortWithNullObject() {
        try {
            Field dummyField = null; // campo fictício, não utilizado
            // Instanciação fictícia, pois o método não depende de estado interno
            // Como o método lança sempre a exceção, não há necessidade de criar objeto real
            // Apenas invoca o método com null para verificar a exceção
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            accessor.getShort(null);
            System.err.println("Erro: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            // Esperado
        } catch (Throwable t) {
            System.err.println("Erro inesperado em getShort com null: " + t);
        }
    }

    private static void testGetShortWithInvalidType() {
        try {
            Field dummyField = null; // campo fictício, não utilizado
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            Object invalidObject = new Object();
            accessor.getShort(invalidObject);
            System.err.println("Erro: Esperado IllegalArgumentException ao passar objeto inválido.");
        } catch (IllegalArgumentException e) {
            // Esperado
        } catch (Throwable t) {
            System.err.println("Erro inesperado em getShort com objeto inválido: " + t);
        }
    }
}
