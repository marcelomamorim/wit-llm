/* @test    @summary Test for Norm2AllModes.getNFCInstance() method    @run main Norm2AllModesWitupTest */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testGetNFCInstanceReturnsObject();
    }

    public static void testGetNFCInstanceReturnsObject() {
        Norm2AllModes instance = Norm2AllModes.getNFCInstance();
        if (instance == null) {
            throw new AssertionError("Expected non-null instance from getNFCInstance()");
        }
        if (!(instance instanceof Norm2AllModes)) {
            throw new AssertionError("Instance is not of type Norm2AllModes");
        }
        System.out.println("testGetNFCInstanceReturnsObject passed.");
    }
}
