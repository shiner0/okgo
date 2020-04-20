package com.ojk.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ojk.base.entity.SwapTokenEntity;
import com.ojk.base.net.OjkRequest;
import com.ojk.base.utils.OjkPermissionsUtils;
import com.ojk.base.utils.OjkSPUtils;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class OjkFragment extends Fragment implements
        OjkPermissionsUtils.PermissionCallbacks {


    protected Activity mActivity;

    protected View rootView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = LayoutInflater.from(mActivity).inflate(getLayoutResId(), null);
        initView(savedInstanceState);
        initData();
        return rootView;
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initData();

    protected <VT extends View> VT getViewById(@IdRes int id) {

        if (rootView == null)
        {
            return null;
        }

        return (VT) rootView.findViewById(id);
    }



    public void showSnackbar(String message) {
        Snackbar.make(getViewById(android.R.id.content), message,
                Snackbar.LENGTH_SHORT).show();
    }

    public boolean hintCode(int code, String msg) {
        if (code == -2) {
            swapToken();
        } else if (code == -1) {
            OjkSPUtils.clearSp(mActivity);
            return true;
        } else {
            Snackbar.make(getViewById(android.R.id.content), msg,
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
                            hintCode(response.body().getCode(), response.body().getMsg());
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
        if (OjkPermissionsUtils.hasPermissions(mActivity, mPerms)) {
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
