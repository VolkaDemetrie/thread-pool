package com.volka.threadpool.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Account {
    private long id;
    private long amount;
    private long millis;
    private long nano;

    public Account(long id) {
        this.id = id;
        this.amount = 0L;
        this.millis = System.currentTimeMillis();
        this.nano = System.nanoTime();
    }

    public Account(long id, long amount) {
        this.id = id;
        this.amount = amount;
        this.millis = System.currentTimeMillis();
        this.nano = System.nanoTime();
    }
}
