package com.scube.localnews.model;


public class NewsItem  {
    @Override
    public String toString() {
        return "NewsItem{" +
                "image='" + image + '\'' +
                ", header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", linkAddress='" + linkAddress + '\'' +
                '}';
    }

    private String image;
    private String header;
    private String description;
    private String linkAddress;

    public NewsItem() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public NewsItem(String image, String header, String description, String linkAddress) {
        this.image = image;
        this.header = header;
        this.description = description;
        this.linkAddress = linkAddress;
    }

}
