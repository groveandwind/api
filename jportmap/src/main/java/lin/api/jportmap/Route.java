/*
 * Route.java
 *
 * Created on 2006年12月28日, 下午12:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package lin.api.jportmap;

import com.sun.deploy.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 转发任务的配置数据对象模板
 * <p>Company: www.NetJava.org</p>
 *
 * @author javafound
 */
@Getter
public class Route {

    public Route(String localIp, int localPort, String destHost, int destPort) {
        this.isAll = true;
        this.allowClient.clear();
        Route(localIp, localPort, destHost, destPort);
    }

    public Route(String localIp, int localPort, String destHost, int destPort, List<String> allowClient) {
        this.isAll = false;
        this.allowClient.addAll(allowClient);
        Route(localIp, localPort, destHost, destPort);
    }

    private void Route(String localIp, int localPort, String destHost, int destPort) {
        this.localIP = localIp;
        this.localPort = localPort;
        this.destHost = destHost;
        this.destPort = destPort;
    }

    //jPortMap绑定的IP
    private String localIP;
    //监听的端口
    private int localPort = 0;
    //转发数据的目标机器IP
    @Setter
    private String destHost;
    //转发的目标端口
    @Setter
    private int destPort = 0;
    @Setter
    private Boolean isAll = false;
    //这个转发上许可进入的IP列表
    @Setter
    private List<String> allowRule = new ArrayList<String>();
    //获得权限的IP
    private Queue<String> allowClient = new ConcurrentLinkedQueue<>();

    //重写的toString方法，输出具体Route对象的信息以便debug
    @Override
    public String toString() {
        StringBuffer stb = new StringBuffer();
        stb.append(" LocalADD  " + localIP).append(" :" + localPort)
                .append(" --->DestHost " + destHost).append(" :" + destPort)
                .append("。此映射" + (isAll ? "不限制客户端IP" : "限制客户端IP"));
        if (!isAll) {
            stb.append("允许链接的客户端IP为：" + StringUtils.join(allowClient, ","));
        }
        return stb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return localPort == route.localPort &&
                destPort == route.destPort &&
                Objects.equals(localIP, route.localIP) &&
                Objects.equals(destHost, route.destHost) &&
                Objects.equals(isAll, route.isAll) &&
                Objects.equals(allowRule, route.allowRule);
    }

    public boolean isMe(Route r) {
        if (localPort == r.getLocalPort()) {
            return true;
        }
        return false;
    }
}
