package com.wecourse.commonutils;

public class ResultCode {

    public static final Integer SUCCESS = 20000; //成功

    public static final Integer ERROR = 20001; //失败

    public static final Integer LOGINERROR =20002;//用户名或密码错误

    public static final Integer ACCESSERROR =20003;//权限不足

    public static final Integer REMOTEERROR =20004;//远程调用失败

    public static final Integer REPERROR =20005;//重复操作

    public static final Integer OVERDUE =20006;//token过期

    public static final Integer REFUSE =20007;//拒绝操作
}
