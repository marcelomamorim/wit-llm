/*
 * @test
 * @summary Test readBoolean method of ObjectInputStream for normal and EOF conditions
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) {
        testReadBooleanWithData();
        testReadBooleanEOF();
    }

    static void testReadBooleanWithData() {
        byte[] data = {1}; // non-zero byte
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            boolean result = ois.readBoolean();
            if (result != true) {
                throw new AssertionError("Expected true, got " + result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testReadBooleanEOF() {
        byte[] data = {}; // empty data to trigger EOF
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            ois.readBoolean();
            throw new AssertionError("Expected EOFException to be thrown");
        } catch (EOFException e) {
            // expected exception
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
