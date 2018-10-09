package parser;


import interfaces.ISourceReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SourceSentenceIteratorTest {
    SourceSentenceIterator iterator;

    private Path currentRelativePath;

    @BeforeEach
    void setUp() {
        currentRelativePath = Paths.get("");
    }

    @AfterEach
    void tearDown() {
        iterator.close();
    }

    private void initIterator(ISourceReader reader) {
        try {
            iterator = new SourceSentenceIterator(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(iterator);
    }

    @Test
    void emptyDataTest() {
        ISourceReader sourceReaderMock = Mockito.mock(ISourceReader.class);
        try {
            when(sourceReaderMock.read()).thenReturn(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initIterator(sourceReaderMock);
        assert iterator.getNextSentence() == null;
    }

    @Test
    void someDataTest() {
        ISourceReader sourceReaderMock = Mockito.mock(ISourceReader.class);
        try {
            when(sourceReaderMock.read()).thenReturn(69, 98, 112, 102, 100, 115, 32, 117, 119, 113, 106, 115, 121, 121, 111,
                    32, 100, 98, 116, 110, 114, 104, 32, 113, 103, 114, 46, 32, 79, 100, 104, 32, 120, 122, 114, 122, 105, 114, 121, 109, 116, 32,
                    115, 32, 103, 112, 98, 121, 118, 105, 32, 104, 32, 107, 104, 105, 101, 46, 32, 71, 119, 121, 46, 32, 79, 112, 113, 98, 112,
                    108, 109, 32, 103, 116, 32, 117, 106, 110, 120, 121, 120, 103, 111, 32, 104, 118, 107, 99, 101, 106, 107, 100, 115, 32, 99,
                    111, 98, 103, 109, 116, 121, 110, 117, 32, 109, 115, 97, 113, 102, 107, 122, 110, 112, 32, 114, 115, 119, 102, 100, 99, 115,
                    113, 105, 32, 115, 110, 46, 32, 78, 104, 100, 112, 102, 113, 32, 107, 106, 105, 107, 111, 97, 120, 32, 113, 46, 32, 67, 101,
                    120, 32, 99, 121, 105, 106, 101, 101, 97, 120, 32, 113, 116, 103, 119, 99, 120, 32, 102, 112, 112, 32, 104, 121, 122, 106,
                    122, 118, 98, 113, 116, 32, 100, 32, 116, 106, 109, 106, 115, 97, 98, 103, 46, 32, 70, 97, 103, 110, 121, 114, 101, 46, 32,
                    87, 32, 116, 46, 32, 72, 102, 109, 32, 102, 111, 104, 32, 110, 104, 108, 114, 104, 100, 108, 97, 106, 32, 99, 102, 116, 118,
                    118, 104, 46, 32, 87, 115, 121, 46, 32, 72, 104, 101, 114, 119, 99, 32, 119, 99, 103, 32, 110, 99, 102, 121, 116, 32, 111,
                    117, 106, 109, 114, 103, 104, 98, 108, 46, 32, 10, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initIterator(sourceReaderMock);
        int count = 0;
        while (iterator.getNextSentence() != null) {
            count++;
        }
        assert count == 11;
    }

    @Test
    void testClosing() {
        ISourceReader sourceReaderMock = Mockito.mock(ISourceReader.class);
        initIterator(sourceReaderMock);
        iterator.close();
        try {
            verify(sourceReaderMock, Mockito.times(1)).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}