/* @test    @summary Test DatagramSocket.connect with valid and invalid addresses    @run main DatagramSocketWitupTest */

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class DatagramSocketWitupTest {

    public static void main(String[] args) {
        testConnectWithValidAddress();
        testConnectWithNullAddress();
        testConnectWithUnresolvedAddress();
        testConnectWithInvalidTypeAddress();
    }

    static void testConnectWithValidAddress() {
        try {
            DatagramSocket ds = new DatagramSocket();
            InetSocketAddress addr = new InetSocketAddress("localhost", 12345);
            ds.connect(addr);
            System.out.println("testConnectWithValidAddress: PASSED");
        } catch (Exception e) {
            System.out.println("testConnectWithValidAddress: FAILED - " + e);
        }
    }

    static void testConnectWithNullAddress() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.connect(null);
            System.out.println("testConnectWithNullAddress: FAILED - no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testConnectWithNullAddress: PASSED");
        } catch (Exception e) {
            System.out.println("testConnectWithNullAddress: FAILED - " + e);
        }
    }

    static void testConnectWithUnresolvedAddress() {
        try {
            DatagramSocket ds = new DatagramSocket();
            InetSocketAddress unresolvedAddr = new InetSocketAddress("unresolvedhost", 12345);
            ds.connect(unresolvedAddr);
            System.out.println("testConnectWithUnresolvedAddress: FAILED - no exception");
        } catch (SocketException e) {
            System.out.println("testConnectWithUnresolvedAddress: PASSED");
        } catch (Exception e) {
            System.out.println("testConnectWithUnresolvedAddress: FAILED - " + e);
        }
    }

    static void testConnectWithInvalidTypeAddress() {
        try {
            DatagramSocket ds = new DatagramSocket();
            SocketAddress invalidAddr = new SocketAddress() {};
            ds.connect(invalidAddr);
            System.out.println("testConnectWithInvalidTypeAddress: FAILED - no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testConnectWithInvalidTypeAddress: PASSED");
        } catch (Exception e) {
            System.out.println("testConnectWithInvalidTypeAddress: FAILED - " + e);
        }
    }
}
