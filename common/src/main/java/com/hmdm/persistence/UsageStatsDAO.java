package com.hmdm.persistence;

import com.hmdm.persistence.domain.UsageStats;
import com.hmdm.persistence.mapper.UsageStatsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsageStatsDAO {

    private final UsageStatsMapper mapper;

    public void insertUsageStats(UsageStats usageStats) {
        mapper.insert(usageStats);
    }
}
