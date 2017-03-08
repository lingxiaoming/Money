package com.chris.money.tools;
/**
 * User: xiaoming
 * Date: 2017-02-26
 * Time: 17:19
 * 支付宝支付
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.chris.money.MoneyApplication;
import com.chris.money.constant.PreferenceConstant;
import com.chris.money.pay.PayResult;
import com.chris.money.pay.util.OrderInfoUtil2_0;
import com.chris.money.ui.HelpActivity;

import java.util.Map;

/**
 * Created by apple on 2017/2/26.
 */
public class AlipayTool {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016052701452069";
//    public static final String APPID = "2088221928432959";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANEtvPGDc5jf1OYD" +
            "pgqLGnnPWzL2SmJqMzYs2SjmqJhYhyyUESQptmXZIGnW1SihZwX7ECqhkpEaeYld" +
            "W2tnt8iBGOA0z6Ou3dPOVXjr8ngagqNC2hyAfLyrvdIu81AQlUnZlpN4N+8Hq9b0" +
            "YpXIOfmULJ95KPljHJo1PxBR/66TAgMBAAECgYACEXm0yZ/y+wOX4qFZqVZxreMw" +
            "9c52eqZW+sqK5Pz1xKpRfoVM3jy3dRYk9cLVzALAxGK8iSxx0tkwyoOE5Fmk1U1O" +
            "Pmdc0TLQlpP8QQdR+gY43X6OGu8cNre4O3xg7VZ3UKRjjnSYQg7eilTxWyepNdSa" +
            "ScSu9cYIhCjQzpH1QQJBAPHRrDHsfMIiFTRsfs1x6V/xf7TF0vbt5OQ4OxOUG+8P" +
            "BxikNAL/LV4sjXfSquxdspzA06cygZt3ewp3hNYThe0CQQDdcgyesnjgPJ5yLWOY" +
            "WXHAzzUMul8Sjy34iSpiPdr1hGwxQuMTL8iD6oHmpJV8pNME7VRdbkdm24aY3t3m" +
            "SXZ/AkAqHZBV6ZAY54K17Kdw9IPmt9K8EzAY3Xnd3YU8dbEfw4hC3GZKl1K5chz5" +
            "X3FxVShEcLjsB7nW78o4GnTCLAhJAkB8NNcdQC+KXpXkps7BChJCujYgMHzY9RQs" +
            "3gq21cj1gtQIgWLKRTfrveIkktYB9pUho1h5mzxTVfhV0FOYMkZTAkEAqt81LzPU" +
            "kunX9IJk8VHkxTKFklf6ecG6L/0QCb+9V/ENLOzFuG8wx8g4+R3Z87hEc8OaPSM1" +
            "JcCuswucuXxTww==";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZRlGNIHuBPtqUJVvns0euk" +
            "NLCqlVIDnO3UbljaPnKVl/reynzNKrlvbYSulHHAXw9MlN1676dAzxCCI2HdgUJC2Sm51ybskeZu1+1+iajyrTXwx5ID1kVJiuldCOM8CgIr" +
            "QacIesEAMQtQ4fvtEymkzfP7NPWH2fOBkes5me3KqAL3COk2D/NNE3qG1z5FYMOebuxHvMoWUEvqzyB+3SIeY0B1X7Q9aSu733dVaUNUzl" +
            "H2vyZDQjCM/SuTK3WIoNolO0Xil0uqXuuyS3Bx6d3XeBxFb2pNKJ5oa+VEs1+pRVu7uNF1158/fQSwXmobGB11JdfBMKbis3NDkCdVjf3Ag" +
            "MBAAECggEAN2qsK5lV7hnaj3ueafxw61WjTgylvuKyxdH/dEjNH3HxRkOGnE8rhtr7jHPfA1OvJrUeZeExTrWPwNQ0z4hFM8rxvkKiQgzIRHX" +
            "xmerys3+juOrr9OPPsozVdQGzpM5juxdL74F35j+crjzfVjRbDlLl+ABJav552ox8t6+qShfHZHTDSCiHjquiNwquyHcvm+ubPLBtm2KltB" +
            "UIH6pjf6gs3Ix/7mYItlW+D1Gx3wgg7//Qgk4cc9KLfprh7g7mhuUgK30ACqkx/cXrFA5MAXSkeUTBWNtvSfDbgfvzoXnq9Cq7TuWCRFU+42" +
            "7x39Ci0ROORLwYr1XjGnS33z32kQKBgQDmA4DssulMDO8enheoqa522KB+j4CRUcNuDzwKAV0abxx93HuU3h0yJTTVxy2APjL69VqLxK8teDo" +
            "Pq2X2K6zvhqLmmadCQYTn3PSNK7lwfonpKnsSv6PSNKaOL8rMnwhotn3z9ZCYo7ncyAbyZ5mUS8mdVJDsYkZ3Or5PhZ+IvwKBgQCql1sexwW" +
            "nhB9cflGf202t3nP18Nemm4RoYuzCPd2560F10ajoQ6jYDE/P2DAasFAjRIuxZ1DDyoLPULqcII8tP0NV1xq9aS5AL71YqhfDyGMFop58TujE" +
            "Xsd+Un++x+9BbOR03Y0VUwryR+6kslXPdml/NReAZzMA/VaasZimyQKBgAGG1m07xsalc1TdscSB09GgIHiWlUDhrQ1m9nwOhEKvqB3Bv+Q+E" +
            "S2LB62/k7K9NvNpe39f/9WqbaA7AANBSPWSDDTBolWTP8GWOt1RA/sWK+4rRQPb18eGZ/CbqpnvGEwxoeZjo+EPfSVVAo7LVz6fvifYLtoS0O" +
            "rrreWzOlrbAoGAMODxUN5Btn/a5IulI6KYnUrx+vQpNim1RJujrvw+/bh0pyeNuA8637yXWbxd/Cesro5DYG9z6sWpuXEuJvIFVDeeOYhKtx7" +
            "eC7IiFLeX25cdGE5CDuglNa7ahk1pIOb0nNBwnTOl6whCSK1nqXUXtTv+el5h/36SVKRCHkzoqlECgYEAszXGFFi/M1WTMC5yXfDyKHMT5eEp" +
            "Oxy9kDyJ7p+gi33rDs/9SDO6EMBVBA2XKJZS3X5xXN2ZpghuZnmon42yCuYnPS1QmEf+uZKmtdhJ0oPLkgwWQ43iUBHE2Zdj6Z+dc4ErSw5PUm" +
            "P3UfyQ3BcC5HgyPqjJiokmx54KR9CGTV8=";

