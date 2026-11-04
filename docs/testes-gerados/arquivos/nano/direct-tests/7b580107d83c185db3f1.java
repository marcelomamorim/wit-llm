/* @test    @summary Test for Norm2AllModes.getNFKCInstance() method    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testGetNFKCInstanceReturnsNonNull();
        testGetNFKCInstanceReturnsSameInstanceOnMultipleCalls();
    }

    private static void testGetNFKCInstanceReturnsNonNull() {
        Norm2AllModes instance = Norm2AllModes.getNFKCInstance();
        if (instance == null) {
            throw new AssertionError("Expected non-null instance from getNFKCInstance()");
        }
    }

    private static void testGetNFKCInstanceReturnsSameInstanceOnMultipleCalls() {
        Norm2AllModes firstInstance = Norm2AllModes.getNFKCInstance();
        Norm2AllModes secondInstance = Norm2AllModes.getNFKCInstance();
        if (firstInstance != secondInstance) {
            throw new AssertionError("Expected same instance on multiple calls to getNFKCInstance()");
        }
    }
}
