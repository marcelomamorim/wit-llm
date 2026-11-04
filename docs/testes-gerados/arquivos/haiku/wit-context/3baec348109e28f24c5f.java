/*
 * @test
 * @summary Checks the public contract of SSLServerSocketFactory.getDefault() without relying on the discarded WIT expath
 * @run main SSLServerSocketFactoryWitupTest
 */

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocketFactoryWitupTest {

    public static void main(String[] args) throws Exception {
        ServerSocketFactory first = SSLServerSocketFactory.getDefault();
        if (first == null) {
            throw new AssertionError("getDefault() returned null");
        }
        if (!(first instanceof ServerSocketFactory)) {
            throw new AssertionError("getDefault() result is not a ServerSocketFactory");
        }

        ServerSocketFactory second = SSLServerSocketFactory.getDefault();
        if (first != second) {
            throw new AssertionError("getDefault() should return the same cached instance");
        }
    }
}
