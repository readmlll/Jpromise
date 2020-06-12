package vip.readm.common.jpromise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.Object;
import vip.readm.common.jpromise.pojo.CatchExResult;

import static org.junit.Assert.*;

/**
 * @author Readm
 * @version 1.0
 * @date 2020-06-13 3:59
 */

public class PromiseTest {

    public Promise getServiceTask2(){

        int a=1;
        int b=3;
        Promise<String, CatchExResult<String>> promise=new Promise<>(null);


        promise.addPromise(new Promise<>((resolve1, reject1)->{
            switch (a){
                case 1:
                    resolve1.resolve("业务1完成");
                    break;
                case 2:
                    CatchExResult<String> exResult=new CatchExResult<>();
                    exResult.msg="业务1出错";
                    reject1.reject(exResult);
                    break;
                case 3:
                    throw  new RuntimeException("业务1异常");
            }
            return null;
        }))
        .addPromise(new Promise<>((resolve2, reject2)->{
            switch (b){
                case 1:
                    resolve2.resolve("业务2完成");
                    break;
                case 2:
                    CatchExResult<String> exResult=new CatchExResult<>();
                    exResult.msg="业务2出错";
                    reject2.reject(exResult);
                    break;
                case 3:
                    throw  new RuntimeException("业务2异常");
            }
            return null;
        }));

        return promise;

    };


    public Promise getServiceTask(){

        int a=1;
        int b=3;
        Promise<String, CatchExResult<String>> promise=new Promise<>((resolve,reject)->{
            switch (a){
                case 1:
                    resolve.resolve("业务1完成");
                    break;
                case 2:
                    CatchExResult<String> exResult=new CatchExResult<>();
                    exResult.msg="业务1出错";
                    reject.reject(exResult);
                    break;
                case 3:
                    throw  new RuntimeException("业务1异常");
            }


            return new Promise<String, CatchExResult<String>>((resolve2, reject2)->{
                switch (b){
                    case 1:
                        resolve2.resolve("业务2完成");
                        break;
                    case 2:
                        CatchExResult<String> exResult=new CatchExResult<>();
                        exResult.msg="业务2出错";
                        reject2.reject(exResult);
                        break;
                    case 3:
                        throw  new RuntimeException("业务2异常");
                }
                return null;
            });
        });

        return promise;
    }

    @Test
    public  void test() {

        Promise promise =getServiceTask2();

        promise.then((data)->{
            System.out.println(data);
        }).then((data -> {
            System.out.println(data);
        })).catchEx((error -> {
            System.out.println(error);
        }));


    }

}
