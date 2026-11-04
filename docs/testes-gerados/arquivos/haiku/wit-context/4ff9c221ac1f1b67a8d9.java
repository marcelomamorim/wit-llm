/*
 * @test
 * @summary Witup-derived tests for PushbackInputStream constructor size validation and unread ordering
 * @run main PushbackInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        checkRejectsSize(0);
        checkRejectsSize(-1);
        checkAcceptsSizeOne();
        checkUnreadOrder();
    }

    static void checkRejectsSize(int size) {
        try {
            new PushbackInputStream(new ByteArrayInputStream(new byte[] {1}), size);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for size " + size);
    }

    static void checkAcceptsSizeOne() throws IOException {
        PushbackInputStream s = new PushbackInputStream(new ByteArrayInputStream(new byte[] {5}));
        expect(s.read(), 5);
        expect(s.read(), -1);
        s.unread(4);
        expect(s.read(), 4);
    }

    static void checkUnreadOrder() throws IOException {
        PushbackInputStream s = new PushbackInputStream(new ByteArrayInputStream(new byte[] {1, 2, 3}), 2);
        expect(s.read(), 1);
        s.unread(7);
        s.unread(8);
        expect(s.read(), 8);
        expect(s.read(), 7);
        expect(s.read(), 2);
        expect(s.read(), 3);
        expect(s.read(), -1);
    }

    static void expect(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
