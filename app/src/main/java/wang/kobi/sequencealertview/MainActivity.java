package wang.kobi.sequencealertview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertView alertView = new AlertView.Builder(MainActivity.this)
                        .setMessage("Alert View1")
                        .setOnCloseBtnClickLister(new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        }).create();

                alertView.showAndInTurn();

                AlertView alertView1 = new AlertView.Builder(MainActivity.this)
                        .setMessage("Alert View2")
                        .setOnCloseBtnClickLister(new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .setLeftButtonTitle("OK", new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .setRightButtonTitle("No", new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .create();

                alertView1.showAndInTurn();


                AlertView alertView2 = new AlertView.Builder(MainActivity.this)
                        .setTitle("Alert title")
                        .setMessage("Alert View3 Alert View3 Alert View3 Alert View3")
                        .setOnCloseBtnClickLister(new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .setLeftButtonTitle("OK", new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .setRightButtonTitle("No", new AlertView.OnAlertViewBtnClickLister() {
                            @Override
                            public void onAlertViewBtnClick(AlertView view) {
                                view.dismiss();
                            }
                        })
                        .create();

                alertView2.showAndInTurn();
            }
        });
    }
}
