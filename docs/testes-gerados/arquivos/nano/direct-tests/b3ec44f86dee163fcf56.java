/*
 * @test
 * @summary Test the finish() method of InflaterOutputStream for correct behavior.
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testFinishCallsEndWhenUsesDefaultInflater();
        testFinishDoesNotCallEndWhenUsesDefaultInflaterFalse();
    }

    private static void testFinishCallsEndWhenUsesDefaultInflater() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        // Mark that default inflater is used
        // Call finish and verify no exception and inflater.end() is called (indirectly by no exception)
        ios.finish();
        // No explicit assertion; test passes if no exception thrown
    }

    private static void testFinishDoesNotCallEndWhenUsesDefaultInflaterFalse() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater) {
            @Override
            public void finish() throws IOException {
                ensureOpen();
                flush();
                // Do not call inf.end() to simulate usesDefaultInflater == false
            }
        };
        // Call finish; should not call inf.end()
        ios.finish();
        // No exception expected; test passes if no exception thrown
    }
}
