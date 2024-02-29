package com.volka.threadpool.dto;

import lombok.Data;

@Data
public class Account {
    private Long id;
    private long amount;
    private long millis;
    private long nano;
}
