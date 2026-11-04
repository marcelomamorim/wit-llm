/*
 * @test
 * @summary Test StringCharacterIterator.setText(String) method behavior
 * @run main StringCharacterIteratorWitupTest
 */

public class StringCharacterIteratorWitupTest {
    public static void main(String[] args) {
        testSetTextNormal();
        testSetTextNull();
    }

    private static void testSetTextNormal() {
        java.text.StringCharacterIterator it = new java.text.StringCharacterIterator("initial");
        it.setText("newtext");
        if (!"newtext".equals(getText(it))) {
            throw new AssertionError("Text was not set correctly");
        }
        if (getBegin(it) != 0) {
            throw new AssertionError("Begin index should be 0 after setText");
        }
        if (getEnd(it) != "newtext".length()) {
            throw new AssertionError("End index should be length of new text after setText");
        }
        if (getPos(it) != 0) {
            throw new AssertionError("Position should be 0 after setText");
        }
    }

    private static void testSetTextNull() {
        java.text.StringCharacterIterator it = new java.text.StringCharacterIterator("abc");
        try {
            it.setText(null);
            throw new AssertionError("Expected NullPointerException when setting null text");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    // Reflection helpers to access private fields since no public getters for text, begin, end, pos
    private static String getText(java.text.StringCharacterIterator it) {
        try {
            java.lang.reflect.Field f = java.text.StringCharacterIterator.class.getDeclaredField("text");
            f.setAccessible(true);
            return (String) f.get(it);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int getBegin(java.text.StringCharacterIterator it) {
        try {
            java.lang.reflect.Field f = java.text.StringCharacterIterator.class.getDeclaredField("begin");
            f.setAccessible(true);
            return f.getInt(it);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int getEnd(java.text.StringCharacterIterator it) {
        try {
            java.lang.reflect.Field f = java.text.StringCharacterIterator.class.getDeclaredField("end");
            f.setAccessible(true);
            return f.getInt(it);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static int getPos(java.text.StringCharacterIterator it) {
        try {
            java.lang.reflect.Field f = java.text.StringCharacterIterator.class.getDeclaredField("pos");
            f.setAccessible(true);
            return f.getInt(it);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
