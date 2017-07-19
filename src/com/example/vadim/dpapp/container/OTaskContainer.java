package com.example.vadim.dpapp.container;

public class OTaskContainer {
    private String code;
    private String codeTask;
    private String opisanie;
    private String codeActiv;

    public OTaskContainer(String code, String codeTask, String opisanie, String codeActiv) {
        this.code = code;
        this.codeTask = codeTask;
        this.opisanie = opisanie;
        this.codeActiv = codeActiv;
    }

    public String getCodeTask() {
        return codeTask;
    }

    public void setCodeTask(String codeTask) {
        this.codeTask = codeTask;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public String getCodeActiv() {
        return codeActiv;
    }

    public void setCodeActiv(String codeActiv) {
        this.codeActiv = codeActiv;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "OTaskContainer{" +
                "code='" + code + '\'' +
                ", codeTask='" + codeTask + '\'' +
                ", opisanie='" + opisanie + '\'' +
                ", codeActiv='" + codeActiv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OTaskContainer that = (OTaskContainer) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (codeTask != null ? !codeTask.equals(that.codeTask) : that.codeTask != null) return false;
        if (opisanie != null ? !opisanie.equals(that.opisanie) : that.opisanie != null) return false;
        return codeActiv != null ? codeActiv.equals(that.codeActiv) : that.codeActiv == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (codeTask != null ? codeTask.hashCode() : 0);
        result = 31 * result + (opisanie != null ? opisanie.hashCode() : 0);
        result = 31 * result + (codeActiv != null ? codeActiv.hashCode() : 0);
        return result;
    }
}
