package com.ojk.base;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ojk.base.entity.SwapTokenEntity;
import com.ojk.base.net.OjkRequest;
import com.ojk.base.utils.OjkPermissionsUtils;
import com.ojk.base.utils.OjkSPUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public abstract class OjkActivity extends AppCompatActivity implements
        OjkPermissionsUtils.PermissionCallbacks {

    protected Activity mActivity = null;


    public abstract int loadView();

    public abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.loadView());
        mActivity = this;
        this.initData();
    }


    public void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message,
                Snackbar.LENGTH_SHORT).show();
    }

    public boolean hintCode(int code, String msg) {
        if (code == -2) {
            swapToken();
        } else if (code == -1) {
            OjkSPUtils.clearSp(mActivity);
            return true;
        } else {
            Snackbar.make(findViewById(android.R.id.content), msg,
                    Snackbar.LENGTH_SHORT).show();
        }
        return false;
    }

    public void swapToken() {
        OkGo.<SwapTokenEntity>post(OjkUrl.token)//
                .tag(this)//
                .params("token", OjkSPUtils.getToken(mActivity))//
                .execute(new OjkRequest<SwapTokenEntity>() {
                    @Override
                    public void onSuccess(Response<SwapTokenEntity> response) {
                        if (response.body().getCode() == 1) {
                            OjkSPUtils.setToken(response.body().getResponse().getCont(), mActivity);
                        } else {
                            hintCode(response.body().getCode(),response.body().getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<SwapTokenEntity> response) {
                    }
                });

    }



    private CheckPermListener mListener;

    public interface CheckPermListener {
        void superPermission();
    }

    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mListener = listener;
        if (OjkPermissionsUtils.hasPermissions(this, mPerms)) {
            if (mListener != null)
                mListener.superPermission();
        } else {
            OjkPermissionsUtils.requestPermissions(this, getString(resString),
                    102, mPerms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        OjkPermissionsUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.superPermission();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        OjkPermissionsUtils.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.permission_tip),
                R.string.setting, R.string.cancel, null, perms);
    }



}
