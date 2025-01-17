package com.proxy.study_aspectj.sample.dto;

public class SampleDto {

    private String title;
    private String content;

    public SampleDto() {
    }

    public SampleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SampleDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
