package com.easybid.bids;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidsService {
    
    private final BidsRepository bidsRepository;

}
