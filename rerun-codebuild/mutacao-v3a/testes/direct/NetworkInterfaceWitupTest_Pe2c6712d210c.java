/* @test    @summary Test NetworkInterface.getByIndex for valid and invalid indices    @run main NetworkInterfaceWitupTest_Pe2c6712d210c */

import java.net.NetworkInterface;
import java.net.SocketException;

public class NetworkInterfaceWitupTest_Pe2c6712d210c {

    public static void main(String[] args) {
        testGetByIndexValid();
        testGetByIndexNegative();
    }

    static void testGetByIndexValid() {
        try {
            int[] testIndices = {0, 1, Integer.MAX_VALUE};
            for (int index : testIndices) {
                try {
                    NetworkInterface ni = NetworkInterface.getByIndex(index);
                    if (ni == null) {
                        System.out.println("Failed: getByIndex(" + index + ") returned null");
                    } else {
                        System.out.println("Passed: getByIndex(" + index + ") returned " + ni.getClass().getName());
                    }
                } catch (SocketException e) {
                    System.out.println("SocketException thrown for index: " + index + 
                        ", which may be acceptable if index does not exist.");
                } catch (Exception e) {
                    System.out.println("Failed: Unexpected exception for index: " + index + 
                        ", exception: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in testGetByIndexValid: " + e);
        }
    }

    static void testGetByIndexNegative() {
        try {
            int negativeIndex = -1;
            NetworkInterface.getByIndex(negativeIndex);
            System.out.println("Failed: getByIndex(" + negativeIndex + ") did not throw" );
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: getByIndex(-1) threw IllegalArgumentException as expected");
        } catch (Exception e) {
            System.out.println("Failed: getByIndex(-1) threw unexpected exception: " + e);
        }
    }
}
