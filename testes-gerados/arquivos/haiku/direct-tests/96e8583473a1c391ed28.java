/*
 * @test
 * @summary Checks public name resolution paths that reach the InetAddress resolution result handling, including the UnknownHostException path
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressWitupTest {
    public static void main(String[] args) throws Exception {
        InetAddress[] addrs = InetAddress.getAllByName("localhost");
        if (addrs == null || addrs.length == 0) {
            throw new RuntimeException("localhost resolved to no addresses");
        }
        for (InetAddress a : addrs) {
            if (a == null) {
                throw new RuntimeException("null address returned for localhost");
            }
        }

        boolean unknownHost = false;
        try {
            InetAddress.getAllByName("nonexistent.invalid");
        } catch (UnknownHostException e) {
            unknownHost = true;
        }
        if (!unknownHost) {
            throw new RuntimeException("expected UnknownHostException for nonexistent.invalid");
        }
    }
}
