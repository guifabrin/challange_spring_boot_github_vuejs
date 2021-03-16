package com.github.guifabrin.application.api.v1.entities;

import org.apache.tomcat.util.codec.binary.Base64;

public class GithubContent {
    public String name;
    public String path;
    public String sha;
    public int size;
    public String url;
    public String html_url;
    public String git_url;
    public String download_url;
    public String type;
    public String content;
    public String encoding;

    public String getDecodedContent() {
        return new String(Base64.decodeBase64(this.content));
    }
}
