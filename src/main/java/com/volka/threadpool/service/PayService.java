package com.volka.threadpool.service;

import com.volka.threadpool.database.Database;
import com.volka.threadpool.dto.Account;
import com.volka.threadpool.task.TaskBlockingQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.FutureTask;

@Slf4j
@RequiredArgsConstructor
@Service
public class PayService {

    public static final String RESPONSE_FAIL = "FAIL";
    public static final String RESPONSE_SUCCESS = "SUCCESS";

    private final Database database;
    private final TaskBlockingQueue taskBlockingQueue;

    public Account getAccount(long id) {
        return database.findById(id);
    }


    @Transactional
    public Account deposit(Account account) {

    }

    @Transactional
    public Account withDraw(Account account) {

    }


    @Transactional
    public String deleteSome(long id) {
        try {

            return RESPONSE_SUCCESS;
        } catch (Exception e) {
            log.error("ERROR :: {}", e);
            return RESPONSE_FAIL;
        }
    }
}
