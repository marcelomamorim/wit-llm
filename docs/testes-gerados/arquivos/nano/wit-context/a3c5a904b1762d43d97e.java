/*
 * @test
 * @summary Test for Checks.requireServiceTypeName to verify exception on null input
 * @run main ChecksWitupTest
 */

import jdk.internal.module.Checks;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireServiceTypeNameNull();
        testRequireServiceTypeNameValid();
    }

    public static void testRequireServiceTypeNameNull() {
        try {
            Checks.requireServiceTypeName(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testRequireServiceTypeNameValid() {
        String validName = "com.example.Service";
        try {
            String result = Checks.requireServiceTypeName(validName);
            if (result.equals(validName)) {
                System.out.println("PASS: Valid name returned correctly");
            } else {
                System.out.println("FAIL: Valid name not returned as expected");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception for valid input: " + t);
        }
    }
}
