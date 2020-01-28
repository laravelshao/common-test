package com.laravelshao.common.test.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import rx.Observable;

import java.util.List;

/**
 * Observable 返回值处理器
 *
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
public class ObservableReturnValueHandler implements AsyncHandlerMethodReturnValueHandler {

    @Override
    public boolean isAsyncReturnValue(Object returnValue, MethodParameter returnType) {
        return returnValue != null && supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return Observable.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

        if (returnValue == null) {
            mavContainer.setRequestHandled(true);
            return;
        }

        final Observable<?> observable = Observable.class.cast(returnValue);
        WebAsyncUtils.getAsyncManager(webRequest)
                .startDeferredResultProcessing(new ObservableAdapter<>(observable), mavContainer);
    }

    public class ObservableAdapter<T> extends DeferredResult<List<T>> {
        ObservableAdapter(Observable<T> observable) {
            observable.toList().subscribe(this::setResult, this::setErrorResult);
        }
    }
}