package com.github.karamelsoft.testing.data.driven.testing.xml.operations;


import com.github.karamelsoft.testing.data.driven.testing.api.operations.Comparison;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Load;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;

/**
 * Created by Jonathan Schoreels on 26/04/15.
 */
public class XmlTester {

    public static <T> Load<T> load(Class<T> type) {
        return
            XmlLoad.<T>newBuilder()
                .type(type)
                .build();
    }

    public static <T> XmlLoad.Builder<T> customLoad() {
        return XmlLoad.newBuilder();
    }

    public static <T> Save<T> save() {
        return
            XmlSave.<T>newBuilder()
                .build();
    }

    public static <T> XmlSave.Builder<T> customSave() {
        return XmlSave.newBuilder();
    }

    public static Comparison compare(XmlComparisonMode mode) {
        return new XmlCompare(mode);
    }

}
