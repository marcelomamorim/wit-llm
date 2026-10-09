/* @test    @summary Test Collections.nCopies with valid and invalid inputs    @run main CollectionsWitupTest_P8c69833ed493 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest_P8c69833ed493 {

    public static void main(String[] args) {
        testNCopiesWithPositiveNumber();
        testNCopiesWithZero();
        testNCopiesWithNegativeNumber();
        testNCopiesWithNullObject();
    }

    private static void testNCopiesWithPositiveNumber() {
        int n = 5;
        String obj = "test";
        List<String> list = Collections.nCopies(n, obj);
        assert list.size() == n : "Expected size " + n + " but got " + list.size();
        for (String item : list) {
            assert item.equals(obj) : "Expected item to be " + obj + " but got " + item;
        }
    }

    private static void testNCopiesWithZero() {
        int n = 0;
        String obj = "zero";
        List<String> list = Collections.nCopies(n, obj);
        assert list.size() == 0 : "Expected size 0 but got " + list.size();
    }

    private static void testNCopiesWithNegativeNumber() {
        int n = -3;
        String obj = "negative";
        boolean exceptionThrown = false;
        try {
            List<String> list = Collections.nCopies(n, obj);
            assert false : "Expected IllegalArgumentException for negative n";
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "IllegalArgumentException was not thrown for negative n";
    }

    private static void testNCopiesWithNullObject() {
        int n = 3;
        Object obj = null;
        List<Object> list = Collections.nCopies(n, obj);
        assert list.size() == n : "Expected size " + n + " but got " + list.size();
        for (Object item : list) {
            assert item == null : "Expected null item but got " + item;
        }
    }
}
