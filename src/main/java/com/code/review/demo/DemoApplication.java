package com.code.review.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {
    private final JdbcTemplate jdbcTemplate;
    private final DemoService demoService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    public void run(String... args) throws Exception {
        String sql = "INSERT INTO hashes (number) VALUES (?)";

        var hashes = IntStream.range(0, 1_000_000).boxed().toList();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int hash = hashes.get(i);
                ps.setInt(1, hash);
            }

            @Override
            public int getBatchSize() {
                return hashes.size();
            }
        });

        log.info("init completed");
        long starts = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            completableFutures.add(CompletableFuture.runAsync(this::test, executorService));
        }
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));
        allTasks.join();
        log.info("Total errors: {}", DemoService.getAtomicInteger().get());
        log.info("time (min): {}", (System.currentTimeMillis() - starts) / 1000 / 60);
    }

    private void test() {
        log.info("start");
        for (int i = 0; i < 10_000; i++) {
            demoService.test();
        }
        log.info("finish");
    }

    //  | 839361 errors | 1 min
    // FOR UPDATE SKIP LOCKED | 0 errors | 8 min
    // order by random() | очень долго min
}
