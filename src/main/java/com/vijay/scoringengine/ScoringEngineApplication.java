package com.vijay.scoringengine;

import com.vijay.scoringengine.service.MATScoreEngine;
import com.vijay.scoringengine.service.impl.DataLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScoringEngineApplication implements CommandLineRunner {

    @Autowired
    private DataLoadService dataLoadService;

    @Autowired
    private MATScoreEngine scanScoreEngine;

    public static void main(String[] args) {
        SpringApplication.run(ScoringEngineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("In Score Engine..");
        dataLoadService.loadTestData();
        System.out.println("Data Loaded...");
        int score = scanScoreEngine.calculateScanScore(MATScoreEngine.MTA, 1909,"Associate_Search.ear");
        System.out.println("Score ==> " + score);
    }
}
