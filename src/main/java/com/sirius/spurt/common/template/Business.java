package com.sirius.spurt.common.template;

import com.sirius.spurt.common.template.Business.Dto;
import com.sirius.spurt.common.template.Business.Result;

public interface Business<I extends Dto, O extends Result> {
    O execute(final I input);

    interface Dto {}

    interface Result {}
}
