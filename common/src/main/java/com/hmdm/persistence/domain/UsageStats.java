package com.hmdm.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * Other instances usage stats.
 */
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

    public UsageStats() {
    }

    // Getters and setters
    // (kept as-is for MyBatis compatibility)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public long getTs() { return ts; }
    public void setTs(long ts) { this.ts = ts; }
    public String getInstanceId() { return instanceId; }
    public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
    public String getWebVersion() { return webVersion; }
    public void setWebVersion(String webVersion) { this.webVersion = webVersion; }
    public boolean isCommunity() { return community; }
    public void setCommunity(boolean community) { this.community = community; }
    public int getDevicesTotal() { return devicesTotal; }
    public void setDevicesTotal(int devicesTotal) { this.devicesTotal = devicesTotal; }
    public int getDevicesOnline() { return devicesOnline; }
    public void setDevicesOnline(int devicesOnline) { this.devicesOnline = devicesOnline; }
    public int getCpuTotal() { return cpuTotal; }
    public void setCpuTotal(int cpuTotal) { this.cpuTotal = cpuTotal; }
    public int getCpuUsed() { return cpuUsed; }
    public void setCpuUsed(int cpuUsed) { this.cpuUsed = cpuUsed; }
    public int getRamTotal() { return ramTotal; }
    public void setRamTotal(int ramTotal) { this.ramTotal = ramTotal; }
    public int getRamUsed() { return ramUsed; }
    public void setRamUsed(int ramUsed) { this.ramUsed = ramUsed; }
    public String getScheme() { return scheme; }
    public void setScheme(String scheme) { this.scheme = scheme; }
    public String getArch() { return arch; }
    public void setArch(String arch) { this.arch = arch; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UsageStats{" +
                "id=" + id +
                ", ts=" + ts +
                ", instanceId='" + instanceId + '\'' +
                ", webVersion='" + webVersion + '\'' +
                ", community=" + community +
                ", devicesTotal=" + devicesTotal +
                ", devicesOnline=" + devicesOnline +
                ", cpuTotal=" + cpuTotal +
                ", cpuUsed=" + cpuUsed +
                ", ramTotal=" + ramTotal +
                ", ramUsed=" + ramUsed +
                ", scheme='" + scheme + '\'' +
                ", arch='" + arch + '\'' +
                ", os='" + os + '\'' +
                '}';
    }

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
