package com.ojk.base.net;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;
import com.ojk.base.OjkApplication;
import com.ojk.base.utils.OjkPhoneUtils;
import com.ojk.base.utils.OjkSPUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import okhttp3.Response;

public abstract class OjkResponse<T> extends AbsCallback<T> {

    private StringConvert convert;

    public OjkResponse() {

    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        request.headers("Accept", "application/json")
                .headers("Connection", "Keep-Alive")
                .headers("appversion", "1.0")
                .headers("platform", "Android")
                .headers("lang", "id")
                .headers("token", OjkSPUtils.getToken(OjkApplication.getBaseApplication()))
                .headers("deviceInfo", OjkPhoneUtils.getHeadInfo());

    }


    @Override
    public T convertResponse(Response response) throws Throwable {
        convert = new StringConvert();
        String s = convert.convertResponse(response);
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        response.close();
        return new Gson().fromJson(s, type);
    }

}
