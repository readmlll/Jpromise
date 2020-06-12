package vip.readm.common.jpromise.face;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-12 14:59
 */
public interface Resolve<T> {
    void resolve(T data);
}
