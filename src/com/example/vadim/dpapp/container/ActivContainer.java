package com.example.vadim.dpapp.container;

import java.util.Comparator;

/**
 * Created by Vadim on 20.04.2017.
 */
public class ActivContainer {
    String number;
    String nameActiv;
    String contractorActiv;
    String typeActiv;
    String shtrihActiv;
    String photo;
    String date;
    String codeContractor;

    public String getCodeContractor() {
        return codeContractor;
    }

    public void setCodeContractor(String codeContractor) {
        this.codeContractor = codeContractor;
    }

    public String getCodeActiv() {
        return number;
    }

    public void setCodeActiv(String codeActiv) {
        this.number = codeActiv;
    }

    public String getNameActiv() {
        return nameActiv;
    }

    public void setNameActiv(String nameActiv) {
        this.nameActiv = nameActiv;
    }

    public String getContractorActiv() {
        return contractorActiv;
    }

    public void setContractorActiv(String contractorActiv) {
        this.contractorActiv = contractorActiv;
    }

    public String getTypeActiv() {
        return typeActiv;
    }

    public void setTypeActiv(String typeActiv) {
        this.typeActiv = typeActiv;
    }

    public String getShtrihActiv() {
        return shtrihActiv;
    }

    public void setShtrihActiv(String shtrihActiv) {
        this.shtrihActiv = shtrihActiv;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String photo) {
        this.date = date;
    }

    public ActivContainer(String codeActiv, String maneActiv, String contractorActiv, String typeActiv, String shtrihActiv, String photo,String date,String codeContractor) {

        this.number = codeActiv;
        this.nameActiv = maneActiv;
        this.contractorActiv = contractorActiv;
        this.typeActiv = typeActiv;
        this.shtrihActiv = shtrihActiv;
        this.photo = photo;
        this.date = date;
        this.codeContractor = codeContractor;
    }

    @Override
    public String toString() {
        return "ActivContainer{" +
                "number='" + Integer.parseInt(number) + '\'' +
                ", nameActiv='" + nameActiv + '\'' +
                ", contractorActiv='" + contractorActiv + '\'' +
                ", typeActiv='" + typeActiv + '\'' +
                ", shtrihActiv='" + shtrihActiv + '\'' +
                ", photo='" + photo + '\'' +
                ", date='" + date + '\'' +
                ", codeContractor='" + codeContractor + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivContainer container = (ActivContainer) o;

        if (number != null ? !number.equals(container.number) : container.number != null) return false;
        if (nameActiv != null ? !nameActiv.equals(container.nameActiv) : container.nameActiv != null) return false;
        if (contractorActiv != null ? !contractorActiv.equals(container.contractorActiv) : container.contractorActiv != null)
            return false;
        if (codeContractor != null ? !codeContractor.equals(container.codeContractor) : container.codeContractor != null)
            return false;
        if (typeActiv != null ? !typeActiv.equals(container.typeActiv) : container.typeActiv != null) return false;
        if (shtrihActiv != null ? !shtrihActiv.equals(container.shtrihActiv) : container.shtrihActiv != null)
            return false;
        if (photo != null ? !photo.equals(container.photo) : container.photo != null) return false;
        return date != null ? date.equals(container.date) : container.date == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (nameActiv != null ? nameActiv.hashCode() : 0);
        result = 31 * result + (contractorActiv != null ? contractorActiv.hashCode() : 0);
        result = 31 * result + (typeActiv != null ? typeActiv.hashCode() : 0);
        result = 31 * result + (shtrihActiv != null ? shtrihActiv.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (codeContractor != null ? codeContractor.hashCode() : 0);
        return result;
    }

    public static final Comparator<ActivContainer> compareByNumber = new Comparator<ActivContainer>() {
        @Override
        public int compare(ActivContainer o1, ActivContainer o2) {
            return Integer.parseInt(o1.getCodeActiv()) - Integer.parseInt(o2.getCodeActiv());
        }
    };
}
