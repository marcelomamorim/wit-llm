/* @test    @summary Teste do método getShort para verificar comportamento esperado e exceções.    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetShortWithNullObject();
        testGetShortWithInvalidObject();
    }

    private static void testGetShortWithNullObject() {
        try {
            Field dummyField = null; // campo fictício, não utilizado na chamada
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
            accessor.getShort(null);
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exceção capturada como esperado para null.");
        } catch (Throwable t) {
            System.out.println("FAIL: Exceção inesperada: " + t);
        }
    }

    private static void testGetShortWithInvalidObject() {
        try {
            Field dummyField = null; // campo fictício, não utilizado na chamada
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
            Object invalidObject = new Object();
            accessor.getShort(invalidObject);
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar objeto inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Exceção capturada como esperado para objeto inválido.");
        } catch (Throwable t) {
            System.out.println("FAIL: Exceção inesperada: " + t);
        }
    }
}
