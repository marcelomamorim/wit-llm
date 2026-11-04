/*
 * @test
 * @summary Tests for java.time.ZoneId.of(String, Map<String, String>)
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testNullZoneId();
        testNullAliasMap();
    }

    private static void testNullZoneId() {
        try {
            ZoneId.of(null, new HashMap<>());
            System.err.println("Failed: Expected NullPointerException for null zoneId");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testNullAliasMap() {
        try {
            ZoneId.of("Europe/Paris", null);
            System.err.println("Failed: Expected NullPointerException for null aliasMap");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
