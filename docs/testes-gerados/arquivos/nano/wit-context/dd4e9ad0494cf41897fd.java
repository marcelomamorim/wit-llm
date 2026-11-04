/*
 * @test    @summary Test the add method of MethodType for null input to verify NullPointerException is thrown.
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testAddNullInput();
    }

    public static void testAddNullInput() {
        MethodType methodType = MethodType.methodType(Object.class, String.class);
        try {
            methodType.add(null);
            System.err.println("Failed: NullPointerException was not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
