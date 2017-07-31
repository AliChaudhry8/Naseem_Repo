package BussinessObjects.Show_Test_BO;

/**
 * Created by Muhammad Taimoor on 7/7/2017.
 */

public class Marks {
    private int id;
    private int total;
    private int obtained;

    public Marks(int id, int total, int obtained) {
        this.id = id;
        this.total = total;
        this.obtained = obtained;
    }

    public Marks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getObtained() {
        return obtained;
    }

    public void setObtained(int obtained) {
        this.obtained = obtained;
    }
}
