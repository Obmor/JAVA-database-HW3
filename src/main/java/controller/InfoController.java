package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
public class InfoController {
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getServerPort() {
        return serverPort;
    }

    @GetMapping("/sum")
    public int getSum() {
        var start = System.currentTimeMillis();
        int sum = IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .sum();

        var end = System.currentTimeMillis() - start;
        logger.info("Elapsed time: {} ms", end);

        return sum;
    }
}
