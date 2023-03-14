public class Query {
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    public Query() {
        this.location = "";
        this.startDate = "";
        this.endDate = "";
        this.startTime = "";
        this.endTime = "";
    }

    public String getLocation() {
        return this.location;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


}
