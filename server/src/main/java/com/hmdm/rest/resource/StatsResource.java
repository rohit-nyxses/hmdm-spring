package com.hmdm.rest.resource;

import com.hmdm.persistence.UsageStatsDAO;
import com.hmdm.persistence.domain.UsageStats;
import com.hmdm.rest.json.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * A REST controller providing interface to usage statistics collection functionality.
 */
@Slf4j
@RestController
@RequestMapping("/public/stats")
@RequiredArgsConstructor
public class StatsResource {

    private final UsageStatsDAO usageStatsDAO;

    @PutMapping
    public Response saveStats(@RequestBody UsageStats usageStats) {
        log.info("Saving usage stats: {}", usageStats);
        usageStatsDAO.insertUsageStats(usageStats);
        return Response.OK();
    }
}
