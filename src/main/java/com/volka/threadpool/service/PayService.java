package com.volka.threadpool.service;

import com.volka.threadpool.database.Database;
import com.volka.threadpool.dto.Account;
import com.volka.threadpool.exception.BizException;
import com.volka.threadpool.task.TaskThreadPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class PayService {

    public static final String RESPONSE_FAIL = "FAIL";
    public static final String RESPONSE_SUCCESS = "SUCCESS";

    private final TaskThreadPool taskThreadPool;

    private final Database database;

    public Account getAccount(long id) {
        try {
            CompletableFuture<Account> result = CompletableFuture.supplyAsync(() -> {
                try {
                    return database.findById(id);
                } catch (InterruptedException e) {
                    throw new BizException("FAIL", e);
                }
            }, taskThreadPool.getThreadPool());

            return result.get();
        } catch (Exception e) {
            throw new BizException("FAIL", e);
        }
    }


    public Account deposit(Account account) {
        try {
            CompletableFuture<Account> result = CompletableFuture.supplyAsync(() -> {
                try {
                    Account record = database.findById(account.getId());
                    account.setAmount(record.getAmount() + account.getAmount());
                    account.setMillis(System.currentTimeMillis());
                    account.setNano(System.nanoTime());

                    return database.change(account);

                } catch (InterruptedException e) {
                    throw new BizException("FAIL", e);
                }
            }, taskThreadPool.getThreadPool());

            return result.get();

        } catch (Exception e) {
            throw new BizException("FAIL", e);
        }
    }

    public Account withDraw(Account account) {
        try {
            CompletableFuture<Account> result = CompletableFuture.supplyAsync(() -> {
                try {
                    Account record = database.findById(account.getId());
                    account.setAmount(record.getAmount() - account.getAmount());
                    account.setMillis(System.currentTimeMillis());
                    account.setNano(System.nanoTime());

                    return database.change(account);

                } catch (InterruptedException e) {
                    throw new BizException("FAIL", e);
                }
            }, taskThreadPool.getThreadPool());

            return result.get();

        } catch (Exception e) {
            throw new BizException("FAIL", e);
        }
    }


    public String deleteSome(long id) {
        try {
            return RESPONSE_SUCCESS;
        } catch (Exception e) {
            log.error("ERROR :: {}", e);
            return RESPONSE_FAIL;
        }
    }
}
