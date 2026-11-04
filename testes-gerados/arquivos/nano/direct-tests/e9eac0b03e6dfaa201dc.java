/* @test    @summary Test the update(byte[] input) method of javax.crypto.Mac for normal and exceptional behavior    @run main MacWitupTest */

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithNullInput();
        testUpdateWithValidInput();
        testUpdateWithoutInitialization();
    }

    static void testUpdateWithNullInput() throws Exception {
        Mac mac = createMacInstance();
        try {
            mac.update((byte[]) null);
        } catch (IllegalStateException e) {
            System.out.println("Passed null input test");
            return;
        }
        throw new AssertionError("Expected IllegalStateException for null input before init");
    }

    static void testUpdateWithValidInput() throws Exception {
        Mac mac = createMacInstance();
        byte[] keyBytes = "testkey".getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        mac.init(keySpec);
        byte[] input = "hello".getBytes();
        mac.update(input);
        byte[] result = mac.doFinal();
        if (result == null || result.length == 0) {
            throw new AssertionError("MAC result should not be null or empty");
        }
        System.out.println("Passed valid input test");
    }

    static void testUpdateWithoutInitialization() throws Exception {
        Mac mac = createMacInstance();
        byte[] input = "hello".getBytes();
        try {
            mac.update(input);
        } catch (IllegalStateException e) {
            System.out.println("Passed update without init test");
            return;
        }
        throw new AssertionError("Expected IllegalStateException for update without init");
    }

    static Mac createMacInstance() throws NoSuchAlgorithmException {
        return Mac.getInstance("HmacSHA256");
    }
}
