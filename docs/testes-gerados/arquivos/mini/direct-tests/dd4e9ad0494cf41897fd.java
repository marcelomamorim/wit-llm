/*
 * @test
 * @summary Test java.lang.invoke.MethodType add(T elem) method behavior
 * @run main MethodTypeWitupTest
 */

public class MethodTypeWitupTest {
    public static void main(String[] args) throws Throwable {
        testAddNullThrowsNPE();
        testAddReturnsSameInstance();
    }

    static void testAddNullThrowsNPE() {
        java.lang.invoke.MethodType mt = java.lang.invoke.MethodType.methodType(void.class);
        try {
            mt.add(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testAddReturnsSameInstance() {
        java.lang.invoke.MethodType mt = java.lang.invoke.MethodType.methodType(void.class);
        String s = "testString";
        String result1 = mt.add(s);
        if (result1 == null) {
            throw new Error("Returned value should not be null");
        }
        String result2 = mt.add(s);
        if (result2 == null) {
            throw new Error("Returned value should not be null");
        }
        if (result1 != result2) {
            throw new Error("Expected same instance to be returned on repeated add calls");
        }
    }
}
