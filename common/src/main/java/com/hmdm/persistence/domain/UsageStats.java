package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * Other instances usage stats.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsageStats implements Serializable {

    private static final long serialVersionUID = 5087620848766943386L;

    private Integer id;
    private long ts;
    private String instanceId;
    private String webVersion;
    private boolean community;
    private int devicesTotal;
    private int devicesOnline;
    private int cpuTotal;
    private int cpuUsed;
    private int ramTotal;
    private int ramUsed;
    private String scheme;
    private String arch;
    private String os;

    // Custom method retained (Lombok won't generate this)
    public String toJsonString() {
        return "{" +
                "\"ts\":" + ts + "," +
                "\"instanceId\":\"" + instanceId + "\"," +
                "\"webVersion\":\"" + webVersion + "\"," +
                "\"community\":" + community + "," +
                "\"devicesTotal\":" + devicesTotal + "," +
                "\"devicesOnline\":" + devicesOnline + "," +
                "\"cpuTotal\":" + cpuTotal + "," +
                "\"cpuUsed\":" + cpuUsed + "," +
                "\"ramTotal\":" + ramTotal + "," +
                "\"ramUsed\":" + ramUsed + "," +
                "\"scheme\":\"" + scheme + "\"," +
                "\"arch\":\"" + arch + "\"," +
                "\"os\":\"" + os + "\"" +
                "}";
    }
}
