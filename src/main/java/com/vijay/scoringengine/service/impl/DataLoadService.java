//package com.vijay.scoringengine.service.impl;
//
//import com.vijay.scoringengine.entity.Rule;
//import com.vijay.scoringengine.entity.RuleGroup;
//import com.vijay.scoringengine.entity.ScanData;
//import com.vijay.scoringengine.repository.RuleGroupRepository;
//import com.vijay.scoringengine.repository.RuleRepository;
//import com.vijay.scoringengine.repository.ScanDataRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class DataLoadService {
//
//    @Autowired
//    private RuleGroupRepository ruleGroupRepository;
//
//    @Autowired
//    private RuleRepository ruleRepository;
//
//    @Autowired
//    private ScanDataRepository scanDataRepository;
//
//    public void loadTestData() {
//        RuleGroup cloudCaching = new RuleGroup().builder().groupId("CLOUD_CACHING").groupName("Cloud Caching").groupDesc("Cloud Caching").severity(2).category("CLOUD").build();
//        cloudCaching = ruleGroupRepository.save(cloudCaching);
//        RuleGroup cloudJCA = new RuleGroup().builder().groupId("CLOUD_JCA").groupName("Cloud JCA").groupDesc("Cloud JCA").severity(2).category("CLOUD").build();
//        cloudJCA = ruleGroupRepository.save(cloudJCA);
//        RuleGroup wlCommonJ = new RuleGroup().builder().groupId("WL_COMMONJ").groupName("Weblogic CommonJ").groupDesc("Weblogic CommonJ").severity(2).category("Platform").build();
//        wlCommonJ = ruleGroupRepository.save(wlCommonJ);
//        RuleGroup wsMgmt = new RuleGroup().builder().groupId("WS_MGMT").groupName("Websphere Management").groupDesc("Websphere Management").severity(2).category("Platform").build();
//        wsMgmt = ruleGroupRepository.save(wsMgmt);
//        System.out.println("Rule Groups Loaded...");
//
//        Rule rule1 = new Rule().builder().ruleId("embedded-cache-libraries-01000").title("Caching - ECache Embedded Library").ruleGroup(cloudCaching).build();
//        rule1 = ruleRepository.save(rule1);
//        Rule rule2 = new Rule().builder().ruleId("embedded-cache-libraries-07000").title("Caching - HazelCast Embedded Library").ruleGroup(cloudCaching).build();
//        rule2 = ruleRepository.save(rule2);
//        Rule rule3 = new Rule().builder().ruleId("commonj-0100").title("CommonJ Timer Manager API").ruleGroup(wlCommonJ).build();
//        rule3 = ruleRepository.save(rule3);
//        Rule rule4 = new Rule().builder().ruleId("commonj-0400").title("CommonJ Work Manager API").ruleGroup(wlCommonJ).build();
//        rule4 = ruleRepository.save(rule4);
//        Rule rule5 = new Rule().builder().ruleId("ws-gmt-0100").title("Websphere Management API").ruleGroup(wsMgmt).build();
//        rule5 = ruleRepository.save(rule5);
//        Rule rule6 = new Rule().builder().ruleId("jca-0100").title("JCA API").ruleGroup(cloudJCA).build();
//        rule6 = ruleRepository.save(rule6);
//
//        System.out.println("Rules Loaded...");
//
//
//        ScanData scanData1 = new ScanData().builder().aitId(1909).componentId("Associate_Search.ear").rule(rule1).fileName("CacheServlet.java").build();
//        scanData1 = scanDataRepository.save(scanData1);
//        ScanData scanData2 = new ScanData().builder().aitId(1909).componentId("Associate_Search.ear").rule(rule1).fileName("CacheService.java").build();
//        scanData2 = scanDataRepository.save(scanData2);
//        ScanData scanData3 = new ScanData().builder().aitId(2902).componentId("ABL.ear").rule(rule3).fileName("ABLWorkManager.java").build();
//        scanData3 = scanDataRepository.save(scanData3);
//        ScanData scanData4 = new ScanData().builder().aitId(1909).componentId("Associate_Search.ear").rule(rule6).fileName("1111.java").build();
//        scanData4 = scanDataRepository.save(scanData4);
//        ScanData scanData5 = new ScanData().builder().aitId(1909).componentId("Associate_Search.ear").rule(rule6).fileName("2222.java").build();
//        scanData5 = scanDataRepository.save(scanData5);
//
//
//
//
//
//    }
//
//}
