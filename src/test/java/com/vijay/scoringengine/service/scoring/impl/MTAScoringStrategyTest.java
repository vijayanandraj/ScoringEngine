package com.vijay.scoringengine.service.scoring.impl;


import com.vijay.scoringengine.repository.ScanDataRepository;
import com.vijay.scoringengine.service.scoring.ScoringStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MTAScoringStrategyTest {

    @InjectMocks
    private MTAScoringStrategy mtaScoringStrategy;

    @Mock
    private ScanDataRepository scanDataRepository;



}
