package com.muaj.oops.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muaj.lib.view.ClearEditText;
import com.muaj.lib.view.DelayedProgressDialog;
import com.muaj.oops.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by muaj on 2018/7/5
 * TODO
 */
public class GameActivity extends AppCompatActivity {

    ClearEditText edit_x0, edit_xfrom, edit_xto, edit_xadd, edit_xwin;
    ClearEditText edit_y0, edit_yfrom, edit_yto, edit_yadd, edit_ywin;
    ClearEditText edit_z0, edit_zfrom, edit_zto, edit_zadd, edit_zwin;

    Button btn_print;
    TextView tv_print;

    Button btn_result;
    TextView tv_result;


    String x0, xfrom, xto, xadd, xwin;
    String y0, yfrom, yto, yadd, ywin;
    String z0, zfrom, zto, zadd, zwin;

    float dx0, dxfrom, dxto, dxadd, dxwin;
    float dy0, dyfrom, dyto, dyadd, dywin;
    float dz0, dzfrom, dzto, dzadd, dzwin;

    DelayedProgressDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        edit_x0 = findViewById(R.id.edit_x0);
        edit_xfrom = findViewById(R.id.edit_xfrom);
        edit_xto = findViewById(R.id.edit_xto);
        edit_xadd = findViewById(R.id.edit_xadd);
        edit_xwin = findViewById(R.id.edit_xwin);

        edit_y0 = findViewById(R.id.edit_y0);
        edit_yfrom = findViewById(R.id.edit_yfrom);
        edit_yto = findViewById(R.id.edit_yto);
        edit_yadd = findViewById(R.id.edit_yadd);
        edit_ywin = findViewById(R.id.edit_ywin);

        edit_z0 = findViewById(R.id.edit_z0);
        edit_zfrom = findViewById(R.id.edit_zfrom);
        edit_zto = findViewById(R.id.edit_zto);
        edit_zadd = findViewById(R.id.edit_zadd);
        edit_zwin = findViewById(R.id.edit_zwin);

        btn_print = findViewById(R.id.btn_print);
        tv_print = findViewById(R.id.tv_print);

        btn_result = findViewById(R.id.btn_result);
        tv_result = findViewById(R.id.tv_result);

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPrint();
            }
        });

        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickResult();
            }
        });
    }

    public void clickPrint() {
        if (!isOK()) return;

        dx0 = Float.valueOf(x0) - 1.0f;
        dxfrom = Float.valueOf(xfrom);
        dxto = Float.valueOf(xto);
        dxadd = Float.valueOf(xadd);
        dxwin = Float.valueOf(xwin);

        dy0 = Float.valueOf(y0) - 1.0f;
        dyfrom = Float.valueOf(yfrom);
        dyto = Float.valueOf(yto);
        dyadd = Float.valueOf(yadd);
        dywin = Float.valueOf(ywin);

        StringBuilder print = new StringBuilder();
        print.append("x1=" + formatFloat(dx0));
        print.append("\ny1=" + formatFloat(dy0));

        if (!TextUtils.isEmpty(z0)) {
            dz0 = Float.valueOf(z0) - 1.0f;
            dzfrom = Float.valueOf(zfrom);
            dzto = Float.valueOf(zto);
            dzadd = Float.valueOf(zadd);
            dzwin = Float.valueOf(zwin);
            print.append("\nz1=" + formatFloat(dz0));
        }
        tv_print.setText(print.toString());

    }

    public void clickResult() {
        mLoadingDialog = new DelayedProgressDialog();
        mLoadingDialog.show(getSupportFragmentManager(), "dialog");
        StringBuilder result = new StringBuilder();
        if (TextUtils.isEmpty(z0)) {
            for (float x = dxfrom; x <= dxto; x += dxadd) {
                for (float y = dyfrom; y <= dyto; y += dyadd) {
                    if (dx0 * x - y > dxwin && dy0 * y - x > dywin) {
                        result.append("\n=======\n" +
                                "x=" + formatFloat(x) + "\t,y=" + formatFloat(y) +
                                "\t,x-win:" + formatFloat(dx0 * x - y) + "\t,y-win:" + formatFloat(dy0 * y - x));
                    }
                }
            }
        } else {
            for (float x = dxfrom; x <= dxto; x += dxadd) {
                for (float y = dyfrom; y <= dyto; y += dyadd) {
                    for (float z = dzfrom; z <= dzto; z += dzadd) {
                        if (dx0 * x - y - z > dxwin && dy0 * y - x - z > dywin && dz0 * z - x - y > dzwin) {
                            result.append("\n=======\n" +
                                    "x=" + formatFloat(x) + "\t,y=" + formatFloat(y) + "\t,z=" + formatFloat(z) +
                                    "\nx-win:" + formatFloat(dx0 * x - y - z) + "\t,y-win:" + formatFloat(dy0 * y - x - z) + "\t,z-win:" + formatFloat(dz0 * z - x - y));
                        }
                    }
                }
            }
        }
        tv_result.setText(TextUtils.isEmpty(result.toString()) ? "无结果" : result.toString());
        mLoadingDialog.cancel();
    }

    public boolean isOK() {
        x0 = edit_x0.getText().toString();
        xadd = edit_xadd.getText().toString();
        xfrom = edit_xfrom.getText().toString();
        xto = edit_xto.getText().toString();
        xwin = edit_xwin.getText().toString();

        y0 = edit_y0.getText().toString();
        yadd = edit_yadd.getText().toString();
        yfrom = edit_yfrom.getText().toString();
        yto = edit_yto.getText().toString();
        ywin = edit_ywin.getText().toString();

        z0 = edit_z0.getText().toString();
        zadd = edit_zadd.getText().toString();
        zfrom = edit_zfrom.getText().toString();
        zto = edit_zto.getText().toString();
        zwin = edit_zwin.getText().toString();

        if (TextUtils.isEmpty(x0)) {
            edit_x0.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(xadd)) {
            edit_xadd.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(xfrom)) {
            edit_xfrom.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(xto)) {
            edit_xto.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(xwin)) {
            edit_xwin.setError("不能空");
            return false;
        }

        if (TextUtils.isEmpty(y0)) {
            edit_y0.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(yadd)) {
            edit_yadd.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(yfrom)) {
            edit_yfrom.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(yto)) {
            edit_yto.setError("不能空");
            return false;
        }
        if (TextUtils.isEmpty(ywin)) {
            edit_ywin.setError("不能空");
            return false;
        }

        if (!TextUtils.isEmpty(z0)) {
            if (TextUtils.isEmpty(zadd)) {
                edit_zadd.setError("不能空");
                return false;
            }
            if (TextUtils.isEmpty(zfrom)) {
                edit_zfrom.setError("不能空");
                return false;
            }
            if (TextUtils.isEmpty(zto)) {
                edit_zto.setError("不能空");
                return false;
            }
            if (TextUtils.isEmpty(zwin)) {
                edit_zwin.setError("不能空");
                return false;
            }
        }
        return true;
    }

    private String formatFloat(float decimal) {
        return new DecimalFormat("##0.00").format(decimal);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoadingDialog = null;
    }
}
