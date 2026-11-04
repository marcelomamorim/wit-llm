/*
 * @test
 * @summary Witup-derived tests for GeneralNames.add null handling and default state
 * @modules java.base/sun.security.x509
 * @run main GeneralNamesWitupTest
 */

import sun.security.x509.GeneralNames;

public class GeneralNamesWitupTest {

    public static void main(String[] args) throws Exception {
        testAddNullThrowsNPE();
        testAddNullLeavesListUnchanged();
        testDefaultConstructorIsEmpty();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddNullThrowsNPE() {
        GeneralNames names = new GeneralNames();
        boolean thrown = false;
        try {
            names.add(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "add(null) did not throw NullPointerException");
    }

    static void testAddNullLeavesListUnchanged() {
        GeneralNames names = new GeneralNames();
        try {
            names.add(null);
        } catch (NullPointerException e) {
            // expected
        }
        check(names.size() == 0, "size changed after failed add(null)");
        check(names.isEmpty(), "list not empty after failed add(null)");
    }

    static void testDefaultConstructorIsEmpty() {
        GeneralNames names = new GeneralNames();
        check(names.isEmpty(), "default GeneralNames should be empty");
        check(names.size() == 0, "default GeneralNames size should be 0");
    }
}
