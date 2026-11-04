/* @test    @summary Test StringTokenizer.nextToken() method for correct token extraction and exception handling.    @run main StringTokenizerWitupTest */

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class StringTokenizerWitupTest {

    public static void main(String[] args) {
        testNextTokenBasic();
        testNextTokenAfterExhaustion();
        testNextTokenWithDelimiters();
        testNextTokenWithEmptyString();
        testNextTokenWithNullString();
    }

    static void testNextTokenBasic() {
        String str = "a,b,c";
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        String token1 = tokenizer.nextToken();
        String token2 = tokenizer.nextToken();
        String token3 = tokenizer.nextToken();
        assert token1.equals("a") : "Expected 'a', got '" + token1 + "'";
        assert token2.equals("b") : "Expected 'b', got '" + token2 + "'";
        assert token3.equals("c") : "Expected 'c', got '" + token3 + "'";
    }

    static void testNextTokenAfterExhaustion() {
        String str = "x";
        StringTokenizer tokenizer = new StringTokenizer(str);
        tokenizer.nextToken();
        boolean exceptionThrown = false;
        try {
            tokenizer.nextToken();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException after tokens exhausted";
    }

    static void testNextTokenWithDelimiters() {
        String str = "a,b;c";
        StringTokenizer tokenizer = new StringTokenizer(str, ",;", true);
        String t1 = tokenizer.nextToken();
        String t2 = tokenizer.nextToken();
        String t3 = tokenizer.nextToken();
        String t4 = tokenizer.nextToken();
        String t5 = tokenizer.nextToken();
        assert t1.equals("a") : "Expected 'a', got '" + t1 + "'";
        assert t2.equals(",") : "Expected ',', got '" + t2 + "'";
        assert t3.equals("b") : "Expected 'b', got '" + t3 + "'";
        assert t4.equals(";") : "Expected ';', got '" + t4 + "'";
        assert t5.equals("c") : "Expected 'c', got '" + t5 + "'";
    }

    static void testNextTokenWithEmptyString() {
        String str = "";
        StringTokenizer tokenizer = new StringTokenizer(str);
        boolean exceptionThrown = false;
        try {
            tokenizer.nextToken();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NoSuchElementException for empty string";
    }

    static void testNextTokenWithNullString() {
        boolean exceptionThrown = false;
        try {
            StringTokenizer tokenizer = new StringTokenizer(null);
            tokenizer.nextToken();
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NullPointerException for null string";
    }
}
