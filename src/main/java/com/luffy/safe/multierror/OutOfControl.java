package com.luffy.safe.multierror;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布逸出
 * 解释：
 * 一个被声明为private的可更改对象对外部提了访问的api，就会有被修改的风险或者未被正确初始化的问题
 * 出现情况：
 * 1. 未初始化就进行对this赋值
 * 2. 监听器问题
 * 3. 构造中新开线程初始化共享数据
 *
 * @author sunzhangfei
 * @since 2022/8/1 4:27 下午
 */
public class OutOfControl {
    private Map<String, String> states;

    public OutOfControl() {
        states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
        states.put("5", "周五");
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) {
        OutOfControl errorCase = new OutOfControl();
        Map<String, String> states = errorCase.getStates();
        System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));
    }
}
