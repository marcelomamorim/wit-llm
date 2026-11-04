/*
 * @test
 * @summary Verifies Writer.write(int) delivers the char to write(char[],int,int) and that nullWriter's write(int) throws IOException after close
 * @run main WriterWitupTest
 */

import java.io.IOException;
import java.io.Writer;

public class WriterWitupTest {

    static class Capture extends Writer {
        final StringBuilder sb = new StringBuilder();

        @Override
        public void write(char[] cbuf, int off, int len) {
            sb.append(cbuf, off, len);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }

    public static void main(String[] args) throws Exception {
        Capture c = new Capture();
        c.write('x');
        c.write(0x41);
        if (!c.sb.toString().equals("xA")) {
            throw new AssertionError("Expected 'xA' but got '" + c.sb + "'");
        }

        Writer w = Writer.nullWriter();
        w.write('a');
        w.close();
        w.close();
        try {
            w.write('b');
            throw new AssertionError("write(int) after close() should throw IOException");
        } catch (IOException expected) {
        }
    }
}
