/*
 * @test
 * @summary Verify the public no-arg SSLSocketFactoryImpl constructor succeeds with the default SSL context
 * @modules java.base/sun.security.ssl
 * @run main SSLSocketFactoryImplWitupTest
 */

import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
import sun.security.ssl.SSLSocketFactoryImpl;

public class SSLSocketFactoryImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactoryImpl factory = new SSLSocketFactoryImpl();
        if (factory == null) {
            throw new RuntimeException("Constructor returned null");
        }
        if (!(factory instanceof SSLSocketFactory)) {
            throw new RuntimeException("Factory is not an SSLSocketFactory");
        }
        Socket socket = factory.createSocket();
        if (socket == null) {
            throw new RuntimeException("createSocket() returned null");
        }
        socket.close();
    }
}
