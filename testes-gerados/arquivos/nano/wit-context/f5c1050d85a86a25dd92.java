/*
 * @test
 * @summary Test getNFCInstance method for Norm2AllModes class.
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testGetNFCInstanceReturnsObject();
        testGetNFCInstanceNotNull();
    }

    private static void testGetNFCInstanceReturnsObject() {
        Norm2AllModes instance = Norm2AllModes.getNFCInstance();
        if (instance == null) {
            throw new AssertionError("Expected non-null instance from getNFCInstance()");
        }
    }

    private static void testGetNFCInstanceNotNull() {
        Norm2AllModes instance = Norm2AllModes.getNFCInstance();
        if (instance == null) {
            throw new AssertionError("Expected non-null object from getNFCInstance()");
        }
    }
}
