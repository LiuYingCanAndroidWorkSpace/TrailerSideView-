package com.example.myview;

import com.example.myview.view.ContainerView;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContainerView containerView = new ContainerView(MainActivity.this);
        setContentView(containerView);
        containerView.addPage("第一页", "第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容第一页的内容");
        containerView.addPage("第二页", "第二页的内容");
        containerView.addPage("第三页", "第三页的内容");
        containerView.addPage("第四页", "第四页的内容");
    }
    
    

}
