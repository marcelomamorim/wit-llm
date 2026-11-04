/* @test    @summary Test for ZoneOffset.ofHours(int) method to verify behavior with valid and invalid inputs.    @run main ZoneOffsetWitupTest */

import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testValidHours();
        testInvalidHours();
    }

    static void testValidHours() {
        // Valid input: within -18 to +18
        for (int h = -18; h <= 18; h++) {
            try {
                ZoneOffset offset = ZoneOffset.ofHours(h);
                if (offset == null) {
                    throw new AssertionError("Expected non-null ZoneOffset for hours: " + h);
                }
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for hours: " + h, e);
            }
        }
    }

    static void testInvalidHours() {
        int[] invalidHours = {19, -19, 20, -20, 100, -100};
        for (int h : invalidHours) {
            try {
                ZoneOffset.ofHours(h);
                throw new AssertionError("Expected exception for invalid hours: " + h);
            } catch (java.time.DateTimeException e) {
                // Expected exception, test passes
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for hours: " + h, e);
            }
        }
    }
}
