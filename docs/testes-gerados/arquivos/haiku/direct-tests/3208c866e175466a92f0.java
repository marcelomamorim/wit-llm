/*
 * @test
 * @summary Verifies IPAddressName(byte[]) accepts IPv4/IPv6 address and mask lengths and rejects others
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws Exception {
        int[] validLengths = {4, 8, 16, 32};
        for (int len : validLengths) {
            new IPAddressName(new byte[len]);
        }

        int[] invalidLengths = {0, 1, 2, 3, 5, 6, 7, 9, 12, 15, 17, 24, 31, 33, 64};
        for (int len : invalidLengths) {
            checkInvalid(len);
        }

        checkNull();
    }

    private static void checkInvalid(int len) {
        try {
            new IPAddressName(new byte[len]);
        } catch (IOException e) {
            if (!"Invalid IPAddressName".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for length " + len + ": " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("No IOException for length " + len);
    }

    private static void checkNull() {
        try {
            new IPAddressName((byte[]) null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null address", e);
        }
        throw new RuntimeException("No NullPointerException for null address");
    }
}
