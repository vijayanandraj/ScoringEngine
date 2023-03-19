package com.vijay.scoringengine.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rules")
public class Rule {
    @Id
    @Column(name = "rule_id")
    private String ruleId;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private RuleGroup ruleGroup;

    // getters and setters
}