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

    public Account findById(long id) throws InterruptedException {
        Thread.sleep((long) Math.random() * 100L);
        return table.get(id) == null ? new Account(id) : table.get(id);
    }

    public Account change(Account account) throws InterruptedException {
        Thread.sleep((long) Math.random() * 100L);

        if (table.get(account.getId()) == null) {
            table.put(account.getId(), account);
        } else {
            table.replace(account.getId(), account);
        }


        return account;
    }
}
