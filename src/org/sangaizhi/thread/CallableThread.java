package org.sangaizhi.thread;

import java.util.concurrent.Callable;

/********************************************
 * 文件名称: CallableThread 系统名称: MultiThread 模块名称: 软件版权: 深圳中云创科技有限公司 功能说明: 系统版本: 1.0.0.0 开发人员: saz 开发时间: 2016/12/28 审核人员:
 * 相关文档: 修改记录: 修改日期 修改人员 修改说明
 **/

public class CallableThread implements Callable<String>
{
    private int id = 0;
    
    public CallableThread(int id)
    {
        this.id = id;
    }
    
    @Override
    public String call()
        throws Exception
    {
        return "CallableThread:" + id;
    }
}
