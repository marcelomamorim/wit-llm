/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int,double) method
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertDoubleAtZero();
        testInsertDoubleAtMiddle();
        testInsertDoubleAtEnd();
        testInsertDoubleEmptyBuilder();
    }

    static void testInsertDoubleAtZero() {
        MyStringBuilder sb = new MyStringBuilder("Hello");
        sb.insert(0, 3.14);
        String result = sb.toString();
        if (!result.equals("3.14Hello")) {
            throw new Error("testInsertDoubleAtZero failed: got '" + result + "'");
        }
    }

    static void testInsertDoubleAtMiddle() {
        MyStringBuilder sb = new MyStringBuilder("HelloWorld");
        sb.insert(5, 2.718);
        String result = sb.toString();
        if (!result.equals("Hello2.718World")) {
            throw new Error("testInsertDoubleAtMiddle failed: got '" + result + "'");
        }
    }

    static void testInsertDoubleAtEnd() {
        MyStringBuilder sb = new MyStringBuilder("Test");
        sb.insert(sb.length(), 0.0);
        String result = sb.toString();
        if (!result.equals("Test0.0")) {
            throw new Error("testInsertDoubleAtEnd failed: got '" + result + "'");
        }
    }

    static void testInsertDoubleEmptyBuilder() {
        MyStringBuilder sb = new MyStringBuilder("");
        sb.insert(0, -123.456);
        String result = sb.toString();
        if (!result.equals("-123.456")) {
            throw new Error("testInsertDoubleEmptyBuilder failed: got '" + result + "'");
        }
    }

    // Minimal concrete subclass to test AbstractStringBuilder
    static class MyStringBuilder extends java.lang.AbstractStringBuilder {

        MyStringBuilder(String s) {
            super(s.length());
            for (int i = 0; i < s.length(); i++) {
                this.append(s.charAt(i));
            }
        }

        MyStringBuilder() {
            super();
        }

        MyStringBuilder(int capacity) {
            super(capacity);
        }

        @Override
        public java.lang.String toString() {
            // Use the public String constructor that takes AbstractStringBuilder
            return new String(this);
        }

        @Override
        public java.lang.AbstractStringBuilder append(char c) {
            if (count == value.length) {
                expandCapacity(count + 1);
            }
            if (coder == LATIN1) {
                value[count++] = (byte) c;
            } else {
                // UTF16 encoding
                // This simplified implementation only supports BMP chars
                int i = count << 1;
                if (i + 2 > value.length) {
                    expandCapacity(count + 1);
                }
                value[i] = (byte) (c >>> 8);
                value[i + 1] = (byte) c;
                count++;
            }
            return this;
        }

        private void expandCapacity(int minimumCapacity) {
            int newCapacity = (value.length * 2) + 2;
            if (newCapacity < minimumCapacity) {
                newCapacity = minimumCapacity;
            }
            byte[] newValue = new byte[newCapacity];
            System.arraycopy(value, 0, newValue, 0, count);
            value = newValue;
        }
    }
}
