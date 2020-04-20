package com.ojk.app;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ojk.base.OjkActivity;
import com.ojk.base.OjkUrl;
import com.ojk.base.entity.FcmTokenEntity;
import com.ojk.base.net.OjkRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends OjkActivity {

    @Override
    public int loadView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("pushToken", "pushToken");
        OkGo.<FcmTokenEntity>post(OjkUrl.fcmToken)//
                .tag(this)//
                .params(params)//
                .execute(new OjkRequest<FcmTokenEntity>() {
                    @Override
                    public void onSuccess(Response<FcmTokenEntity> response) {
                        FcmTokenEntity.ResponseBean responseBean = response.body().getResponse();
                        Log.i("hintcode",responseBean.getCont()+"aa");
                    }

                    @Override
                    public void onError(Response<FcmTokenEntity> response) {
                        Log.i("hintcode","shibai");
                    }
                });

    }

}
