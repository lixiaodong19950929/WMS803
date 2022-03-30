package com.wms.quartz.util;

import com.wms.quartz.domain.SysJob;
import org.quartz.JobExecutionContext;


public class QuartzJobExecution extends AbstractQuartzJob
{
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception
    {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
