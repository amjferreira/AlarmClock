package novoalarme.alarmv2;

import java.util.Date;




/* classe obsoleta em função do cursor adapter */

class AlarmData {

    private String title;
    private String time;
    private String date;
    private int id;
    private Boolean isActive;

    private int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected Boolean isActive() {
        return isActive;
    }

    protected void setActive(Boolean active) {
        isActive = active;
    }

    protected String getTime() {
        return time;
    }

    protected void setTime(String time) {
        this.time = time;
    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }
}
