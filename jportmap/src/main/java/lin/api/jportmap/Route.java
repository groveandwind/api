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

import java.util.ArrayList;
import java.util.List;

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
    private String destHost;
    //转发的目标端口
    private int destPort = 0;
    private Boolean isAll = false;
    //这个转发上许可进入的IP列表
    private List<String> allowClient = new ArrayList<String>();

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
}
