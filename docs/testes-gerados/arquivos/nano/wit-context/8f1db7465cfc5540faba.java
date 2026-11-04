/* @test    @summary Test for AlgorithmParameters.init(byte[]) method to verify exception handling and behavior.    @run main AlgorithmParametersWitupTest */

import java.io.*;
import java.security.*;

public class AlgorithmParametersWitupTest {

    public static void main(String[] args) {
        testInitThrowsIOExceptionWhenAlreadyInitialized();
        testInitNormalBehavior();
    }

    private static void testInitThrowsIOExceptionWhenAlreadyInitialized() {
        AlgorithmParameters ap = null;
        try {
            ap = AlgorithmParameters.getInstance("AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AlgorithmParameters instance", e);
        }
        byte[] dummyParams = new byte[] {1, 2, 3};
        try {
            ap.init(dummyParams);
        } catch (Exception e) {
            // ignore, as init may throw if not initialized
        }
        boolean exceptionThrown = false;
        try {
            ap.init(dummyParams);
            System.out.println("Expected IOException was not thrown.");
        } catch (IOException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            System.out.println("Unexpected exception type: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("init(byte[]) did not throw IOException when already initialized.");
        }
    }

    private static void testInitNormalBehavior() {
        AlgorithmParameters ap = null;
        try {
            ap = AlgorithmParameters.getInstance("AES");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AlgorithmParameters instance", e);
        }
        byte[] validParams = new byte[] {10, 20, 30};
        try {
            ap.init(validParams);
        } catch (Exception e) {
            throw new AssertionError("init(byte[]) threw an unexpected exception: " + e);
        }
        // Verify that subsequent init throws IOException
        boolean exceptionThrown = false;
        try {
            ap.init(validParams);
            System.out.println("Expected IOException was not thrown on second init.");
        } catch (IOException e) {
            exceptionThrown = true;
        } catch (Exception e) {
            System.out.println("Unexpected exception type on second init: " + e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Second init(byte[]) did not throw IOException.");
        }
    }
}
