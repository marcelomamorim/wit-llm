/* @test    @summary Test for java.security.Security.setProperty method to verify NullPointerException scenarios    @run main SecurityWitupTest */

import java.security.Security;

public class SecurityWitupTest {

    public static void main(String[] args) {
        testSetProperty_NullSecurityAndNullDatum();
        testSetProperty_NullSecurityNonNullDatum();
        testSetProperty_NonNullSecurityNullDatum();
        testSetProperty_NonNullSecurityNonNullDatum();
    }

    private static void testSetProperty_NullSecurityAndNullDatum() {
        String key = "testKey1";
        String datum = null;
        try {
            Security.setProperty(key, datum);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null security and null datum");
            return;
        }
        System.out.println("Failed: No exception thrown for null security and null datum");
    }

    private static void testSetProperty_NullSecurityNonNullDatum() {
        String key = "testKey2";
        String datum = "value";
        try {
            Security.setProperty(key, datum);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for null security and non-null datum");
            return;
        }
        System.out.println("Failed: No exception thrown for null security and non-null datum");
    }

    private static void testSetProperty_NonNullSecurityNullDatum() {
        String key = "testKey3";
        String datum = null;
        try {
            Security.setProperty(key, datum);
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException for non-null security and null datum");
            return;
        }
        System.out.println("Failed: No exception thrown for non-null security and null datum");
    }

    private static void testSetProperty_NonNullSecurityNonNullDatum() {
        String key = "testKey4";
        String datum = "value";
        try {
            Security.setProperty(key, datum);
            System.out.println("setProperty executed successfully for non-null security and non-null datum");
        } catch (NullPointerException e) {
            System.out.println("Failed: Unexpected NullPointerException for non-null security and non-null datum");
        }
    }
}
