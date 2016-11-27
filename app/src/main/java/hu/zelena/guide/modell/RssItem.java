package hu.zelena.guide.modell;

/**
 * Created by patrik on 2016.11.08..
 */


public class RssItem {
    String title;
    String category;
    String link;
    String pubDate;

    public String getCategory() {
        return category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}