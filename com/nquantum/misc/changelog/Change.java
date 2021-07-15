package com.nquantum.misc.changelog;

public class Change {
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ChangeType getType() {
        return type;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    String desc;
    ChangeType type;

    public Change(String desc, ChangeType type) {
        this.desc = desc;
        this.type = type;
    }
}
