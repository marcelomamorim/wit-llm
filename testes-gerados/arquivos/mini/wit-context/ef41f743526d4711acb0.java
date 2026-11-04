/*
 * @test
 * @summary Test JumboEnumSet.EnumSetIterator.remove() behavior including IllegalStateException
 * @run main JumboEnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class JumboEnumSetWitupTest {

    // Minimal enum with more than 64 elements to force JumboEnumSet usage
    enum LargeEnum {
        E0, E1, E2, E3, E4, E5, E6, E7, E8, E9,
        E10, E11, E12, E13, E14, E15, E16, E17, E18, E19,
        E20, E21, E22, E23, E24, E25, E26, E27, E28, E29,
        E30, E31, E32, E33, E34, E35, E36, E37, E38, E39,
        E40, E41, E42, E43, E44, E45, E46, E47, E48, E49,
        E50, E51, E52, E53, E54, E55, E56, E57, E58, E59,
        E60, E61, E62, E63, E64, E65, E66, E67, E68, E69
    }

    public static void main(String[] args) {
        // Create JumboEnumSet by using EnumSet.range with large enum to force JumboEnumSet
        EnumSet<LargeEnum> set = EnumSet.range(LargeEnum.E0, LargeEnum.E69);

        // Confirm that the set is a JumboEnumSet by checking class name
        if (!set.getClass().getName().contains("JumboEnumSet")) {
            throw new RuntimeException("Test requires JumboEnumSet instance");
        }

        Iterator<LargeEnum> it = set.iterator();

        // Test IllegalStateException when remove() called before next()
        boolean threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when remove() called before next()");
        }

        // Advance iterator and remove element
        if (!it.hasNext()) {
            throw new RuntimeException("Iterator has no elements");
        }
        LargeEnum first = it.next();
        it.remove();

        // After removal, the element should no longer be contained in the set
        if (set.contains(first)) {
            throw new RuntimeException("Element was not removed from the set");
        }

        // Calling remove() twice without next() should throw IllegalStateException
        threw = false;
        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException when remove() called twice without next()");
        }

        // Iterate through remaining elements and remove all
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        // After removing all elements via iterator, set should be empty
        if (!set.isEmpty()) {
            throw new RuntimeException("Set should be empty after removing all elements via iterator");
        }

        System.out.println("PASS");
    }
}
