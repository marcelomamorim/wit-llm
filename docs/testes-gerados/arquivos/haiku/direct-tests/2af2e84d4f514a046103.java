/*
 * @test
 * @summary Verify NetworkInterface.getByIndex rejects negative indexes with IllegalArgumentException and accepts non-negative indexes without that exception
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    private static final String EXPECTED_MESSAGE = "Interface index can't be negative";

    public static void main(String[] args) throws Exception {
        int[] negatives = {-1, Integer.MIN_VALUE};
        for (int idx : negatives) {
            boolean thrown = false;
            try {
                NetworkInterface.getByIndex(idx);
            } catch (IllegalArgumentException e) {
                thrown = true;
                if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message for index " + idx + ": " + e.getMessage());
                }
            }
            if (!thrown) {
                throw new RuntimeException("No IllegalArgumentException for negative index " + idx);
            }
        }

        try {
            NetworkInterface.getByIndex(0);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Index 0 must not be rejected as negative", e);
        } catch (SocketException e) {
            // Platform-dependent native lookup; a SocketException is an acceptable outcome.
        }
    }
}
