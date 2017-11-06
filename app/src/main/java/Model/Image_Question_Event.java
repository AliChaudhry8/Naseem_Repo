package Model;

import java.security.PrivateKey;

import BussinessObjects.Show_Test_BO.Image_Questions;

/**
 * Created by DELL on 10/13/2017.
 */

public class Image_Question_Event {
    private Image_Questions images;

    public Image_Question_Event(){

    }

    public Image_Questions getImages() {
        return images;
    }

    public void setImages(Image_Questions images) {
        this.images = images;
    }

    public Image_Question_Event(Image_Questions images) {
        this.images = images;
    }
}
