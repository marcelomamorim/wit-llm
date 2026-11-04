/*
 * @test
 * @summary Testa o método getShort de UnsafeStaticObjectFieldAccessorImpl para comportamentos observáveis.
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortWithNullObject();
        testGetShortWithInvalidType();
    }

    private static void testGetShortWithNullObject() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field dummyField = DummyClass.class.getDeclaredField("value");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            // Invoca getShort com objeto null, espera-se que lance IllegalArgumentException
            accessor.getShort(null);
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar null.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getShort com null objeto lançou IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: getShort com null objeto lançou exceção inesperada: " + t);
        }
    }

    private static void testGetShortWithInvalidType() {
        try {
            // Cria uma instância de UnsafeStaticObjectFieldAccessorImpl com um campo válido
            Field dummyField = DummyClass.class.getDeclaredField("value");
            dummyField.setAccessible(true);
            jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl(dummyField);
            // Invoca getShort com objeto de tipo incorreto, espera-se IllegalArgumentException
            accessor.getShort("string_instead_of_expected_type");
            System.out.println("FAIL: Esperado IllegalArgumentException ao passar tipo incorreto.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getShort com tipo incorreto lançou IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: getShort com tipo incorreto lançou exceção inesperada: " + t);
        }
    }

    static class DummyClass {
        public static Object value = 42;
    }
}
