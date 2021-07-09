package org.jeecg.modules.demo.gumbo.util;



import java.util.Map;

public class CommonUtil {
    /**
     * object转map
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
