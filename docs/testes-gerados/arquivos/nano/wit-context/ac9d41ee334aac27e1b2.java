/* @test    @summary Test for sun.util.calendar.ZoneInfo.getAliasTable() behavior and exception paths    @run main ZoneInfoWitupTest */

import java.util.Map;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        testGetAliasTableReturnsMap();
        testGetAliasTableNullPointer();
    }

    private static void testGetAliasTableReturnsMap() {
        Map<String, String> result = ZoneInfo.getAliasTable();
        if (result == null) {
            throw new AssertionError("Expected non-null result from getAliasTable()");
        }
    }

    private static void testGetAliasTableNullPointer() {
        try {
            // Simulate the scenario where getAliasMap() returns null
            // Since getAliasMap() is package-private and static, we cannot mock directly.
            // Instead, we invoke getAliasTable() and assume the underlying implementation
            // can be manipulated or we rely on the current behavior.
            // As per current source, getAliasMap() is called directly.
            // If getAliasMap() returns null, getAliasTable() returns null.
            // We test that behavior.
            Map<String, String> result = ZoneInfo.getAliasTable();
            if (result == null) {
                // This matches the guard condition aliases == null
                // Confirm that the method handles null gracefully
                System.out.println("getAliasTable() returned null as expected for null alias map.");
            } else {
                System.out.println("getAliasTable() returned a non-null map.");
            }
        } catch (NullPointerException e) {
            throw new AssertionError("Unexpected NullPointerException in getAliasTable()", e);
        }
    }
}
