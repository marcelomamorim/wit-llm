/*
 * @test
 * @summary Test for RBCollationTables constructor with valid and invalid inputs.
 * @run main RBCollationTablesWitupTest
 */

import java.text.RBCollationTables;
import java.text.ParseException;

public class RBCollationTablesWitupTest {

    public static void main(String[] args) {
        testValidRules();
        testEmptyRules();
    }

    private static void testValidRules() {
        String rules = "a < b < c";
        int decmp = 0;
        try {
            RBCollationTables tables = new RBCollationTables(rules, decmp);
            System.out.println("testValidRules passed");
        } catch (ParseException e) {
            System.err.println("testValidRules failed: " + e);
        }
    }

    private static void testEmptyRules() {
        String rules = "";
        int decmp = 0;
        try {
            RBCollationTables tables = new RBCollationTables(rules, decmp);
            System.out.println("testEmptyRules passed");
        } catch (ParseException e) {
            System.err.println("testEmptyRules failed: " + e);
        }
    }
}
