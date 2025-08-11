package com.hmdm.rest.resource;

import com.hmdm.persistence.UsageStatsDAO;
import com.hmdm.persistence.domain.UsageStats;
import com.hmdm.rest.json.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * A REST controller providing interface to usage statistics collection functionality.
 */
@RestController
@RequestMapping("/public/stats")
public class StatsResource {

    private static final Logger logger = LoggerFactory.getLogger(StatsResource.class);

    private final UsageStatsDAO usageStatsDAO;

    @Autowired
    public StatsResource(UsageStatsDAO usageStatsDAO) {
        this.usageStatsDAO = usageStatsDAO;
    }

    @PutMapping
    public Response saveStats(@RequestBody UsageStats usageStats) {
        usageStatsDAO.insertUsageStats(usageStats);
        return Response.OK();
    }
}
