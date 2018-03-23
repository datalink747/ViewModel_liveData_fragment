package com.soussidev.viewmodel_livedata_fragment.RxPref;

/**
 * Created by Soussi on 23/03/2018.
 */

public class PrefItem {

    private  int span_count;
    private  String span_name;

    public PrefItem() {
    }

    public String getSpan_name() {
        return span_name;
    }

    public void setSpan_name(String span_name) {
        this.span_name = span_name;
    }

    public int getSpan_count() {
        return span_count;
    }

    public void setSpan_count(int span_count) {
        this.span_count = span_count;
    }
}
