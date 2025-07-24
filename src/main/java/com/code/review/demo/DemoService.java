package com.code.review.demo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void test() {
        try {
            Integer removed = jdbcTemplate.queryForObject("delete from hashes where id in (select id from hashes FOR UPDATE SKIP LOCKED limit 1) returning number", Integer.class);
        } catch (Exception ex) {
            log.error("error", ex);
            atomicInteger.incrementAndGet();
        }
    }
}
