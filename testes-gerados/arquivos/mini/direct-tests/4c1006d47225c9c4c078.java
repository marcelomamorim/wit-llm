/*
 * @test
 * @summary Test BaseSSLSocketImpl.getOOBInline throws SocketException
 * @run main BaseSSLSocketImplWitupTest
 */

import sun.security.ssl.BaseSSLSocketImpl;
import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Abstract class requires implementation of abstract methods
            // but since no abstract methods are shown, empty anonymous class suffices
        };

        try {
            socket.getOOBInline();
            throw new RuntimeException("Expected SocketException was not thrown");
        } catch (SocketException se) {
            String msg = se.getMessage();
            if (msg == null || !msg.contains("urgent data")) {
                throw new RuntimeException("SocketException message unexpected: " + msg);
            }
        }
    }
}
