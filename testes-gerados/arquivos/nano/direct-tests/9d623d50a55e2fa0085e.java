/* @test    @summary Test RBCollationTables constructor with various rules    @run main RBCollationTablesWitupTest */

import java.text.RBCollationTables;
import java.text.ParseException;

public class RBCollationTablesWitupTest {

    public static void main(String[] args) {
        testValidRules();
        testEmptyRules();
        testInvalidRules();
    }

    static void testValidRules() {
        try {
            String rules = "a, A < b, B < c, C";
            RBCollationTables tables = new RBCollationTables(rules, 0);
            assert tables != null : "Expected non-null RBCollationTables for valid rules";
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for valid rules", e);
        }
    }

    static void testEmptyRules() {
        try {
            String rules = "";
            RBCollationTables tables = new RBCollationTables(rules, 0);
            assert tables != null : "Expected non-null RBCollationTables for empty rules";
        } catch (ParseException e) {
            throw new AssertionError("Unexpected ParseException for empty rules", e);
        }
    }

    static void testInvalidRules() {
        boolean exceptionThrown = false;
        try {
            String rules = "invalid rule syntax";
            new RBCollationTables(rules, 0);
        } catch (ParseException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected ParseException for invalid rules";
    }
}
