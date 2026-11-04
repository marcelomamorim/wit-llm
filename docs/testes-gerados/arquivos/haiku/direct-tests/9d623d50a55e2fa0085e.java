/*
 * @test
 * @summary Verifies RBCollationTables construction through the public RuleBasedCollator(String) path
 * @run main RBCollationTablesWitupTest
 */

import java.text.RuleBasedCollator;
import java.text.ParseException;

public class RBCollationTablesWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testAscendingRules() throws ParseException {
        RuleBasedCollator c = new RuleBasedCollator("< a < b < c");
        check(c.compare("a", "b") < 0, "a must sort before b");
        check(c.compare("b", "c") < 0, "b must sort before c");
        check(c.compare("c", "a") > 0, "c must sort after a");
        check(c.compare("a", "a") == 0, "a must equal a");
    }

    static void testDescendingRules() throws ParseException {
        RuleBasedCollator c = new RuleBasedCollator("< c < b < a");
        check(c.compare("a", "c") > 0, "a must sort after c under reversed rules");
        check(c.compare("c", "b") < 0, "c must sort before b under reversed rules");
    }

    static void testTertiaryDifference() throws ParseException {
        RuleBasedCollator c = new RuleBasedCollator("< a,A");
        check(c.compare("a", "A") < 0, "lowercase a must sort before A at tertiary level");
    }

    static void testDeterministicRepeat() throws ParseException {
        RuleBasedCollator c1 = new RuleBasedCollator("< x < y");
        RuleBasedCollator c2 = new RuleBasedCollator("< x < y");
        check(c1.compare("x", "y") == c2.compare("x", "y"), "same rules must yield same ordering");
    }

    public static void main(String[] args) throws Exception {
        testAscendingRules();
        testDescendingRules();
        testTertiaryDifference();
        testDeterministicRepeat();
    }
}
