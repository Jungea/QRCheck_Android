package net.skhu.qrcheck.domain;

public class Course {
    int id;

    String name;
    Boolean split;
    int joint;
    Boolean show;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSplit() {
        return split;
    }

    public void setSplit(Boolean split) {
        this.split = split;
    }

    public int getJoint() {
        return joint;
    }

    public void setJoint(int joint) {
        this.joint = joint;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}
