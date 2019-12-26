package cn.itsource.hrm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description TODO
 * @Author 初代火影
 * @Date 2019/12/26 10:11
 * @Version v1.0
 */
@ConfigurationProperties(prefix = "hrm.swagger")
public class SwaggerProperties {
    private String basePackage="cn.itsource.hrm.web.controller";
    private String title;
    private String description;
    private String name="初代火影";
    private String url="";
    private String email="123@163.com";
    private String version="1.0";

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