    private static final int SDK_PAY_FLAG = 1;


    public static void showPayDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("快点目前稳定支持微信自动抢红包。\n现在打赏10元即可激活～")
                .setNegativeButton("免费试用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int freeCount = PreferenceTool.getInstance().getInt(PreferenceConstant.KEY_FREE_COUNT);

                        if (freeCount >= 0 && freeCount < 2) {
                            ToastTool.showWarn("剩余免费自动抢红包个数：" + (2 - freeCount));
                        } else {
                            ToastTool.showWarn("免费试用结束，请激活永久使用");
                        }

                    }
                }).setPositiveButton("立即激活", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                payV2(activity);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    private void showFailDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("没办法用支付宝购买？您也可以加入我们的官方QQ群与群主联系其他支付途径哦～")
                .setTitle("哎呀！支付失败了")
                .setNegativeButton("继续支付", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payV2(activity);

                    }
                }).setPositiveButton("我要免费使用", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                HelpActivity.go(activity);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastTool.showSuccess("支付成功");
                        MoneyApplication.userRegisterCode = MoneyApplication.registerCode;
                        PreferenceTool.getInstance().saveString(PreferenceConstant.KEY_REGISTER_CODE, MoneyApplication.userRegisterCode);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastTool.showError("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 支付宝支付业务
     */
    public static void payV2(final Activity activity) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            ToastTool.showWarn("需要配置APPID | RSA_PRIVATE");
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogTool.d("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion(Activity activity) {
        PayTask payTask = new PayTask(activity);
        String version = payTask.getVersion();
        ToastTool.showWarn(version + "");
    }

}
