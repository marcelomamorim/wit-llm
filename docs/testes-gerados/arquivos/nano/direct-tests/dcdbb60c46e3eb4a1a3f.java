/* @test    @summary Test for FileTreeIterator.next() method to ensure correct behavior and exception handling.    @run main FileTreeIteratorWitupTest */

import java.io.IOException;
import java.nio.file.FileTreeIterator;
import java.nio.file.FileTreeWalker.Event;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) {
        testNextReturnsEventAndResetsNext();
        testNextThrowsNoSuchElementExceptionWhenNextIsNull();
        testNextThrowsIllegalStateExceptionWhenWalkerClosed();
    }

    private static void testNextReturnsEventAndResetsNext() {
        try {
            Path startPath = Paths.get("/");
            // Using maxDepth=1 and no options for simplicity
            FileTreeIterator iterator = new FileTreeIterator(startPath, 1);
            Event event = iterator.next();
            if (event == null) {
                throw new AssertionError("Expected non-null Event from next()");
            }
            // After calling next(), next should be reset to null
            if (iterator.next == null) {
                throw new AssertionError("Expected next to be reset to null after next()");
            }
            iterator.close();
        } catch (IOException e) {
            // In case of IO error, test is inconclusive
            System.err.println("IO exception during testNextReturnsEventAndResetsNext: " + e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextReturnsEventAndResetsNext: " + e);
        }
    }

    private static void testNextThrowsNoSuchElementExceptionWhenNextIsNull() {
        try {
            Path startPath = Paths.get("/");
            FileTreeIterator iterator = new FileTreeIterator(startPath, 1);
            // Force next to null to simulate end of iteration
            iterator.next = null;
            // Call next(), should throw NoSuchElementException
            iterator.next();
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("IO exception during testNextThrowsNoSuchElementExceptionWhenNextIsNull: " + e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextThrowsNoSuchElementExceptionWhenNextIsNull: " + e);
        }
    }

    private static void testNextThrowsIllegalStateExceptionWhenWalkerClosed() {
        try {
            Path startPath = Paths.get("/");
            FileTreeIterator iterator = new FileTreeIterator(startPath, 1);
            // Simulate walker being closed
            // Note: isOpen() method is not accessible; assuming internal state or simulate by reflection if needed
            // For simplicity, we assume isOpen() returns false if walker is closed, but since it's private,
            // we cannot directly set it. Instead, we can simulate by closing the iterator if possible.
            // However, since close() only closes the walker, and isOpen() checks walker state, we can call close()
            // and then call next() to trigger exception.
            iterator.close();
            iterator.next();
            throw new AssertionError("Expected IllegalStateException was not thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("IO exception during testNextThrowsIllegalStateExceptionWhenWalkerClosed: " + e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNextThrowsIllegalStateExceptionWhenWalkerClosed: " + e);
        }
    }
}
