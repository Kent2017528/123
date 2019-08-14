package cn.smbms.sysfun;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Aspect
@Component
public class SystemLogAdvice {
    //获取日志对象
    Log log = LogFactory.getLog("SystemLogAdvice");

    @Pointcut("execution(* cn.smbms.service..*.*(..))")
    public void myPointCut(){}

    @Around("myPointCut()")
    public Object around(ProceedingJoinPoint pjp){
        Object rs = null;
        try{
        //获取方法参数
        Object []args = pjp.getArgs();
        log.debug("方法：["+pjp.getSignature().getName()+"]，入参：["+Arrays.toString(args)+"]");
        //调用业务方法
        rs = pjp.proceed(args);
        if(null != rs){
            log.debug("方法：["+pjp.getSignature().getName()+"]，执行结果："+rs);
        }
        return rs;
        }catch(Throwable e){
            throw new RuntimeException(e);
        }
    }
}
