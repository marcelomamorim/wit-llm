/*
 * @test
 * @summary Verifies that the unconnected createServerSocket() of SSLServerSocketFactory throws IOException when not overridden by the subclass dispatch path.
 * @run main SSLServerSocketFactoryWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLServerSocketFactoryWitupTest {

    static class StubFactory extends SSLServerSocketFactory {
        @Override
        public ServerSocket createServerSocket(int port) throws IOException {
            return null;
        }

        @Override
        public ServerSocket createServerSocket(int port, int backlog) throws IOException {
            return null;
        }

        @Override
        public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException {
            return null;
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return new String[0];
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return new String[0];
        }
    }

    public static void main(String[] args) {
        StubFactory factory = new StubFactory();
        boolean thrownDirect = false;
        try {
            factory.createServerSocket();
        } catch (IOException e) {
            thrownDirect = true;
        }
        if (!thrownDirect) {
            throw new RuntimeException("createServerSocket() did not throw IOException");
        }

        ServerSocketFactory base = factory;
        boolean thrownViaBase = false;
        try {
            base.createServerSocket();
        } catch (IOException e) {
            thrownViaBase = true;
        }
        if (!thrownViaBase) {
            throw new RuntimeException("createServerSocket() via ServerSocketFactory reference did not throw IOException");
        }
    }
}
