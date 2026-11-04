/*
 * @test
 * @summary Verifies RegularEnumSet iterator next() ordering, snapshot behavior and NoSuchElementException through the public EnumSet API
 * @run main RegularEnumSetWitupTest
 */
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularEnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW, BLACK }

    public static void main(String[] args) {
        testOrderedIteration();
        testSingleElement();
        testAllElements();
        testNextOnEmptySetThrows();
        testNextAfterExhaustionThrows();
        testSnapshotIgnoresLaterAdds();
    }

    static void testOrderedIteration() {
        EnumSet<Color> s = EnumSet.of(Color.BLACK, Color.RED, Color.BLUE);
        Iterator<Color> it = s.iterator();
        check(it.hasNext());
        check(it.next() == Color.RED);
        check(it.next() == Color.BLUE);
        check(it.next() == Color.BLACK);
        check(!it.hasNext());
    }

    static void testSingleElement() {
        EnumSet<Color> s = EnumSet.of(Color.YELLOW);
        Iterator<Color> it = s.iterator();
        check(it.hasNext());
        check(it.next() == Color.YELLOW);
        check(!it.hasNext());
    }

    static void testAllElements() {
        Iterator<Color> it = EnumSet.allOf(Color.class).iterator();
        Color[] values = Color.values();
        for (int i = 0; i < values.length; i++) {
            check(it.hasNext());
            check(it.next() == values[i]);
        }
        check(!it.hasNext());
    }

    static void testNextOnEmptySetThrows() {
        Iterator<Color> it = EnumSet.noneOf(Color.class).iterator();
        check(!it.hasNext());
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown);
    }

    static void testNextAfterExhaustionThrows() {
        Iterator<Color> it = EnumSet.of(Color.GREEN).iterator();
        check(it.next() == Color.GREEN);
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown);
    }

    static void testSnapshotIgnoresLaterAdds() {
        EnumSet<Color> s = EnumSet.of(Color.RED, Color.GREEN);
        Iterator<Color> it = s.iterator();
        s.add(Color.YELLOW);
        check(it.next() == Color.RED);
        check(it.next() == Color.GREEN);
        check(!it.hasNext());
        check(s.contains(Color.YELLOW));
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Check failed");
        }
    }
}
