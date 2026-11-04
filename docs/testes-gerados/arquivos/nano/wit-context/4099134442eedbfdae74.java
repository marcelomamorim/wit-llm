/* @test    @summary Testa o método getInt para verificar comportamento esperado e exceções.    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithNullObject();
        testGetIntWithInvalidObject();
    }

    private static void testGetIntWithNullObject() {
        try {
            Field dummyField = null; // campo fictício apenas para instanciar
            UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(dummyField);
            accessor.getInt(null);
            System.err.println("Expected IllegalArgumentException for null object, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (Throwable t) {
            System.err.println("Unexpected exception for null object: " + t);
        }
    }

    private static void testGetIntWithInvalidObject() {
        try {
            Field dummyField = null; // campo fictício apenas para instanciar
            UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(dummyField);
            Object invalidObj = new Object();
            accessor.getInt(invalidObj);
            System.err.println("Expected IllegalArgumentException for invalid object, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // esperado
        } catch (Throwable t) {
            System.err.println("Unexpected exception for invalid object: " + t);
        }
    }
}
