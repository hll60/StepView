package com.hll.android.stepview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    HorizontalStepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepView = (HorizontalStepView) findViewById(R.id.stepView);

        initStepView();
    }

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
}
