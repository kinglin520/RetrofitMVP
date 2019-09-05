package com.example.dream.retrofitrxjavaokhttpdemo.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author wenlin
 * 缓存注解器
 *
 * 两种使用方法：
 * ①在Service协议类里的方法上面@Cache(path = "接口参数TYPE（替换自己的）")
 * ②在Service协议类里的方法上面@Cache(path = "接口参数TYPE（替换自己的）", mode = true)*
 * 然后再你的Activity或者Fragment里面，找到对应的接口回调，在Error里面如下写
 *      //加载数据，参考你的onSuccess里面的代码
 *
 * 注：第二种方法，当你需要对缓存数据特殊处理时，或者产品对你提出了一些很优秀（二）的需求，可以采用，备用方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    String path();//缓存接口路径

    boolean mode() default false;//false:网络层已经处理好缓存，不会走Error；true：业务逻辑需要自己处理，在Error里面
}
