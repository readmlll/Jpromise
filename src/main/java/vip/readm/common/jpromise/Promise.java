package vip.readm.common.jpromise;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import vip.readm.common.jpromise.face.CatchEx;
import vip.readm.common.jpromise.face.Task;
import vip.readm.common.jpromise.face.Then;
import vip.readm.common.jpromise.pojo.CatchExResult;
import vip.readm.common.jpromise.pojo.TaskResult;
import vip.readm.common.jpromise.pojo.TaskWarp;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-12 14:59
 */
@EqualsAndHashCode
@ToString
public class Promise<T,E extends CatchExResult > {

    private Promise(){

    }

    Queue<TaskWarp<T,E>> taskQueue=new ConcurrentLinkedQueue<>();
    E lastError=null;

    public Promise(Task<T,E> task){
        synchronized (this){
            if(task!=null){
                TaskWarp<T,E> tkTaskWarp=new TaskWarp<T, E>();
                tkTaskWarp.setTask(task).setResult(new TaskResult<T,E>());
                taskQueue.add(tkTaskWarp);
            }
        }
    }


    public Promise<T,E> then(Then<T> processData){

         TaskWarp<T,E> task=null;

         synchronized (this){


             task=taskQueue.poll();
             try {

                 if(task==null){
                     return this;
                 }
                 AtomicReference<TaskWarp<T,E>> taskWarpReference=
                         new AtomicReference<>(task);

                 Promise<T,E> newPromise =task.getTask().runTask((data)->{
                     taskWarpReference.get().getResult().data=data;
                 },(error)->{
                     taskWarpReference.get().getResult().error= (E) error;
                 });
                 if(newPromise!=null && taskWarpReference.get().getResult().error==null){
                     addPromise(newPromise);
                 }
             }catch (Exception e){
                 TaskResult<T, E> finalTaskResult1 = task.getResult();
                 CatchExResult exResult=new CatchExResult<>();
                 exResult.isExcetion=true;
                 exResult.exception=e;
                 assert finalTaskResult1 != null;
                 finalTaskResult1.error=exResult;
             }

             TaskResult<T, E> finalTaskResult1 = task.getResult();
             if(finalTaskResult1.error==null){
                 //无错误
                 lastError=null;
                 processData.then(finalTaskResult1.data);
             }else{
                 //有异常 或者 业务错误  终止任务链
                 lastError= (E) finalTaskResult1.error;
                 taskQueue.clear();
             }
         }

        return this;
     }

     public Promise<T,E> addPromise(Promise<T,E> newPromise){
         if(newPromise!=null && lastError==null){
             taskQueue.addAll(newPromise.taskQueue);
         }
        return this;
     }


    public Promise<T,E> catchEx(CatchEx<E> processError){


        synchronized (this){
            if(lastError!=null){
                processError.catchEx(lastError);
                lastError=null;
            }
        }
        return this;
    }




}
