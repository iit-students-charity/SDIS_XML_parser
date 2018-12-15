package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reader {
    private static final String NEW_LINE = "\n";

    public static String readFileByPath(String path) throws URISyntaxException, IOException {
        Path pathToFile = Paths.get(Reader.class.getClassLoader().getResource(path).toURI());

        Stream<String> lines = Files.lines(pathToFile);
        String data = lines.collect(Collectors.joining(NEW_LINE));
        lines.close();

        return data;
    }

    public static String readFileByStream(InputStream inputStream) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }
}
