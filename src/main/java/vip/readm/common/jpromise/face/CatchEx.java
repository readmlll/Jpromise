package vip.readm.common.jpromise.face;



import vip.readm.common.jpromise.pojo.CatchExResult;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-12 14:59
 */
public interface CatchEx<E> {
    void catchEx(CatchExResult<E> error);
}
