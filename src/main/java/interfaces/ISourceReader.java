package interfaces;

import java.io.IOException;

public interface ISourceReader {
    void close() throws IOException;

    int read() throws IOException;
}
