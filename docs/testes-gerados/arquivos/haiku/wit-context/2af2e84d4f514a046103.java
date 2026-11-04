/*
 * @test
 * @summary Verifies that NetworkInterface.getByIndex rejects negative indexes with IllegalArgumentException
 * @run main NetworkInterfaceWitupTest
 */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest {

    public static void main(String[] args) throws Exception {
        checkNegativeIndexRejected(-1);
        checkNegativeIndexRejected(Integer.MIN_VALUE);
    }

    private static void checkNegativeIndexRejected(int index) throws SocketException {
        try {
            NetworkInterface.getByIndex(index);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for index " + index);
    }
}
