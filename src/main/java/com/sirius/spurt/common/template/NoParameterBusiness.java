package com.sirius.spurt.common.template;

import com.sirius.spurt.common.template.Business.Result;

public interface NoParameterBusiness<O extends Result> {
    O execute();

    interface Result {}
}
