package com.example.vadim.dpapp.container;

/**
 * Created by Vadim on 29.05.2017.
 */
public class CompliteTaskContainer {
    public String id;
    public String date;
    public String compliteOTask;
    public String time;
    public String codeTask;
    public String codeActiv;

    public CompliteTaskContainer(String id, String date, String compliteOTask, String time, String codeTask, String codeActiv) {
        this.id = id;
        this.date = date;
        this.compliteOTask = compliteOTask;
        this.time = time;
        this.codeTask = codeTask;
        this.codeActiv = codeActiv;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompliteOTask() {
        return compliteOTask;
    }

    public void setCompliteOTask(String compliteOTask) {
        this.compliteOTask = compliteOTask;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCodeTask() {
        return codeTask;
    }

    public void setCodeTask(String codeTask) {
        this.codeTask = codeTask;
    }

    public String getCodeActiv() {
        return codeActiv;
    }

    public void setCodeActiv(String codeActiv) {
        this.codeActiv = codeActiv;
    }

    @Override
    public String toString() {
        return "CompliteTaskContainer{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", compliteOTask='" + compliteOTask + '\'' +
                ", time='" + time + '\'' +
                ", codeTask='" + codeTask + '\'' +
                ", codeActiv='" + codeActiv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompliteTaskContainer that = (CompliteTaskContainer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (compliteOTask != null ? !compliteOTask.equals(that.compliteOTask) : that.compliteOTask != null)
            return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (codeTask != null ? !codeTask.equals(that.codeTask) : that.codeTask != null) return false;
        return codeActiv != null ? codeActiv.equals(that.codeActiv) : that.codeActiv == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (compliteOTask != null ? compliteOTask.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (codeTask != null ? codeTask.hashCode() : 0);
        result = 31 * result + (codeActiv != null ? codeActiv.hashCode() : 0);
        return result;
    }
}
