package com.github.karamelsoft.testing.data.driven.testing.core;

import com.github.karamelsoft.testing.data.driven.testing.api.RuntimeIOException;
import com.github.karamelsoft.testing.data.driven.testing.api.Tester;
import com.github.karamelsoft.testing.data.driven.testing.api.operations.Save;
import com.github.karamelsoft.testing.data.driven.testing.core.utils.ExceptionUtils;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by frederic on 15/03/16.
 */
class ActiveTester<T> extends AbstractTester<T> {

    private final T value;

    ActiveTester(final AbstractTester<?> builder, T value) {
        super(builder);

        this.value = value;
    }

    @Override
    public <U> Tester<U> map(final Function<T, U> function) {
        return
            new ActiveTester<>(
                this,
                function.apply(value));
    }

    @Override
    public Tester<T> apply(final Consumer<T> consumer) {
        consumer.accept(value);

        return this;
    }

    @Override
    public Tester<T> save(final String fileName, final Save<T> strategy) {
        ExceptionUtils.toRuntime(() ->
            strategy.save(
                value,
                writeIntoTarget(fileName)));

        return this;
    }
}