package com.mazeltov.custompoxy;

import com.mazeltov.postcontext.AfterReadyContext;

//@Benchmark
public class SuperDuperBusinessLogicImpl implements SuperDuperBusinessLogic {

    @AfterReadyContext
    @Override
    public void iShouldStartAfterContextIsReady() {
        System.out.println("Context is ready");
    }

    @Benchmark
    @Override
    public void doBusinessLogic() {
        System.out.println("super-duper-logic");
    }
}
