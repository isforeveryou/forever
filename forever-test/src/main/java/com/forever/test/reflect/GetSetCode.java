package com.forever.test.reflect;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author WJX
 * @date 2021/4/25 11:09
 */
public class GetSetCode {

    public static void getSetCode(String write, String read, Class<?> javaBeanClazz) {
        Field[] fields = javaBeanClazz.getDeclaredFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
                String fieldName = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);

                if (property != null) {
                    System.out.println("\t\t// " + property.value());
                }
                System.out.println(write + ".set" + fieldName + "(" + read + ".get" + fieldName + "());");
            }
        }

    }


}
