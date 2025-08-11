package com.hmdm.persistence;

import com.hmdm.persistence.domain.UsageStats;
import com.hmdm.persistence.mapper.UsageStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsageStatsDAO {

    private final UsageStatsMapper mapper;

    @Autowired
    public UsageStatsDAO(UsageStatsMapper mapper) {
        this.mapper = mapper;
    }

    public void insertUsageStats(UsageStats usageStats) {
        mapper.insert(usageStats);
    }
}
