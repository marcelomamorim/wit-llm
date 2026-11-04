/*
 * @test
 * @summary Test for MethodType.get(T) method, focusing on null input handling.
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testGetWithNull();
        testGetWithNonNull();
    }

    private static void testGetWithNull() {
        try {
            MethodType methodType = MethodType.methodType(Object.class);
            methodType.get(null);
            System.out.println("FAIL: Expected NullPointerException for null argument");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null argument");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    private static void testGetWithNonNull() {
        try {
            MethodType methodType = MethodType.methodType(String.class);
            String result = methodType.get("test");
            if (result == null) {
                System.out.println("PASS: get() returned null for missing key as expected");
            } else {
                System.out.println("FAIL: get() returned unexpected non-null value: " + result);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during get() with non-null argument: " + t);
        }
    }
}
