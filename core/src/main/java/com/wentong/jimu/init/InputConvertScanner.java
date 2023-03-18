package com.wentong.jimu.init;

import cn.hutool.core.annotation.AnnotationUtil;
import com.wentong.jimu.flow.convert.InputConverter;
import com.wentong.jimu.service.Service;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 输入转换扫描器
 */
public class InputConvertScanner {

    private Map<String, String> map = new HashMap<>();

    public String getExpression(String service) {
        return map.get(service);
    }

    public static void main(String[] args) {
        // invoke init method
        new InputConvertScanner().init();
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        List<Annotation> annotations = AnnotationUtil.scanMetaAnnotation(InputConverter.class);
        InputConverter[] processes = AnnotationUtil.getAnnotations(Service.class.getMethod("process", Object.class), false, InputConverter.class);
        System.out.println(processes);


    }

}
