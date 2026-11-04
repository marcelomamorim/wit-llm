/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, float) method
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class SimpleStringBuilder extends java.lang.AbstractStringBuilder {
        SimpleStringBuilder(String s) {
            super(s.length());
            for (int i = 0; i < s.length(); i++) {
                this.append(s.charAt(i));
            }
        }

        @Override
        public java.lang.String toString() {
            return new String(this.value, 0, this.count, this.coder == java.lang.String.LATIN1 ? java.nio.charset.StandardCharsets.ISO_8859_1 : java.nio.charset.StandardCharsets.UTF_16);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("HelloWorld");

        // Insert float at beginning
        sb.insert(0, 3.14f);
        String result1 = sb.toString();
        if (!result1.startsWith("3.14")) {
            throw new Error("Insert float at beginning failed: " + result1);
        }

        // Insert float in middle
        sb = new SimpleStringBuilder("HelloWorld");
        sb.insert(5, 2.718f);
        String result2 = sb.toString();
        if (!result2.substring(5).startsWith("2.718")) {
            throw new Error("Insert float in middle failed: " + result2);
        }

        // Insert float at end
        sb = new SimpleStringBuilder("HelloWorld");
        sb.insert(sb.length(), 0.0f);
        String result3 = sb.toString();
        if (!result3.endsWith("0.0")) {
            throw new Error("Insert float at end failed: " + result3);
        }

        // Insert negative float
        sb = new SimpleStringBuilder("Test");
        sb.insert(2, -123.456f);
        String result4 = sb.toString();
        if (!result4.substring(2).startsWith("-123.456")) {
            throw new Error("Insert negative float failed: " + result4);
        }

        // Insert NaN
        sb = new SimpleStringBuilder("NaNTest");
        sb.insert(3, Float.NaN);
        String result5 = sb.toString();
        if (!result5.substring(3).startsWith("NaN")) {
            throw new Error("Insert NaN failed: " + result5);
        }

        // Insert Infinity
        sb = new SimpleStringBuilder("InfTest");
        sb.insert(3, Float.POSITIVE_INFINITY);
        String result6 = sb.toString();
        if (!result6.substring(3).startsWith("Infinity")) {
            throw new Error("Insert Infinity failed: " + result6);
        }

        System.out.println("All tests passed.");
    }
}
