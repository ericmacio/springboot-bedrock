package emk_bedrock.chat_request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class AwsEvent<T> {
    @JsonProperty("detail")
    private T detail = null;

    @JsonProperty("detail-type")
    private String detailType = null;

    @JsonProperty("resources")
    private List resources = null;

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("source")
    private String source = null;

    @JsonProperty("time")
    private Date time = null;

    @JsonProperty("region")
    private String region = null;

    @JsonProperty("version")
    private String version = null;

    @JsonProperty("account")
    private String account = null;

    public AwsEvent<T> detail(T detail) {
        this.detail = detail;
        return this;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public AwsEvent<T> detailType(String detailType) {
        this.detailType = detailType;
        return this;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public AwsEvent<T> resources(List resources) {
        this.resources = resources;
        return this;
    }

    public List getResources() {
        return resources;
    }

    public void setResources(List resources) {
        this.resources = resources;
    }

    public AwsEvent<T> id(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AwsEvent<T> source(String source) {
        this.source = source;
        return this;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public AwsEvent<T> time(Date time) {
        this.time = time;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AwsEvent<T> region(String region) {
        this.region = region;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public AwsEvent<T> version(String version) {
        this.version = version;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AwsEvent<T> account(String account) {
        this.account = account;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}

