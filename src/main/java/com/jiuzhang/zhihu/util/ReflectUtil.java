package com.jiuzhang.zhihu.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.BeansException;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.core.convert.Property;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author L.cm
 */
@UtilityClass
public class ReflectUtil extends ReflectionUtils {

	@Nullable
	public static Field getField(Class<?> clazz, String fieldName) {
		while(clazz != Object.class) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException var3) {
				clazz = clazz.getSuperclass();
			}
		}

		return null;
	}
}
