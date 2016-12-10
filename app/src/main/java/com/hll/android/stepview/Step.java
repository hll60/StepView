package com.hll.android.stepview;

/**
 * Created by lin on 2016/12/3.
 */

public class Step {

    private String name;
    private String iconText;

    private StepStatus status;

    public Step(String name, StepStatus status) {
        this.name = name;
        this.status = status;
    }

    public Step(String name, String iconText, StepStatus status) {
        this.name = name;
        this.iconText = iconText;
        this.status = status;
    }

    public String getIconText() {
        return iconText;
    }

    public void setIconText(String iconText) {
        this.iconText = iconText;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StepStatus getStatus() {
        return status;
    }

    public void setStatus(StepStatus status) {
        this.status = status;
    }

    public enum StepStatus{
        UnDo,
        Doing,
        Completed,
    }
}
