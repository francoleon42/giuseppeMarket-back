package com.giuseppemarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.giuseppemarket.repository.IPingRepository;
import com.giuseppemarket.service.IPingService;

@Service
@RequiredArgsConstructor
public class PingServiceImpl implements IPingService {
    private final IPingRepository pingRepository;

    @Override
    public String getPong() {
        return pingRepository.getPong();
    }
}
