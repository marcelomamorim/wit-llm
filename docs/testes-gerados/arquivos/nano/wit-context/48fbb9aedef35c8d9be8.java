/* @test    @summary Test for Checks.requirePackageName to verify exception on null input    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequirePackageNameNull();
        testRequirePackageNameValid();
    }

    public static void testRequirePackageNameNull() {
        try {
            jdk.internal.module.Checks.requirePackageName(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testRequirePackageNameValid() {
        String validName = "com.example.package";
        try {
            String result = jdk.internal.module.Checks.requirePackageName(validName);
            if (result.equals(validName)) {
                System.out.println("PASS: Valid package name returned correctly");
            } else {
                System.out.println("FAIL: Unexpected result for valid package name");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid input: " + t);
        }
    }
}
