/*
 * @test
 * @summary Witup-generated tests for javax.crypto.Mac.update(byte[]) on an uninitialized Mac
 * @run main MacWitupTest
 */
import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;

public class MacWitupTest {

    public static void main(String[] args) throws Exception {
        testUpdateWithoutInitThrowsIllegalState();
        testUpdateNullWithoutInitThrowsIllegalState();
    }

    static Mac newMac() throws NoSuchAlgorithmException {
        return Mac.getInstance("HmacSHA256");
    }

    static void testUpdateWithoutInitThrowsIllegalState() throws Exception {
        Mac mac = newMac();
        try {
            mac.update(new byte[] {1, 2, 3});
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException from update on uninitialized Mac");
    }

    static void testUpdateNullWithoutInitThrowsIllegalState() throws Exception {
        Mac mac = newMac();
        try {
            mac.update((byte[]) null);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException from update(null) on uninitialized Mac");
    }
}
