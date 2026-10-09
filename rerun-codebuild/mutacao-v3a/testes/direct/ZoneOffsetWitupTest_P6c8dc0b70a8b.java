/*
 * @test
 * @summary Test ZoneOffset.ofTotalSeconds for valid and invalid inputs
 * @run main ZoneOffsetWitupTest_P6c8dc0b70a8b
 */

import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest_P6c8dc0b70a8b {

    public static void main(String[] args) {
        testValidOffsets();
        testInvalidOffsets();
        testCacheBehavior();
    }

    static void testValidOffsets() {
        // Test minimum valid offset
        try {
            ZoneOffset offsetMin = ZoneOffset.ofTotalSeconds(-64800); // -18:00
            assert offsetMin.getTotalSeconds() == -64800 : "Expected -64800 seconds";
        } catch (Exception e) {
            throw new AssertionError("Failed to create minimum offset", e);
        }
        // Test maximum valid offset
        try {
            ZoneOffset offsetMax = ZoneOffset.ofTotalSeconds(64800); // +18:00
            assert offsetMax.getTotalSeconds() == 64800 : "Expected 64800 seconds";
        } catch (Exception e) {
            throw new AssertionError("Failed to create maximum offset", e);
        }
        // Test zero offset
        try {
            ZoneOffset offsetZero = ZoneOffset.ofTotalSeconds(0);
            assert offsetZero.getTotalSeconds() == 0 : "Expected 0 seconds";
        } catch (Exception e) {
            throw new AssertionError("Failed to create zero offset", e);
        }
        // Test typical offset
        try {
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(3600); // +1:00
            assert offset.getTotalSeconds() == 3600 : "Expected 3600 seconds";
        } catch (Exception e) {
            throw new AssertionError("Failed to create typical offset", e);
        }
    }

    static void testInvalidOffsets() {
        // Below minimum offset
        try {
            ZoneOffset.ofTotalSeconds(-64801);
            throw new AssertionError("Expected exception for offset below -18:00");
        } catch (DateTimeException e) {
            // expected
        }
        // Above maximum offset
        try {
            ZoneOffset.ofTotalSeconds(64801);
            throw new AssertionError("Expected exception for offset above +18:00");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testCacheBehavior() {
        int offsetSeconds = 18000; // +5:00
        ZoneOffset offset1 = ZoneOffset.ofTotalSeconds(offsetSeconds);
        ZoneOffset offset2 = ZoneOffset.ofTotalSeconds(offsetSeconds);
        // Should return same cached instance
        assert offset1 == offset2 : "Expected cached instance for same offset";
        // ID cache should also contain the same instance
        String id = offset1.getId();
        ZoneOffset offset3 = ZoneOffset.ofTotalSeconds(offsetSeconds);
        assert offset1 == offset3 : "Expected same instance from cache";
    }
}
