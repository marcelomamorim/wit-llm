/*
 * @test
 * @summary Tests for java.util.stream.Stream forEachRemaining method
 * @run main StreamWitupTest
 */

import java.util.stream.Stream;
import java.util.function.Consumer;

public class StreamWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithNullAction();
        testForEachRemainingWithNullActionAndFinished();
        testForEachRemainingWithNullActionAndStarted();
    }

    private static void testForEachRemainingWithNullAction() {
        Stream<Object> stream = Stream.of(1, 2, 3);
        try {
            stream.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null action");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithNullActionAndFinished() {
        Stream<Object> stream = Stream.of(1, 2, 3);
        // Use reflection to set 'finished' to true to simulate already finished state
        try {
            java.lang.reflect.Field finishedField = Stream.class.getDeclaredField("finished");
            finishedField.setAccessible(true);
            finishedField.setBoolean(stream, true);
            stream.forEachRemaining(new Consumer<Object>() {
                public void accept(Object t) {}
            });
            System.out.println("PASS: No exception when finished is true");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("NOTE: Reflection failed, cannot set finished flag: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testForEachRemainingWithNullActionAndStarted() {
        Stream<Object> stream = Stream.of(1, 2, 3);
        // Use reflection to set 'started' to true
        try {
            java.lang.reflect.Field startedField = Stream.class.getDeclaredField("started");
            startedField.setAccessible(true);
            startedField.setBoolean(stream, true);
            stream.forEachRemaining(new Consumer<Object>() {
                public void accept(Object t) {}
            });
            System.out.println("PASS: No exception when started is true");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("NOTE: Reflection failed, cannot set started flag: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
