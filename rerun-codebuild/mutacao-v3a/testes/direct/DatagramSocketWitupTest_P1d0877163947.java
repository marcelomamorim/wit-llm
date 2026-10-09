/* @test    @summary Test setTrafficClass method of DatagramSocket for range and socket state.    @run main DatagramSocketWitupTest_P1d0877163947 */

import java.net.DatagramSocket;
import java.net.SocketException;

public class DatagramSocketWitupTest_P1d0877163947 {

    public static void main(String[] args) {
        testSetTrafficClassValidRange();
        testSetTrafficClassBelowRange();
        testSetTrafficClassAboveRange();
        testSetTrafficClassWhenClosed();
    }

    static void testSetTrafficClassValidRange() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(0);
            ds.setTrafficClass(255);
            System.out.println("testSetTrafficClassValidRange passed");
            ds.close();
        } catch (Exception e) {
            System.out.println("testSetTrafficClassValidRange failed: " + e);
        }
    }

    static void testSetTrafficClassBelowRange() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(-1);
            System.out.println("testSetTrafficClassBelowRange failed: no exception");
            ds.close();
        } catch (IllegalArgumentException e) {
            System.out.println("testSetTrafficClassBelowRange passed");
        } catch (Exception e) {
            System.out.println("testSetTrafficClassBelowRange failed: " + e);
        }
    }

    static void testSetTrafficClassAboveRange() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setTrafficClass(256);
            System.out.println("testSetTrafficClassAboveRange failed: no exception");
            ds.close();
        } catch (IllegalArgumentException e) {
            System.out.println("testSetTrafficClassAboveRange passed");
        } catch (Exception e) {
            System.out.println("testSetTrafficClassAboveRange failed: " + e);
        }
    }

    static void testSetTrafficClassWhenClosed() {
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.close();
            ds.setTrafficClass(10);
            System.out.println("testSetTrafficClassWhenClosed failed: no exception");
        } catch (SocketException e) {
            System.out.println("testSetTrafficClassWhenClosed passed");
        } catch (Exception e) {
            System.out.println("testSetTrafficClassWhenClosed failed: " + e);
        }
    }
}
