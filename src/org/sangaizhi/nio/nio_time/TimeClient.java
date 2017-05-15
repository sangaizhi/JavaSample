/**
 * 文件名称: TimeClient
 * 系统名称: JavaSample
 * 模块名称:
 * 软件版权:
 * 功能说明:
 * 系统版本: 1.0.0.0
 * 开发人员: sangaizhi
 * 开发时间: 2017/5/14 23:20
 * 审核人员:
 * 相关文档:
 * 修改记录:
 * 修改日期:
 * 修改人员：
 * 修改说明：
 */
package org.sangaizhi.nio.nio_time;

/**
 * @name TimeClient
 * @author sangaizhi
 * @date 2017/5/14  23:20
 * @version 1.0
 */
public class TimeClient {



    public static void main(String[] args) {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(port);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        new Thread(new TimeClientHandle("127.0.0.1",8080), "Test-Client-001").start();
    }
}
