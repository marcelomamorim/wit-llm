/* @test    @summary Test for Checks.requireServiceProviderName to verify exception on null input    @run main ChecksWitupTest */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireServiceProviderNameNull();
        testRequireServiceProviderNameValid();
    }

    public static void testRequireServiceProviderNameNull() {
        try {
            jdk.internal.module.Checks.requireServiceProviderName(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null input");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testRequireServiceProviderNameValid() {
        String validName = "com.example.ServiceProvider";
        try {
            String result = jdk.internal.module.Checks.requireServiceProviderName(validName);
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
