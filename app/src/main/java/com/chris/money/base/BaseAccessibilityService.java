package com.chris.money.base;
/**
 * User: xiaoming
 * Date: 2016-05-13
 * Time: 22:47
 * 基类辅助服务，提供各种找控件的方法
 */


import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import com.chris.money.tools.LogTool;

import java.util.List;


public abstract class BaseAccessibilityService extends AccessibilityService {
    //工具性方法
    private static final String TAG = "BaseAccessibilityService";

    /**
     * 通过 文本 查找
     */
    public static AccessibilityNodeInfo findNodeInfoByText(AccessibilityNodeInfo nodeInfo, String text) {
        if (null == nodeInfo || TextUtils.isEmpty(text)) {
            return null;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static AccessibilityNodeInfo findNodeInfoByTextLast(AccessibilityNodeInfo nodeInfo, String text) {
        if (null == nodeInfo || TextUtils.isEmpty(text)) {
            return null;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (null == list || list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static AccessibilityNodeInfo getLatestChildNodeInfo(AccessibilityNodeInfo nodeInfo) {
        if (null == nodeInfo) {
            return null;
        }

        if(nodeInfo.getChildCount() < 1){
            return nodeInfo;
        }

        AccessibilityNodeInfo nodeInfoChild = nodeInfo.getChild(nodeInfo.getChildCount() - 1);
        return nodeInfoChild;
    }

    /**
     * 通过 关键字 先后查找
     */
    public static AccessibilityNodeInfo findNodeInfoByTexts(AccessibilityNodeInfo nodeInfo, String... texts) {
        if (null == nodeInfo || null == texts || texts.length <= 0) {
            return null;
        }
        for (String key : texts) {
            AccessibilityNodeInfo info = findNodeInfoByText(nodeInfo, key);
            if (null != info) {
                return info;
            }
        }
        return null;
    }

    /**
     * 通过 viewId 查找
     */
    public static AccessibilityNodeInfo findNodeInfoById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (null == nodeInfo || TextUtils.isEmpty(resId)) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 18) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if (null != list && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    /**
     * AccessibilityNodeInfo.getChildCount()和getChild(i)存在疑问？？？
     */
    /**
     * 通过 组件名称查找
     */
    public static AccessibilityNodeInfo findNodeInfoByClassName(AccessibilityNodeInfo nodeInfo, String className) {
        if (null == nodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo node = nodeInfo.getChild(i);
            if(null != node ){
                LogTool.d(TAG, "all className ("+i+") "+node.getClassName());
            }
            if (null != node && className.equals(node.getClassName())) {
                LogTool.d("BaseQhbService", "xzhhbls...findNodeInfosByClassName.node=" + node.toString());
                return node;
            }
        }
        return findNodeInfoByClassNameRecycle(nodeInfo, className);
    }

    /**
     * 通过 组件名称查找---递归， 遍历树中的第一项
     */
    private static AccessibilityNodeInfo findNodeInfoByClassNameRecycle(AccessibilityNodeInfo parentNodeInfo, String className) {
        if (null == parentNodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        if (className.equals(parentNodeInfo.getClassName())) {
            LogTool.d(TAG, "zyy...findNodeInfosByClassName.1.=" + parentNodeInfo.toString());
            return parentNodeInfo;
        }

        for (int i = 0; i < parentNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo nodeInfoResult =
                    findNodeInfoByClassNameRecycle(parentNodeInfo.getChild(i), className);
            if (null != nodeInfoResult) {
                LogTool.d(TAG, "zyy...findNodeInfosByClassName.2.=" + nodeInfoResult.toString());
                return nodeInfoResult;
            }
        }
        return null;
    }

    /**
     * AccessibilityNodeInfo.getChildCount()和getChild(i)存在疑问？？？
     */
    /**
     * 通过 组件名称查找
     */
    public static AccessibilityNodeInfo findLatestNodeInfoByClassName(AccessibilityNodeInfo nodeInfo, String className) {
        if (null == nodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        for (int i = nodeInfo.getChildCount()-1; i >= 0; i--) {
            AccessibilityNodeInfo node = nodeInfo.getChild(i);
            if(null != node ){
                LogTool.d(TAG, "all className ("+i+") "+node.getClassName());
            }
            if (null != node && className.equals(node.getClassName())) {
                LogTool.d("BaseQhbService", "xzhhbls...findNodeInfosByClassName.node=" + node.toString());
                return node;
            }
        }
        return findLatestNodeInfoByClassNameRecycle(nodeInfo, className);
    }

    /**
     * 通过 组件名称查找---递归， 遍历树中的第一项
     */
    private static AccessibilityNodeInfo findLatestNodeInfoByClassNameRecycle(AccessibilityNodeInfo parentNodeInfo,
                                                                              String className) {
        if (null == parentNodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        if (className.equals(parentNodeInfo.getClassName())) {
            LogTool.d(TAG, "zyy...findNodeInfosByClassName.1.=" + parentNodeInfo.toString());
            return parentNodeInfo;
        }

        for (int i = parentNodeInfo.getChildCount()-1; i >= 0; i--) {
            AccessibilityNodeInfo nodeInfoResult =
                    findNodeInfoByClassNameRecycle(parentNodeInfo.getChild(i), className);
            if (null != nodeInfoResult) {
                LogTool.d(TAG, "zyy...findNodeInfosByClassName.2.=" + nodeInfoResult.toString());
                return nodeInfoResult;
            }
        }
        return null;
    }

    //    private AccessibilityNodeInfo fun(AccessibilityNodeInfo parentNodeInfo, String className)
    //    {
    //        APPLog.d("xzhhbls...findNodeInfosByClassNameRecycle");
    //        if (null == parentNodeInfo || TextUtils.isEmpty(className))
    //        {
    //            return null;
    //        }
    //
    //        for (int i = 0; i < parentNodeInfo.getChildCount(); i++)
    //        {
    //            AccessibilityNodeInfo childNodeInfo = parentNodeInfo.getChild(i);
    //            if (className.equals(childNodeInfo.getClassName()))
    //            {
    //                return childNodeInfo;
    //            }
    //            AccessibilityNodeInfo nodeInfoResult = findNodeInfoByClassNameRecycle(childNodeInfo, className);
    //            if (null != nodeInfoResult)
    //            {
    //                return nodeInfoResult;
    //            }
    //        }
    //        return null;
    //    }

    /**
     * @param nodeInfo
     * @param contentDsc
     * @param className
     *
     * @return AccessibilityNodeInfo
     *
     * @function findNodeInfoByContentDescribeAndClassName
     * @Description 先匹配className,再匹配contentDescription
     * @author lingxiaoming
     * @date 2016-5-14下午10:30:39
     */
    public static AccessibilityNodeInfo findNodeInfoByContentDescribeAndClassName(AccessibilityNodeInfo nodeInfo, String contentDsc,
                                                                                  String className) {
        if (null == nodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo node = nodeInfo.getChild(i);
            if(null != node ){
                LogTool.d(TAG, "all className ("+i+") "+node.getClassName());
            }
            if (null != node && className.equals(node.getClassName()) && contentDsc.equals(node.getContentDescription())) {
                LogTool.d("BaseQhbService", "xzhhbls...findNodeInfosByClassName.node=" + node.toString());
                return node;
            }
        }
        return findNodeInfoByClassNameRecycleAndContentDescription(nodeInfo, contentDsc, className);
    }

    /**
     * 通过 组件名称查找---递归， 遍历树中的第一项
     */
    private static AccessibilityNodeInfo findNodeInfoByClassNameRecycleAndContentDescription(AccessibilityNodeInfo parentNodeInfo,
                                                                                             String contentDsc, String className) {
        if (null == parentNodeInfo || TextUtils.isEmpty(className)) {
            return null;
        }
        if (className.equals(parentNodeInfo.getClassName()) && contentDsc.equals(parentNodeInfo.getContentDescription())) {
            LogTool.d(TAG, "zyy...findNodeInfosByClassName.1.=" + parentNodeInfo.toString());
            return parentNodeInfo;
        }

        for (int i = 0; i < parentNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo nodeInfoResult =
                    findNodeInfoByClassNameRecycleAndContentDescription(parentNodeInfo.getChild(i), contentDsc, className);
            if (null != nodeInfoResult) {
                LogTool.d(TAG, "zyy...findNodeInfosByClassName.2.=" + nodeInfoResult.toString());
                return nodeInfoResult;
            }
        }
        return null;
    }

    /**
     * @param nodeInfo
     * @param text
     * @param className
     *
     * @return AccessibilityNodeInfo
     *
     * @function findNodeInfoByContentDscAndClassName
     * @Description 先找text，再匹配className
     * @author xiazehong
     * @date 2016-3-23下午4:42:39
     */
    public static AccessibilityNodeInfo findNodeInfoByTextAndClassName(AccessibilityNodeInfo nodeInfo, String text,
                                                                       String className) {
        if (null == nodeInfo || TextUtils.isEmpty(text) || TextUtils.isEmpty(className)) {
            return null;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (null == list || list.isEmpty()) {
            return null;
        }

        for (AccessibilityNodeInfo node : list) {
            className.equals(node.getClassName());
            return node;
        }
        return null;
    }

    /**
     * @param nodeInfo
     * @param text
     * @param className
     *
     * @return AccessibilityNodeInfo
     *
     * @function findNodeInfoByContentDscAndClassName
     * @Description 先匹配className，再找text
     * @author lingxiaoming
     * @date 2016-5-21下午4:42:39
     */
    public static AccessibilityNodeInfo findNodeInfoByClassNameAndPartOfText(AccessibilityNodeInfo nodeInfo, String text,
                                                                             String className) {
        if (null == nodeInfo || TextUtils.isEmpty(text) || TextUtils.isEmpty(className)) {
            return null;
        }

        if(TextUtils.equals(nodeInfo.getClassName(), className)){
            if(nodeInfo.getText() != null) {
                String realtext = nodeInfo.getText().toString();
                if (realtext != null && realtext.contains(text)) {
                    return nodeInfo;
                }
            }
        }

        for(int i = 0; i < nodeInfo.getChildCount(); i++){
            AccessibilityNodeInfo nodeInfoChild = nodeInfo.getChild(i);
            AccessibilityNodeInfo nodeInfo1 = findNodeInfoByClassNameAndPartOfText(nodeInfoChild, text, className);
            if(nodeInfo1 != null){
                return nodeInfo1;
            }
        }

        return null;
    }

    /**
     * @param nodeInfo
     * @param textStart
     * @param textEnd
     * @param className
     *
     * @return AccessibilityNodeInfo
     *
     * @function findLatestNodeInfoByClassNameAndPartOfTextStartAndEnd
     * @Description 先匹配className，再找text
     * @author lingxiaoming
     * @date 2016-5-21下午4:42:39
     */
    public static AccessibilityNodeInfo findLatestNodeInfoByClassNameAndPartOfTextStartAndEnd(AccessibilityNodeInfo nodeInfo, String textStart, String textEnd,
                                                                                              String className) {
        if (null == nodeInfo || TextUtils.isEmpty(textStart) || TextUtils.isEmpty(textEnd) || TextUtils.isEmpty(className)) {
            return null;
        }

        if(TextUtils.equals(nodeInfo.getClassName(), className)){
            if(nodeInfo.getText() != null) {
                String realtext = nodeInfo.getText().toString().trim();
                if (realtext != null && realtext.startsWith(textStart) && realtext.endsWith(textEnd)) {
                    return nodeInfo;
                }
            }
        }

        for(int i = nodeInfo.getChildCount() - 1; i >= 0 ; i--){
            AccessibilityNodeInfo nodeInfoChild = nodeInfo.getChild(i);
            AccessibilityNodeInfo nodeInfo1 = findLatestNodeInfoByClassNameAndPartOfTextStartAndEnd(nodeInfoChild, textStart, textEnd, className);
            if(nodeInfo1 != null){
                return nodeInfo1;
            }
        }

        return null;
    }

    //    private static final Field sSourceNodeField;
    //
    //    static
    //    {
    //        Field field = null;
    //        try
    //        {
    //            field = AccessibilityNodeInfo.class.getDeclaredField("mSourceNodeId");
    //            field.setAccessible(true);
    //        }
    //        catch (Exception e)
    //        {
    //            e.printStackTrace();
    //        }
    //        sSourceNodeField = field;
    //    }
    //
    //    public static long getSourceNodeId(AccessibilityNodeInfo nodeInfo)
    //    {
    //        if (sSourceNodeField == null)
    //        {
    //            return -1;
    //        }
    //        try
    //        {
    //            return sSourceNodeField.getLong(nodeInfo);
    //        }
    //        catch (Exception e)
    //        {
    //            e.printStackTrace();
    //        }
    //        return -1;
    //    }

    //    public static String getViewIdResourceName(AccessibilityNodeInfo nodeInfo)
    //    {
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
    //        {
    //            return nodeInfo.getViewIdResourceName();
    //        }
    //        return null;
    //    }

    /**
     * 返回主界面事件
     */
    public static void performHome(AccessibilityService service) {
        if (null == service) {
            return;
        }
        if (!service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)) {
            gotoHomeLauncher(service);
        }
    }

    /**
     * 返回事件
     */
    public static boolean performBack(AccessibilityService service) {
        if (null == service) {
            return false;
        }
        return service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    /**
     * 点击事件
     */
    public static boolean performClick(AccessibilityNodeInfo nodeInfo) {
        if (null == nodeInfo) {
            return false;
        }
        if (nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            return performClick(nodeInfo.getParent());
        }
    }

    public static void gotoHomeLauncher(Context ctx) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(i);
    }

    //    protected void noticeJiaHongBao()
    //    {
    //        Intent i = new Intent(this, QhbActivity.class);
    //        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //        i.putExtra(QhbActivity.INTENT_KEY, QhbActivity.INTENT_JIAHONGBAO);
    //        startActivity(i);
    //    }
}

