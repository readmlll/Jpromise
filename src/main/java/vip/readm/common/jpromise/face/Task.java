package vip.readm.common.jpromise.face;


import vip.readm.common.jpromise.Promise;
import vip.readm.common.jpromise.pojo.CatchExResult;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-12 14:59
 */
public   interface Task<T,E extends CatchExResult>{
    Promise<T,E> runTask(Resolve<T> resolve, Reject<E> reject);
}

