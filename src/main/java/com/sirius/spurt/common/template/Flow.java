package com.sirius.spurt.common.template;

import com.sirius.spurt.common.template.Flow.Dto;
import com.sirius.spurt.common.template.Flow.Result;

public interface Flow<I extends Dto, O extends Result> {
    O execute(final I input);

    interface Dto {}

    interface Result {}
}
