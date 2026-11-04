/*
 * @test
 * @summary Test constructor of RBCollationTables with various inputs to check ParseException and normal construction
 * @run main RBCollationTablesWitupTest
 */

import java.text.ParseException;
import java.text.RBCollationTables;

public class RBCollationTablesWitupTest {
    public static void main(String[] args) throws Exception {
        testEmptyRulesThrowsParseException();
        testValidRulesNoException();
    }

    private static void testEmptyRulesThrowsParseException() {
        try {
            new RBCollationTables("", 0);
            throw new RuntimeException("Expected ParseException for empty rules string");
        } catch (ParseException e) {
            // Expected exception
        }
    }

    private static void testValidRulesNoException() throws Exception {
        // Use a minimal valid rules string that should not cause ParseException
        // Since the actual rules format is not specified, use a simple non-empty string
        String rules = "a < b";
        RBCollationTables tables = new RBCollationTables(rules, 0);
        if (tables == null) {
            throw new RuntimeException("RBCollationTables constructor returned null");
        }
    }
}
