package com.ojk.base.net;

import com.lzy.okgo.request.base.Request;
import com.ojk.base.utils.OjkSignUtils;

public abstract class OjkRequest<T> extends OjkResponse<T> {

    public OjkRequest() {
        super();
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        OjkSignUtils.signature(request.getParams());
    }

}
