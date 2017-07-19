package com.example.vadim.dpapp.container;

/**
 * Created by Vadim on 10.05.2017.
 */
public class RegistrContainer {
    String code;
    String organization;
    String divisionOfOrganizations;
    String mol;
    String contractor;
    String divisionOfContractor;
    String molContractor;
    String lastDate;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNameActiv() {
        return nameActiv;
    }

    public void setNameActiv(String nameActiv) {
        this.nameActiv = nameActiv;
    }

    String nameActiv;

    public String getShtrihCode() {
        return shtrihCode;
    }

    public void setShtrihCode(String shtrihCode) {
        this.shtrihCode = shtrihCode;
    }

    String shtrihCode;


    public RegistrContainer(String code, String organization, String divisionOfOrganizations, String mol, String contractor, String divisionOfContractor, String molContractor, String lastDate,String shtrihCode,String nameActiv, String status) {
        this.code = code;
        this.organization = organization;
        this.divisionOfOrganizations = divisionOfOrganizations;
        this.mol = mol;
        this.contractor = contractor;
        this.divisionOfContractor = divisionOfContractor;
        this.molContractor = molContractor;
        this.lastDate = lastDate;
        this.shtrihCode = shtrihCode;
        this.nameActiv = nameActiv;
        this.status = status;
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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDivisionOfOrganizations() {
        return divisionOfOrganizations;
    }

    public void setDivisionOfOrganizations(String divisionOfOrganizations) {
        this.divisionOfOrganizations = divisionOfOrganizations;
    }

    public String getMol() {
        return mol;
    }

    public void setMol(String mol) {
        this.mol = mol;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getDivisionOfContractor() {
        return divisionOfContractor;
    }

    public void setDivisionOfContractor(String divisionOfContractor) {
        this.divisionOfContractor = divisionOfContractor;
    }

    public String getMolContractor() {
        return molContractor;
    }

    public void setMolContractor(String molContractor) {
        this.molContractor = molContractor;
    }
}
