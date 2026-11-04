/*
 * @test
 * @summary Tests the single-element factory EnumSet.of(E e) using only public API
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE }

    enum Op {
        PLUS { int apply(int a, int b) { return a + b; } },
        MINUS { int apply(int a, int b) { return a - b; } };
        abstract int apply(int a, int b);
    }

    public static void main(String[] args) {
        testSingleElementSet();
        testConstantWithBodyUsesDeclaringClass();
        testNullElementThrowsNPE();
        testSetsAreIndependent();
        testResultIsMutable();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleElementSet() {
        EnumSet<Color> s = EnumSet.of(Color.GREEN);
        check(s.size() == 1, "size should be 1");
        check(s.contains(Color.GREEN), "set should contain GREEN");
        check(!s.contains(Color.RED), "set should not contain RED");
        check(!s.contains(Color.BLUE), "set should not contain BLUE");
        Iterator<Color> it = s.iterator();
        check(it.hasNext(), "iterator should have an element");
        check(it.next() == Color.GREEN, "first element should be GREEN");
        check(!it.hasNext(), "iterator should have exactly one element");
    }

    static void testConstantWithBodyUsesDeclaringClass() {
        EnumSet<Op> s = EnumSet.of(Op.MINUS);
        check(s.size() == 1, "size should be 1 for constant with body");
        check(s.contains(Op.MINUS), "set should contain MINUS");
        check(!s.contains(Op.PLUS), "set should not contain PLUS");
        check(s.iterator().next() == Op.MINUS, "element should be MINUS");
    }

    static void testNullElementThrowsNPE() {
        boolean thrown = false;
        try {
            EnumSet.of((Color) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "of(null) should throw NullPointerException");
    }

    static void testSetsAreIndependent() {
        EnumSet<Color> s1 = EnumSet.of(Color.RED);
        EnumSet<Color> s2 = EnumSet.of(Color.RED);
        check(s1 != s2, "each call should return a new set");
        s1.add(Color.BLUE);
        check(s1.size() == 2, "s1 should have 2 elements after add");
        check(s2.size() == 1, "s2 should be unaffected by changes to s1");
        check(!s2.contains(Color.BLUE), "s2 should not contain BLUE");
    }

    static void testResultIsMutable() {
        EnumSet<Color> s = EnumSet.of(Color.RED);
        check(s.add(Color.GREEN), "add of new element should return true");
        check(!s.add(Color.GREEN), "add of existing element should return false");
        check(s.remove(Color.RED), "remove of present element should return true");
        check(s.size() == 1 && s.contains(Color.GREEN), "final set should contain only GREEN");
    }
}
