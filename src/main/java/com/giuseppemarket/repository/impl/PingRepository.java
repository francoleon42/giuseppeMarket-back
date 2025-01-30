package com.giuseppemarket.repository.impl;

import org.springframework.stereotype.Service;
import com.giuseppemarket.repository.IPingRepository;

@Service
public class PingRepository implements IPingRepository {
    @Override
    public String getPong() {
        return "pong";
    }
}
