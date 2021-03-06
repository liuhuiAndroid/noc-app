package com.noc.app.data.bean;

import java.util.List;

public class SofaTab {

    public int activeSize;
    public int normalSize;
    public String activeColor;
    public String normalColor;
    public int select;
    public int tabGravity;
    public List<Tabs> tabs;

    public static class Tabs {

        public String title;
        public int index;
        public String tag;
        public boolean enable;

        public Tabs(String title, int index, String tag, boolean enable) {
            this.title = title;
            this.index = index;
            this.tag = tag;
            this.enable = enable;
        }
    }
}
