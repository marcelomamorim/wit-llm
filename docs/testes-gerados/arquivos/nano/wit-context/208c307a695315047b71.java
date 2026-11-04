/* @test    @summary Test for Checks.requireQualifiedClassName handling null input    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireQualifiedClassNameNull();
        testRequireQualifiedClassNameValid();
        testRequireQualifiedClassNameInvalid();
    }

    public static void testRequireQualifiedClassNameNull() {
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", null);
            System.out.println("FAIL: Expected IllegalArgumentException for null name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null name");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testRequireQualifiedClassNameValid() {
        String validName = "com.example.MyClass";
        try {
            String result = jdk.internal.module.Checks.requireQualifiedClassName("test", validName);
            if (result.equals(validName)) {
                System.out.println("PASS: Valid qualified name accepted");
            } else {
                System.out.println("FAIL: Valid qualified name not returned as expected");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid name: " + t);
        }
    }

    public static void testRequireQualifiedClassNameInvalid() {
        String invalidName = "com.example";
        try {
            jdk.internal.module.Checks.requireQualifiedClassName("test", invalidName);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid qualified name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for invalid qualified name");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
