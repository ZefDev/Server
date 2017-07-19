package com.example.vadim.dpapp.container;

import java.util.Comparator;

public class DocContainer {
    private String codeDoc;
    private String avtorDoc;
    private String messageDoc;
    private String dateDoc;


    public DocContainer(String codeDoc, String avtorDoc, String messageDoc,String dateDoc) {
        this.codeDoc = codeDoc;
        this.avtorDoc = avtorDoc;
        this.messageDoc = messageDoc;
        this.dateDoc = dateDoc;
    }

    public String getCodeDoc() {
        return codeDoc;
    }

    public void setCodeDoc(String codeDoc) {
        this.codeDoc = codeDoc;
    }

    public String getAvtorDoc() {
        return avtorDoc;
    }

    public void setAvtorDoc(String avtorDoc) {
        this.avtorDoc = avtorDoc;
    }

    public String getMessageDoc() {
        return messageDoc;
    }

    public void setMessageDoc(String messageDoc) {
        this.messageDoc = messageDoc;
    }
    public String getDateDoc() {
        return dateDoc;
    }

    public void setDateDoc(String dateDoc) {
        this.dateDoc = dateDoc;
    }
    @Override
    public String toString() {
        return "DocContainer{" +
                "codeDoc='" + codeDoc + '\'' +
                ", avtorDoc='" + avtorDoc + '\'' +
                ", messageDoc='" + messageDoc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocContainer that = (DocContainer) o;

        if (codeDoc != null ? !codeDoc.equals(that.codeDoc) : that.codeDoc != null) return false;
        if (avtorDoc != null ? !avtorDoc.equals(that.avtorDoc) : that.avtorDoc != null) return false;
        return messageDoc != null ? messageDoc.equals(that.messageDoc) : that.messageDoc == null;
    }

    @Override
    public int hashCode() {
        int result = codeDoc != null ? codeDoc.hashCode() : 0;
        result = 31 * result + (avtorDoc != null ? avtorDoc.hashCode() : 0);
        result = 31 * result + (messageDoc != null ? messageDoc.hashCode() : 0);
        return result;
    }

    public static final Comparator<DocContainer> compareByNumber = new Comparator<DocContainer>() {
        @Override
        public int compare(DocContainer o1, DocContainer o2) {
            return Integer.parseInt(o1.getCodeDoc()) - Integer.parseInt(o2.getCodeDoc());
        }
    };
}
