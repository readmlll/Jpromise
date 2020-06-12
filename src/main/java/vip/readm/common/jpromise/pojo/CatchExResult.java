package vip.readm.common.jpromise.pojo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-12 14:59
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class CatchExResult<T> {

    public boolean isExcetion=false;
    public Exception exception;
    public int code;
    public String msg;
    public T errData;
}
