package com.volka.threadpool.controller;

import com.volka.threadpool.dto.Account;
import com.volka.threadpool.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pay")
@RestController
public class PayController {

    private final PayService payService;

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable("id") long id) {
        return payService.getAccount(id);
    }

    @PostMapping
    public Account deposit(@RequestBody Account account) {
        return payService.deposit(account);
    }

    @PatchMapping
    public Account withDraw(@RequestBody Account account) {
        return payService.withDraw(account);
    }

    @DeleteMapping
    public String deleteSome(@PathVariable long id) {
        return payService.deleteSome(id);
    }
}
