package com.volka.threadpool.database;

import com.volka.threadpool.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Component
public class Database {
    private Map<Long, Account> table = new HashMap<>();
    public Account findById(long id) {
        try {
            Thread.sleep((long) Math.random() * 100L);
            return table.get(id);
        } catch (InterruptedException e) {
            log.error("find ERROR :: ", e);
            throw new RuntimeException("ERROR");
        }
    }

    public Account change(Account account) {
        return table.putIfAbsent(account.getId(), account);
    }
}
