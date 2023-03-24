package com.vijay.scoringengine.repostory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import com.vijay.scoringengine.entity.RuleGroup;
import com.vijay.scoringengine.repository.RuleGroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ComponentScan("com.vijay.scoringengine")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:scoring",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.show-sql=true",
        "spring.datasource.initialization-mode=always",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
})
public class RuleGroupRepositoryTest {

    @Autowired
    private RuleGroupRepository ruleGroupRepository;

    @Test
    @Sql(scripts = {"/data.sql"})
    public void testFindAll() {
        List<RuleGroup> ruleGroups = ruleGroupRepository.findAll();
        assertNotNull(ruleGroups);
        assertEquals(2, ruleGroups.size());
    }

    @Test
    @Sql(scripts = {"/data.sql"})
    public void findByGroupId() {
        RuleGroup ruleGroup = ruleGroupRepository.findByGroupId("CLOUD_CACHING");
        assertNotNull(ruleGroup);
        assertEquals("CLOUD_CACHING", ruleGroup.getGroupId());
    }

    @Test
    public void testSave() {
        RuleGroup ruleGroup = RuleGroup.builder()
                .groupId("TEST_GROUP")
                .groupName("Test Group")
                .groupDesc("Test Group Description")
                .severity(3)
                .category("Test Category")
                .build();

        ruleGroup = ruleGroupRepository.save(ruleGroup);
        assertNotNull(ruleGroup);
        assertEquals("TEST_GROUP", ruleGroup.getGroupId());
    }
}
