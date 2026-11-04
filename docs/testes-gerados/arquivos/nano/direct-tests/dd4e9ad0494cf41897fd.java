/*
 * @test
 * @summary Tests the add method of MethodType for correct behavior and exception handling.
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testAddWithValidElement();
        testAddWithNullElement();
    }

    static void testAddWithValidElement() {
        MethodType mt = MethodType.methodType(String.class, int.class);
        String element = "test";
        try {
            String result = mt.add(element);
            if (!result.equals(element)) {
                throw new AssertionError("Expected element to be returned by add, but got: " + result);
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception during add with valid element", t);
        }
    }

    static void testAddWithNullElement() {
        MethodType mt = MethodType.methodType(String.class, int.class);
        try {
            mt.add(null);
            throw new AssertionError("Expected NullPointerException when adding null element");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type when adding null element", t);
        }
    }
}
