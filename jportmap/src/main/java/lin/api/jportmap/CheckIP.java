package lin.api.jportmap;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author lin
 * @date 2018/9/3
 */
public class CheckIP {
    /**
     * 更新过滤规则
     *
     * @param route
     */
    public static void updateRoute(Route route) {
        if (route.getIsAll()) {
            route.getAllowClient().clear();
        }
        Iterator<String> clients = route.getAllowClient().iterator();
        while (clients.hasNext()) {
            String client = clients.next();
            if (!checkIP(client, route.getAllowRule())) {
                clients.remove();
            }
        }
    }

    /**
     * 校验IP
     *
     * @param route
     * @param inIP
     * @return
     */
    public static boolean checkIP(Route route, String inIP) {
        if (route.getIsAll()) {
            return true;
        }
        if (route.getAllowClient().contains(inIP)) {
            return true;
        }
        if (checkIP(inIP, route.getAllowRule())) {
            route.getAllowClient().add(inIP);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验IP是否符合规则
     *
     * @param inIP
     * @param rules
     * @return
     */
    private static boolean checkIP(String inIP, List<String> rules) {
        boolean result = false;
        String[] inI = string2StringArray(inIP, ".");
        for (String rule : rules) {
            String[] list = string2StringArray(rule, ".");
            if (inI.length == list.length) {
                for (int i = 0; i < inI.length; i++) {
                    if ((inI[i].equals(list[i])) || (list[i].equals("*"))) {
                        result = true;
                        break;
                    }
                }
            }
            if (result) {
                break;
            }
        }
        return result;
    }

    /**
     * 把字符串数组用separator衔接为一个字符串
     *
     * @param srcString 原字符串
     * @param separator 分隔符
     * @return 目的数组
     */
    private static final String[] string2StringArray(String srcString,
                                                     String separator) {
        int index = 0;
        String[] temp;
        StringTokenizer st = new StringTokenizer(srcString, separator);
        temp = new String[st.countTokens()];
        while (st.hasMoreTokens()) {
            temp[index] = st.nextToken().trim();
            index++;
        }
        return temp;
    }
}
