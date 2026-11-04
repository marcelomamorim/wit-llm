/*
 * @test
 * @summary Testa o método getByte de UnsafeCharacterFieldAccessorImpl para comportamentos observáveis.
 * @run main UnsafeCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeCharacterFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetByteWithNullObject();
        testGetByteWithInvalidObject();
    }

    private static void testGetByteWithNullObject() {
        try {
            // Como o método não usa o estado interno, apenas invoca com null para verificar comportamento.
            jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = createAccessor();
            accessor.getByte(null);
            System.out.println("testGetByteWithNullObject: FAILED (não lançou)");
        } catch (NullPointerException e) {
            System.out.println("testGetByteWithNullObject: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetByteWithNullObject: FAILED (exceção inesperada: " + t + ")");
        }
    }

    private static void testGetByteWithInvalidObject() {
        try {
            jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl accessor = createAccessor();
            Object invalidObject = new Object();
            accessor.getByte(invalidObject);
            System.out.println("testGetByteWithInvalidObject: FAILED (não lançou)");
        } catch (IllegalArgumentException e) {
            System.out.println("testGetByteWithInvalidObject: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetByteWithInvalidObject: FAILED (exceção inesperada: " + t + ")");
        }
    }

    private static jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl createAccessor() throws Exception {
        // Cria um campo fictício para passar ao construtor, mesmo que não seja usado.
        Field dummyField = String.class.getDeclaredField("value");
        dummyField.setAccessible(true);
        return new jdk.internal.reflect.UnsafeCharacterFieldAccessorImpl(dummyField);
    }
}
