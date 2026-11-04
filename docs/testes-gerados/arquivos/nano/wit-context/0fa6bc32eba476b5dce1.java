/* @test    @summary Testa o método getFloat para verificar comportamento esperado e exceções.    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithNullObject();
        testGetFloatWithInvalidObject();
    }

    static void testGetFloatWithNullObject() {
        try {
            Field dummyField = null; // campo fictício, não será usado
            // Instanciação fictícia, pois o método não depende de estado interno
            // Como o método é estático, podemos invocá-lo diretamente
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
            accessor.getFloat(null);
            System.out.println("FAIL: Expected NullPointerException");
        } catch (NullPointerException npe) {
            System.out.println("PASS: NullPointerException caught as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testGetFloatWithInvalidObject() {
        try {
            Field dummyField = null; // campo fictício
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(dummyField);
            Object invalidObj = new Object();
            accessor.getFloat(invalidObj);
            System.out.println("FAIL: Expected IllegalArgumentException");
        } catch (IllegalArgumentException iae) {
            System.out.println("PASS: IllegalArgumentException caught as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
