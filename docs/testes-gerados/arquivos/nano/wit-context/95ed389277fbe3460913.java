/*
 * @test
 * @summary Test for CharBufferSpliterator.forEachRemaining method handling null action
 * @run main CharBufferSpliteratorWitupTest
 */

import java.nio.CharBuffer;
import java.util.function.IntConsumer;

public class CharBufferSpliteratorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingValidAction();
    }

    private static void testForEachRemainingNullAction() {
        CharBuffer buffer = CharBuffer.allocate(10);
        buffer.position(0);
        buffer.limit(10);
        java.nio.CharBufferSpliterator spliterator = new java.nio.CharBufferSpliterator(buffer);
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingValidAction() {
        CharBuffer buffer = CharBuffer.allocate(5);
        buffer.put(new char[] { 'a', 'b', 'c', 'd', 'e' });
        buffer.position(0);
        buffer.limit(5);
        java.nio.CharBufferSpliterator spliterator = new java.nio.CharBufferSpliterator(buffer);
        StringBuilder sb = new StringBuilder();
        IntConsumer consumer = sb::append;
        spliterator.forEachRemaining(consumer);
        String result = sb.toString();
        if ("abcde".equals(result)) {
            System.out.println("PASS: forEachRemaining processed buffer correctly");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }
}
