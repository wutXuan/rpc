package com.rpc.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

public class ConfigUtils {
    public static <T> T loadConfig(Class<T> tClass,String prefix){
        return loadConfig(tClass,prefix,"");
    }

    public static <T> T loadConfig(Class<T> tClass,String prefix,String environment){
        StringBuilder configBuilder = new StringBuilder("application");
        if(StrUtil.isNotBlank(environment)){
            configBuilder.append("-").append(environment);
        }
        configBuilder.append(".properties");
        Props props = new Props(configBuilder.toString());
        return props.toBean(tClass,prefix);
    }
}
