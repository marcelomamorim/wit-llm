/* @test    @summary Test for OutputStream.write(byte[])
 * @run main OutputStreamWitupTest
 */

import java.io.OutputStream;
import java.io.IOException;

public class OutputStreamWitupTest {

    public static void main(String[] args) {
        testWriteByteArrayNormal();
        testWriteByteArrayNull();
        testWriteByteArrayEmpty();
        testWriteByteArrayOutOfBounds();
    }

    static void testWriteByteArrayNormal() {
        OutputStream os = new OutputStream() {
            boolean written = false;
            @Override
            public void write(byte[] b) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
                written = true;
            }
        };
        try {
            os.write(new byte[]{1, 2, 3});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testWriteByteArrayNull() {
        OutputStream os = new OutputStream() {
            @Override
            public void write(byte[] b) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
            }
        };
        try {
            os.write(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testWriteByteArrayEmpty() {
        OutputStream os = new OutputStream() {
            boolean called = false;
            @Override
            public void write(byte[] b) throws IOException {
                if (b.length != 0) {
                    throw new AssertionError("Array not empty");
                }
                called = true;
            }
        };
        try {
            os.write(new byte[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void testWriteByteArrayOutOfBounds() {
        byte[] data = new byte[]{1, 2, 3};
        OutputStream os = new OutputStream() {
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                if (b == null) {
                    throw new NullPointerException();
                }
                if (off < 0 || len < 0 || off + len > b.length) {
                    throw new IndexOutOfBoundsException();
                }
            }
        };
        try {
            os.write(data, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            os.write(data, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            os.write(data, 2, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for out of bounds");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
