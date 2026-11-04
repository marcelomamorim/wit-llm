/*
 * @test
 * @summary Test for ObjectOutputStream.putFields() method behavior.
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testPutFieldsNormal();
        testPutFieldsWhenCurPutIsNullAndCurContextIsNull();
    }

    private static void testPutFieldsNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            PutField pf = oos.putFields();
            if (pf == null) {
                throw new AssertionError("Expected non-null PutField");
            }
        } finally {
            oos.close();
        }
    }

    private static void testPutFieldsWhenCurPutIsNullAndCurContextIsNull() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos) {
                @Override
                public PutField putFields() throws IOException {
                    // Force curPut and curContext to null to simulate the condition
                    this.curPut = null;
                    this.curContext = null;
                    return super.putFields();
                }
            };
            try {
                oos.putFields();
                throw new AssertionError("Expected NotActiveException to be thrown");
            } catch (java.io.NotActiveException e) {
                // Expected exception
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
