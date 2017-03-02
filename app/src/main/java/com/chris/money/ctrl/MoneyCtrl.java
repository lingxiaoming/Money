package com.chris.money.ctrl;
/**
 * User: xiaoming
 * Date: 2016-05-29
 * Time: 13:37
 * 抢红包流程,当抢红包开关打开就会进入这里
 */

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.chris.money.MoneyApplication;
import com.chris.money.MoneyService;
import com.chris.money.bean.Money;
import com.chris.money.constant.UIConstants;
import com.chris.money.db.MoneyOperate;
import com.chris.money.tools.DateFormatTool;
import com.chris.money.tools.LogTool;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by apple on 16/5/29.
 */
public class MoneyCtrl {

    private static final String TAG = "MoneyCtrl";
    private static final String WECHAT_RED_PACKAGE = "[微信红包]";
    private boolean isAutoClickToRedPackageDetail = false;
    private boolean isAutoClickToRedPackageDialog = false;
    private boolean isAutoBackToChatActivity = false;
    private boolean robRedPackageSuccess = false;


    public void dispathRedpackage(final MoneyService moneyService, AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                List<CharSequence> texts = event.getText();
                if (!texts.isEmpty()) {
                    for (CharSequence text : texts) {
                        String content = text.toString();
                        LogTool.d(TAG, "notification text:" + content);
                        if (content.contains(WECHAT_RED_PACKAGE)) {
                            wakeUpAndUnlock(moneyService);
                            //模拟打开通知栏消息
                            if (event.getParcelableData() != null
                                    && event.getParcelableData() instanceof Notification) {
                                Notification notification = (Notification) event.getParcelableData();
                                final PendingIntent pendingIntent = notification.contentIntent;

                                try {
                                    pendingIntent.send();
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:

                if (TextUtils.equals(event.getClassName().toString(), UIConstants.ACTIVITY_MAIN)) {//主页面与聊天页面同属一个activity
                    if (isAutoBackToChatActivity) {
                        isAutoBackToChatActivity = false;

                        if (robRedPackageSuccess) {//上次抢到红包了
                            robRedPackageSuccess = false;
                            if (MoneyApplication.wechatAnswer) {
                                AccessibilityNodeInfo editNodeInfo = moneyService.findNodeInfoByClassName(event.getSource(), "android.widget.EditText");
                                if (editNodeInfo != null) {
                                    Bundle arguments = new Bundle();

                                    int replysSize = MoneyApplication.wechatAnswerList.size();
                                    if (replysSize > 0) {
                                        String reply = MoneyApplication.wechatAnswerList.get(new Random().nextInt(replysSize));
                                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, reply);
                                        editNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                                        final AccessibilityNodeInfo buttonNodeInfo = moneyService.findNodeInfoByTextAndClassName(event.getSource(), "发送", "android.widget.Button");
                                        if (buttonNodeInfo != null) {
                                            TimerTask task = new TimerTask() {

                                                public void run() {
                                                    moneyService.performClick(buttonNodeInfo);
                                                }

                                            };

                                            Timer timer = new Timer();
                                            timer.schedule(task, 2000);//这里做延时

                                        }
                                    }
                                }
                            }
                        }

                        return;
                    }
                    if (isChatActivity(moneyService, event)) {//如果是聊天页面才能点击红包

                        if (findLatestRedPackageAndClickIt(moneyService, event.getSource())) {//找到最后一个红包Item并点击它
                            isAutoClickToRedPackageDetail = true;
                            isAutoClickToRedPackageDialog = true;
                        } else {
                            isAutoClickToRedPackageDetail = false;
                            isAutoClickToRedPackageDialog = false;
                        }
                    }
                } else if (TextUtils.equals(moneyService.mTopClassname, UIConstants.ACTIVITY_DIALOG_REDPACKAGE)) {
                    final AccessibilityNodeInfo accessibilityNodeInfo = moneyService.findNodeInfoByClassName(event.getSource(), "android.widget.Button");

                    //todo 找到制定textview 过滤关键字 TalkApplication.getInstance().filters
                    TimerTask task = new TimerTask() {

                        public void run() {

                            if (moneyService.performClick(accessibilityNodeInfo)) {
                                robRedPackageSuccess = true;
                                isAutoClickToRedPackageDetail = true;
                            } else {
                                isAutoClickToRedPackageDetail = false;
                                if (isAutoClickToRedPackageDialog) {
                                    isAutoBackToChatActivity = true;
                                    moneyService.performBack(moneyService);//红包没来得及拆开
                                }
                            }

                        }

                    };

                    Timer timer = new Timer();
                    int delayTime = (int) (MoneyApplication.wechatDelay * 1000);
                    if (delayTime < 0) delayTime = 0;
                    timer.schedule(task, delayTime);//这里做延时，做0-x秒内随机抢


                } else if (TextUtils.equals(moneyService.mTopClassname, UIConstants.ACTIVITY_REDPACKAGE_SUCCESS)) {

                    if (isAutoClickToRedPackageDetail) {//是软件自动点进来抢红包的，就退出这个页面

                        AccessibilityNodeInfo moneyNumNodeInfo = moneyService.findNodeInfoByClassNameAndPartOfText(event.getSource(), ".", "android.widget.TextView");
                        if (moneyNumNodeInfo != null) {
                            try {
                                double money = Double.parseDouble(moneyNumNodeInfo.getText().toString());
                                double moneyInt = Math.ceil(money);
                                for (int i = 0; (i < moneyInt && i < 10); i++) {
// TODO 播放抢到红包声
//                                    MyApplication.getInstance().playMononey(i * 150);
                                }
//                                Toast.makeText(robService, "money! " +money+"元", Toast.LENGTH_SHORT).show();

                                Money money1 = new Money();
                                money1.timeLong = System.currentTimeMillis();
                                money1.timeYear = DateFormatTool.getYear(money1.timeLong);
                                money1.timeMonth = DateFormatTool.getMonth(money1.timeLong);
                                money1.timeDay = DateFormatTool.getDay(money1.timeLong);
                                money1.name = "";
                                money1.type = 0;
                                money1.money = (float) money;
                                MoneyOperate.getInstance().addWeChatMoney(money1);

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }

                        if (moneyService.performBack(moneyService)) {//退出需要知道是不是点击返回退出
                            isAutoBackToChatActivity = true;
                            isAutoClickToRedPackageDetail = false;
                        }
                    }
                }

                break;

            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED://窗口内容改变
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED://列表内容滚动，用这个来判断正在聊天

                if (event.getItemCount() != event.getToIndex() + 1) return;//不是最后一个item，什么都不管

                if (TextUtils.equals(moneyService.mTopClassname, UIConstants.ACTIVITY_MAIN)) {
                    if (isChatActivity(moneyService, event)) {

                        AccessibilityNodeInfo nodeInfo = event.getSource();
                        if (nodeInfo == null) break;
                        if (TextUtils.equals(nodeInfo.getClassName(), "android.widget.ListView") && nodeInfo.getChildCount() > 0) {
                            if (findLatestRedPackageAndClickIt(moneyService, nodeInfo)) {
                                isAutoClickToRedPackageDetail = true;
                                isAutoClickToRedPackageDialog = true;
                            } else {
                                isAutoClickToRedPackageDetail = false;
                                isAutoClickToRedPackageDialog = false;
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * 采取的办法是验证listview，然后判断listview下面是不是聊天操作栏
     *
     * @param moneyService
     * @param event
     * @return 是否是微信聊天页面
     */
    private boolean isChatActivity(MoneyService moneyService, AccessibilityEvent event) {
        if (moneyService == null || event == null) return false;

        AccessibilityNodeInfo rootNodeInfo = moneyService.getRootInActiveWindow();//得到rootView
        if (rootNodeInfo == null) return false;

        AccessibilityNodeInfo backImageView = moneyService.findNodeInfoByContentDescribeAndClassName(rootNodeInfo, "返回", "android.widget.ImageView");

        if (backImageView != null && TextUtils.equals(MoneyService.mTopClassname, UIConstants.ACTIVITY_MAIN)) {
            return true;
        }

        return false;
    }

    private boolean findLatestRedPackageAndClickIt(MoneyService robService, AccessibilityNodeInfo listviewNodeInfo) {
        if (robService == null || listviewNodeInfo == null) return false;

        AccessibilityNodeInfo listNodeInfo = robService.findNodeInfoByClassName(listviewNodeInfo, "android.widget.ListView");
        if (listNodeInfo == null) return false;

        AccessibilityNodeInfo lastItemOfList = listNodeInfo.getChild(listNodeInfo.getChildCount() - 1);
        if (lastItemOfList != null) {
            AccessibilityNodeInfo lastItemHasRedpackage = robService.findNodeInfoByText(lastItemOfList, "领取红包");
            if (lastItemHasRedpackage != null) {
                return clickGetRedPackageTextNodeInfo(robService, lastItemHasRedpackage);
            }else {
                lastItemHasRedpackage = robService.findNodeInfoByText(lastItemOfList, "查看红包");
                if(lastItemHasRedpackage != null)
                    return clickGetRedPackageTextNodeInfo(robService, lastItemHasRedpackage);
            }
        }

        int[] buttomArray = new int[2];
        AccessibilityNodeInfo lastRedpackageTextView = robService.findNodeInfoByTextLast(listNodeInfo, "领取红包");
        if(lastRedpackageTextView == null){
            lastRedpackageTextView = robService.findNodeInfoByTextLast(listNodeInfo, "查看红包");
        }
        if (lastRedpackageTextView == null) return false;

        Rect rect = new Rect();
        lastRedpackageTextView.getBoundsInScreen(rect);
        buttomArray[0] = rect.bottom;

        AccessibilityNodeInfo lastHadBeenOpenTextNodeInfo = robService.findLatestNodeInfoByClassNameAndPartOfTextStartAndEnd(listNodeInfo, "你领取了", "的红包", "android.widget.TextView");
        if (lastHadBeenOpenTextNodeInfo == null) {
            return clickGetRedPackageTextNodeInfo(robService, lastRedpackageTextView);
        }

        Rect rect2 = new Rect();
        lastHadBeenOpenTextNodeInfo.getBoundsInScreen(rect2);
        buttomArray[1] = rect2.bottom;

//        Toast.makeText(robService, buttomArray[0]+","+buttomArray[1], Toast.LENGTH_SHORT).show();
        if (buttomArray[0] > buttomArray[1]) {//红包在"你领取了xxx的红包"之后
            return clickGetRedPackageTextNodeInfo(robService, lastRedpackageTextView);
        }
        return false;
    }

    private boolean clickGetRedPackageTextNodeInfo(MoneyService robService, AccessibilityNodeInfo lastRedTextNodeInfo) {
        if (MoneyApplication.wechatIgnore) {
            AccessibilityNodeInfo parentNodeInfo = lastRedTextNodeInfo.getParent();
            if(parentNodeInfo == null){
                return false;
            }
            AccessibilityNodeInfo redContent = parentNodeInfo.getChild(0);//红包文字内容
            String text = redContent.getText().toString();
            for (String string : MoneyApplication.wechatIgnoreList) {
                if (TextUtils.equals(text, string)) {
                    return false;
                }
            }
        }
        return robService.performClick(lastRedTextNodeInfo);
    }

    public void wakeUpAndUnlock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

}
