/* @test    @summary Test NetworkInterface.getByName(String) for null and valid input    @run main NetworkInterfaceWitupTest_P0a7e9032713a */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_P0a7e9032713a {

    public static void main(String[] args) throws Exception {
        testGetByNameNull();
        testGetByNameValid();
    }

    static void testGetByNameNull() {
        try {
            NetworkInterface.getByName(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null input: " + e);
        }
    }

    static void testGetByNameValid() {
        try {
            NetworkInterface ni = NetworkInterface.getByName("lo");
            if (ni != null && "lo".equals(ni.getName())) {
                System.out.println("Passed: getByName("lo") returned interface with name 'lo'");
            } else {
                System.err.println("Failed: getByName("lo") did not return expected interface");
            }
        } catch (SocketException e) {
            System.err.println("Failed: SocketException thrown during getByName("lo"): " + e);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception during getByName("lo"): " + e);
        }
    }
}
