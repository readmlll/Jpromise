package vip.readm.common.jpromise.pojo;

import lombok.*;
import lombok.experimental.Accessors;
import vip.readm.common.jpromise.face.Task;

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
@Data
public class TaskWarp<T,E extends CatchExResult> {

    private Task<T,E> task;
    private TaskResult<T,E> result=new TaskResult<T,E>();
}
