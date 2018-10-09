package implementation;

import interfaces.ISourceReader;

import java.io.BufferedReader;
import java.io.Reader;

public class SourceReader extends BufferedReader implements ISourceReader {
    public SourceReader(Reader in, int sz) {
        super(in, sz);
    }

    public SourceReader(Reader in) {
        super(in);
    }
}
