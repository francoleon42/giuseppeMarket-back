package com.nameproyect.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.nameproyect.repository.IPingRepository;
import com.nameproyect.service.IPingService;

@Service
@RequiredArgsConstructor
public class PingServiceImpl implements IPingService {
    private final IPingRepository pingRepository;

    @Override
    public String getPong() {
        return pingRepository.getPong();
    }
}
