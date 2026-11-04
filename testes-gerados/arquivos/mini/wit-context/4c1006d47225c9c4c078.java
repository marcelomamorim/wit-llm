/*
 * @test
 * @summary Test BaseSSLSocketImpl.getOOBInline throws SocketException as expected
 * @run main BaseSSLSocketImplWitupTest
 */

import sun.security.ssl.BaseSSLSocketImpl;
import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {
    public static void main(String[] args) {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Provide minimal implementation to allow instantiation
        };

        try {
            socket.getOOBInline();
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("SocketException message unexpected: " + msg);
            }
        }
    }
}
