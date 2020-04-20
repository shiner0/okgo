package com.ojk.base.entity;

public class SwapTokenEntity {

    /**
     * code : 1
     * msg : ok
     * response : {"list":null,"cont":"c13a90cd-c188-42fc-be71-97d96d1a79c7"}
     */

    private int code;
    private String msg;
    private ResponseBean response;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * list : null
         * cont : c13a90cd-c188-42fc-be71-97d96d1a79c7
         */

        private Object list;
        private String cont;

        public Object getList() {
            return list;
        }

        public void setList(Object list) {
            this.list = list;
        }

        public String getCont() {
            return cont;
        }

        public void setCont(String cont) {
            this.cont = cont;
        }
    }
}
