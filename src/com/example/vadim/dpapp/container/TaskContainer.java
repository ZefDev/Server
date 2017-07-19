package com.example.vadim.dpapp.container;

import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskContainer implements Serializable{
    private static final long serialVersionUID = -3158424480246390052L;

    private String code;
    private String taskName;
    private String contractor;
    private String date;
    private String executor;
    private String complite;
    private String lastDate;
    private ArrayList<OTaskContainer> otasks;
    private ArrayList<CompliteTaskContainer> compliteTasks;

    public TaskContainer(String code, String taskName, String contractor, String date, String executor, String complite,String lastDate, ArrayList otasks, ArrayList compliteTasks) {
        this.code = code;
        this.taskName = taskName;
        this.contractor = contractor;
        this.date = date;
        this.executor = executor;
        this.complite = complite;
        this.lastDate = lastDate;
        this.otasks = otasks;
        this.compliteTasks = compliteTasks;
    }

    public ArrayList<CompliteTaskContainer> getCompliteTasks() {
        return compliteTasks;
    }

    public void setCompliteTasks(ArrayList<CompliteTaskContainer> compliteTasks) {
        this.compliteTasks = compliteTasks;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getComplite() {
        return complite;
    }

    public void setComplite(String complite) {
        this.complite = complite;
    }

    public ArrayList<OTaskContainer> getOtasks() {
        return otasks;
    }

    public void setOtasks(ArrayList<OTaskContainer> otasks) {
        this.otasks = otasks;
    }

    @Override
    public String toString() {
        return "TaskContainer{" +
                "code='" + code + '\'' +
                ", taskName='" + taskName + '\'' +
                ", contractor='" + contractor + '\'' +
                ", date='" + date + '\'' +
                ", executor='" + executor + '\'' +
                ", complite='" + complite + '\'' +
                ", lastDate='" + lastDate + '\'' +
                ", otasks=" + otasks +
                ", compliteTasks=" + compliteTasks +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskContainer that = (TaskContainer) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (taskName != null ? !taskName.equals(that.taskName) : that.taskName != null) return false;
        if (contractor != null ? !contractor.equals(that.contractor) : that.contractor != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (executor != null ? !executor.equals(that.executor) : that.executor != null) return false;
        if (complite != null ? !complite.equals(that.complite) : that.complite != null) return false;
        if (lastDate != null ? !lastDate.equals(that.lastDate) : that.lastDate != null) return false;
        if (otasks != null ? !otasks.equals(that.otasks) : that.otasks != null) return false;
        return compliteTasks != null ? compliteTasks.equals(that.compliteTasks) : that.compliteTasks == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (contractor != null ? contractor.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (executor != null ? executor.hashCode() : 0);
        result = 31 * result + (complite != null ? complite.hashCode() : 0);
        result = 31 * result + (lastDate != null ? lastDate.hashCode() : 0);
        result = 31 * result + (otasks != null ? otasks.hashCode() : 0);
        result = 31 * result + (compliteTasks != null ? compliteTasks.hashCode() : 0);
        return result;
    }

    public static final Comparator<TaskContainer> compareByNumber = new Comparator<TaskContainer>() {
        @Override
        public int compare(TaskContainer o1, TaskContainer o2) {
            return Integer.parseInt(o1.getCode()) - Integer.parseInt(o2.getCode());
        }
    };
}
