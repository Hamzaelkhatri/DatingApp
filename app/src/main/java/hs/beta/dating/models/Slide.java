package hs.beta.dating.models;

public class Slide
{
    int Images;
    String Title;
    String Description;

    public Slide(int images, String title, String description) {
        Images = images;
        Title = title;
        Description = description;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
