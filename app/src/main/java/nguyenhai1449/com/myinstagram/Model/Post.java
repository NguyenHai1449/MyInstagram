package nguyenhai1449.com.myinstagram.Model;

public class Post {

    private String postid;
    private String postimage;
    private String desciption;
    private String publisher;


    public Post(String postid, String postimage, String desciption, String publisher) {
        this.postid = postid;
        this.postimage = postimage;
        this.desciption = desciption;
        this.publisher = publisher;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
