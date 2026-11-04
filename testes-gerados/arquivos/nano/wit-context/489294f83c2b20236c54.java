/*
 * @test
 * @summary Test for Norm2AllModes.append method, focusing on the exception path where first == second.
 * @run main Norm2AllModesWitupTest
 */

import sun.text.normalizer.Norm2AllModes;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) {
        testAppendThrowsIllegalArgumentExceptionWhenFirstEqualsSecond();
    }

    private static void testAppendThrowsIllegalArgumentExceptionWhenFirstEqualsSecond() {
        StringBuilder sb = new StringBuilder("test");
        CharSequence seq = sb;
        try {
            // According to source, append calls normalizeSecondAndAppend with first and second
            // and throws IllegalArgumentException if first == second.
            Norm2AllModes.append(sb, seq);
            // If no exception, test fails
            System.err.println("Failed: Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Passed: IllegalArgumentException thrown as expected.");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
