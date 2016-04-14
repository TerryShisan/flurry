package org.mine.findblacklist;

import org.apache.spark.storage.StorageLevel;
import org.springframework.xd.module.options.spi.ModuleOption;
import org.springframework.xd.module.options.spi.ProfileNamesProvider;

import javax.validation.constraints.AssertTrue;

public class OptionsMeta   {
    private  String blackName="";
    private  String listFileName="";

    private String moduleExecutionFramework = "spark";
    private String batchInterval;
    private String storageLevel = "";
    private boolean enableTap = false;

    public OptionsMeta() {
    }

    @ModuleOption("the time interval in millis for batching the stream events")
    public void setBatchInterval(String batchInterval) {
        this.batchInterval = batchInterval;
    }

    public String getBatchInterval() {
        return this.batchInterval;
    }

    @ModuleOption("the storage level for spark streaming RDD persistence")
    public void setStorageLevel(String storageLevel) {
        this.storageLevel = storageLevel;
    }

    public String getStorageLevel() {
        return this.storageLevel;
    }

    @AssertTrue(
            message = "Use a valid storage level option. See org.apache.spark.storage.StorageLevel"
    )
    public boolean isSparkStorageValid() {
        if(this.storageLevel.length() == 0) {
            return true;
        } else {
            try {
                StorageLevel.fromString(this.storageLevel);
                return true;
            } catch (IllegalArgumentException var2) {
                return false;
            }
        }
    }

    @ModuleOption("enable tap at the output of the spark processor module")
    public void setEnableTap(boolean enableTap) {
        this.enableTap = enableTap;
    }

    public boolean isEnableTap() {
        return this.enableTap;
    }

    @ModuleOption(
            value = "the underlying execution framework",
            hidden = true
    )
    public String getModuleExecutionFramework() {
        return this.moduleExecutionFramework;
    }

    public String getBlackName() {
        return blackName;
    }

    @ModuleOption("the black name")
    public void setBlackName(String blackName) {
        this.blackName = blackName;
    }

    public String getListFileName() {
        return listFileName;
    }

    @ModuleOption("the black list file name")
    public void setListFileName(String listFileName) {
        this.listFileName = listFileName;
    }
}
