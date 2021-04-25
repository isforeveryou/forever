package com.forever.utils;

import java.util.Objects;
import java.util.function.*;

/**
 * @author WJX
 * @date 2020/12/2 10:01
 */
public final class OptionalBean<T> {

    private final T value;

    private static final OptionalBean<?> EMPTY_BEAN = new OptionalBean();


    private OptionalBean() {
        this.value = null;
    }

    private OptionalBean(T value) {
        this.value = Objects.requireNonNull(value);
    }


    @SuppressWarnings("unchecked")
    public static<T> OptionalBean<T> empty() {
        return (OptionalBean<T>) EMPTY_BEAN;
    }

    public static <T> OptionalBean<T> of(T value) {
        return new OptionalBean<>(value);
    }

    public static <T> OptionalBean<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public static <T, X extends Throwable> OptionalBean<T> of(T value, Supplier<? extends X> exceptionSupplier) throws X {
        if (value == null) {
            throw exceptionSupplier.get();
        }
        return of(value);
    }


    public T get() {
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public void ifPresent(BooleanSupplier compareSupplier, Consumer<? super T> consumer) {
        if (value != null && compareSupplier.getAsBoolean()) {
            consumer.accept(value);
        }
    }

    public void ifPresent(Predicate<? super T> compareSupplier, Consumer<? super T> consumer) {
        if (value != null && compareSupplier.test(value)) {
            consumer.accept(value);
        }
    }


    public <E> OptionalBean<E> getBean(Function<? super T, ? extends E> function) {
        return value == null ? OptionalBean.empty() : OptionalBean.ofNullable(function.apply(value));
    }

    public T orElse(T other) {
        return value != null ? value : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return value != null ? value : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    public <X extends Throwable> T trueThenThrow(Predicate<? super T> compareSupplier, Supplier<? extends X> exceptionSupplier) throws X {

        if (value != null && compareSupplier.negate().test(value)) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    public <X extends Throwable> T falseThenThrow(Predicate<? super T> compareSupplier, Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null && compareSupplier.test(value)) {
            return value;
        }
        throw exceptionSupplier.get();
    }


}
