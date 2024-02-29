package com.volka.threadpool.task;

import com.volka.threadpool.dto.Producer;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

@Component
public class TaskBlockingQueue extends ArrayBlockingQueue<Callable<Producer>> {

    private static final int INIT_CAPACITY = 1000;
    private static final boolean INIT_FAIR = true;

    public TaskBlockingQueue() {
        super(INIT_CAPACITY, INIT_FAIR);
    }

    public TaskBlockingQueue(int capacity) {
        super(capacity);
    }

    public TaskBlockingQueue(int capacity, boolean fair) {
        super(capacity, fair);
    }

    public TaskBlockingQueue(int capacity, boolean fair, Collection c) {
        super(capacity, fair, c);
    }
}
