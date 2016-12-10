# StepView
HorizontalStepView . For step indicator, flow indicator, timeline...

### HorizontalStepView
<img src="screenshots/screen_hr.png"></img>

### Usage
#### 1.Just copy these source files and attrs.xml to your project.
#### 2.Then use the HorizontalStepView like this.

     <your.package.stepview.HorizontalStepView
        android:id="@+id/stepView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:textSize="13sp"
        app:textUnDoColor="#6e6e6e"
        app:textDoingColor="#ec4a48"
        app:textCompletedColor="#ec4a48">

        <your.package.stepview.HorizontalStepViewIndicator
            android:id="@+id/stepViewIndicator"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            app:iconHeight="40dp"
            app:iconWidth="40dp"
            app:lineHeight="2.5dp"
            app:lineWidth="60dp"
            app:lineUnCompletedColor="#6e6e6e"
            app:lineCompletedColor="#ec4a48"
            app:lineSpacing="5dp"
            app:iconShape="circle"
            app:iconBackgroundColor="@android:color/transparent"
            app:iconUnDo="@drawable/ic_circle_gray"
            app:iconDoing="@drawable/ic_circle_red"
            app:iconCompleted="@drawable/ic_circle_red"
            app:iconTextSize="21.5sp"
            app:iconTextColor="@android:color/white"/>

        <RelativeLayout
            android:id="@+id/textContainerRL"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </your.package.stepview.HorizontalStepView>

#### 3.Setup the StepView

    public void initStepView(){
        List<Step> stepsList = new ArrayList<>();
        Step stepBean0 = new Step("验证身份", "1", Step.StepStatus.Doing);
        Step stepBean1 = new Step("设置密码保护", "2", Step.StepStatus.UnDo);
        Step stepBean2 = new Step("设置支付密码", "3", Step.StepStatus.UnDo);
        stepsList.add(stepBean0);
        stepsList.add(stepBean1);
        stepsList.add(stepBean2);
        stepView.setStepList(stepsList);
    }


