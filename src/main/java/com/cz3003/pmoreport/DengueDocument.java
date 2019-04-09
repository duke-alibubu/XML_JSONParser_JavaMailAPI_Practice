package com.cz3003.pmoreport;

import org.bson.Document;

import java.util.Objects;

public class DengueDocument {
    Document doc;

    public DengueDocument(Document doc) {
        this.doc = doc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DengueDocument that = (DengueDocument) o;
        return Objects.equals(doc.getString("locality"), that.doc.getString("locality"));
    }

    @Override
    public int hashCode() {
        return Objects.hash(doc.getString("locality"));
    }

    public int getSize() {
        return doc.getInteger("caseSize");
    }

    public String getLocation() {
        return doc.getString("locality");
    }

    public static DengueDocument from(Document doc) {
        return new DengueDocument(doc);
    }
}
