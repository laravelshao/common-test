package com.laravelshao.common.test.transformer;

/**
 * 将获取到的数据转换为实例对象
 *
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
public interface Transformer<T, R> {

    /**
     * 转换操作
     *
     * @param source 源类型数据
     * @return 结果类型数据
     */
    R transform(T source);
}