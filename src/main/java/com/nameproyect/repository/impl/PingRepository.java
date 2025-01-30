package com.nameproyect.repository.impl;

import org.springframework.stereotype.Service;
import com.nameproyect.repository.IPingRepository;

@Service
public class PingRepository implements IPingRepository {
    @Override
    public String getPong() {
        return "pong";
    }
}
