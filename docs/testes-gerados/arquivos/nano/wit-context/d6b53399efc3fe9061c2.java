/* @test    @summary Test for ZoneRules.of(ZoneOffset) method to verify behavior with null input and valid input    @run main ZoneRulesWitupTest */

import java.time.ZoneOffset;
import java.time.zone.ZoneRules;

public class ZoneRulesWitupTest {

    public static void main(String[] args) {
        testOfWithNull();
        testOfWithValidOffset();
    }

    private static void testOfWithNull() {
        try {
            ZoneRules.of(null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected for null input");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception type: " + e);
        }
    }

    private static void testOfWithValidOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneRules rules = ZoneRules.of(offset);
        if (rules != null) {
            System.out.println("Passed: ZoneRules.of returned non-null for valid offset");
        } else {
            System.err.println("Failed: ZoneRules.of returned null for valid offset");
        }
    }
}
