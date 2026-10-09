/* @test
   @summary Tests the static method ZoneOffset.ofTotalSeconds(int).
   @run main ZoneOffsetWitupTest_P37ec03090b57
 */
import java.time.ZoneOffset;
import java.time.DateTimeException;

public class ZoneOffsetWitupTest_P37ec03090b57 {
    public static void main(String[] args) {
        testValidOffsets();
        testInvalidOffsets();
        testCacheBehavior();
    }

    static void testValidOffsets() {
        // Test minimum valid offset (-64800 seconds, -18 hours)
        ZoneOffset offsetMin = ZoneOffset.ofTotalSeconds(-64800);
        assert offsetMin.getTotalSeconds() == -64800 : "Failed at min offset";
        // Test maximum valid offset (+64800 seconds, +18 hours)
        ZoneOffset offsetMax = ZoneOffset.ofTotalSeconds(64800);
        assert offsetMax.getTotalSeconds() == 64800 : "Failed at max offset";
        // Test zero offset
        ZoneOffset offsetZero = ZoneOffset.ofTotalSeconds(0);
        assert offsetZero.getTotalSeconds() == 0 : "Failed at zero offset";
    }

    static void testInvalidOffsets() {
        // Offset less than minimum
        try {
            ZoneOffset.ofTotalSeconds(-64801);
            throw new AssertionError("Expected DateTimeException for offset below minimum");
        } catch (DateTimeException e) {
            // expected
        }
        // Offset greater than maximum
        try {
            ZoneOffset.ofTotalSeconds(64801);
            throw new AssertionError("Expected DateTimeException for offset above maximum");
        } catch (DateTimeException e) {
            // expected
        }
        // Offset not divisible by 900 seconds (15 minutes)
        try {
            ZoneOffset.ofTotalSeconds(900 + 1);
            // Should succeed, as per implementation, since no restriction on divisibility
            // But to test behavior, we check if it creates an offset
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(901);
            assert offset.getTotalSeconds() == 901 : "Offset mismatch";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for non-divisible offset", e);
        }
    }

    static void testCacheBehavior() {
        int totalSeconds = 3600; // +1 hour
        ZoneOffset offset1 = ZoneOffset.ofTotalSeconds(totalSeconds);
        ZoneOffset offset2 = ZoneOffset.ofTotalSeconds(totalSeconds);
        // Since cache is used, both references should be equal
        assert offset1 == offset2 : "Cache not returning same instance";
        // Check that ID cache contains the ID
        String id = offset1.getId();
        // The ID should be in the cache (not directly accessible, but we can check consistency)
        assert id.equals("+01:00") || id.equals("-01:00") || id.matches("[+-][0-9]{2}:[0-9]{2}") : "ID format invalid";
    }
}
