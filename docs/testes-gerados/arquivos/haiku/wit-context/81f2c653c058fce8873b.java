/*
 * @test
 * @summary Verify ByteArrayInputStream.readAllBytes returns remaining bytes, copies data, and throws IllegalArgumentException when pos is greater than count
 * @run main ByteArrayInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class ByteArrayInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3};
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        check(Arrays.equals(in.readAllBytes(), new byte[]{1, 2, 3}), "full read");
        check(in.readAllBytes().length == 0, "second call returns empty");
        check(in.read() == -1, "stream at EOF after readAllBytes");

        in = new ByteArrayInputStream(data);
        in.read();
        check(Arrays.equals(in.readAllBytes(), new byte[]{2, 3}), "remaining after one read");

        byte[] five = {1, 2, 3, 4, 5};
        in = new ByteArrayInputStream(five, 1, 3);
        check(Arrays.equals(in.readAllBytes(), new byte[]{2, 3, 4}), "offset and length");

        in = new ByteArrayInputStream(five, 2, 10);
        check(Arrays.equals(in.readAllBytes(), new byte[]{3, 4, 5}), "length clipped to buffer");

        in = new ByteArrayInputStream(new byte[0]);
        check(in.readAllBytes().length == 0, "empty buffer");

        in = new ByteArrayInputStream(five);
        byte[] result = in.readAllBytes();
        result[0] = 9;
        check(five[0] == 1, "returned array is a copy");

        try {
            new ByteArrayInputStream(new byte[2], 3, 1).readAllBytes();
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
