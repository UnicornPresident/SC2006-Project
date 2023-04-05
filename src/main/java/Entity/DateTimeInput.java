package Entity;

public class DateTimeInput {
    private String date;
    private String time;

    public DateTimeInput(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public DateTimeInput() {
        this.date = "";
        this.time = "";
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
