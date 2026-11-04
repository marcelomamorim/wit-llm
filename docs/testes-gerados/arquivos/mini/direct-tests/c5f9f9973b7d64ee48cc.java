/*
 * @test
 * @summary Test java.util.Scanner.tokens() method basic behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.util.stream.Stream;
import java.util.Iterator;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        // Test 1: tokens() on a simple string input
        Scanner sc1 = new Scanner("a b c");
        Stream<String> tokens1 = sc1.tokens();
        Iterator<String> it1 = tokens1.iterator();
        if (!it1.hasNext() || !"a".equals(it1.next()))
            throw new RuntimeException("Test1 failed: first token");
        if (!it1.hasNext() || !"b".equals(it1.next()))
            throw new RuntimeException("Test1 failed: second token");
        if (!it1.hasNext() || !"c".equals(it1.next()))
            throw new RuntimeException("Test1 failed: third token");
        if (it1.hasNext())
            throw new RuntimeException("Test1 failed: unexpected extra token");
        tokens1.close();

        // Test 2: tokens() stream closes the Scanner
        Scanner sc2 = new Scanner("x y");
        Stream<String> tokens2 = sc2.tokens();
        tokens2.close();
        try {
            sc2.hasNext();
            throw new RuntimeException("Test2 failed: Scanner should be closed after tokens stream close");
        } catch (IllegalStateException ise) {
            // expected
        }

        // Test 3: tokens() on empty input
        Scanner sc3 = new Scanner("");
        Stream<String> tokens3 = sc3.tokens();
        if (tokens3.findAny().isPresent())
            throw new RuntimeException("Test3 failed: tokens stream should be empty");
        tokens3.close();

        // Test 4: tokens() on input with multiple whitespace
        Scanner sc4 = new Scanner("  foo   bar\tbaz\n");
        Stream<String> tokens4 = sc4.tokens();
        Iterator<String> it4 = tokens4.iterator();
        if (!it4.hasNext() || !"foo".equals(it4.next()))
            throw new RuntimeException("Test4 failed: first token");
        if (!it4.hasNext() || !"bar".equals(it4.next()))
            throw new RuntimeException("Test4 failed: second token");
        if (!it4.hasNext() || !"baz".equals(it4.next()))
            throw new RuntimeException("Test4 failed: third token");
        if (it4.hasNext())
            throw new RuntimeException("Test4 failed: unexpected extra token");
        tokens4.close();

        System.out.println("ScannerWitupTest passed");
    }
}
