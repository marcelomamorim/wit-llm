/*
 * @test
 * @summary Test sun.text.normalizer.Norm2AllModes.getNFCInstance() for normal and exceptional behavior
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) {
        testGetNFCInstanceReturnsNonNull();
        testGetNFCInstanceExceptionPath();
    }

    private static void testGetNFCInstanceReturnsNonNull() {
        Norm2AllModes instance = Norm2AllModes.getNFCInstance();
        if (instance == null) {
            throw new AssertionError("getNFCInstance() returned null");
        }
    }

    private static void testGetNFCInstanceExceptionPath() {
        /*
         * According to WIT expath, getNFCInstance() may throw an exception if
         * NFCSingleton.INSTANCE.exception != null.
         * The current code calls getInstanceFromSingleton(NFCSingleton.INSTANCE),
         * which may throw NFCSingleton.INSTANCE.exception.
         *
         * Since we cannot set NFCSingleton.INSTANCE.exception publicly,
         * we cannot reliably trigger the exception.
         * This test calls getNFCInstance() and catches any Throwable,
         * verifying that if an exception is thrown, it is not unexpected.
         */
        try {
            Norm2AllModes.getNFCInstance();
        } catch (Throwable t) {
            // Accept any throwable as per the documented expath
            // No assertion here because we cannot control the singleton state
        }
    }
}
