/*
 * @test
 * @summary Verifies IPAddressName(byte[]) accepts 4, 8, 16 and 32 byte arrays and throws IOException for other lengths
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */
import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        int[] valid = {4, 8, 16, 32};
        for (int len : valid) {
            IPAddressName name = new IPAddressName(new byte[len]);
            if (name == null) {
                throw new RuntimeException("null IPAddressName for length " + len);
            }
        }

        int[] invalid = {0, 1, 3, 5, 7, 9, 12, 15, 17, 31, 33};
        for (int len : invalid) {
            boolean thrown = false;
            try {
                new IPAddressName(new byte[len]);
            } catch (IOException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("expected IOException for length " + len);
            }
        }
    }
}
