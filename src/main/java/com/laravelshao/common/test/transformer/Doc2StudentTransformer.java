package com.laravelshao.common.test.transformer;

import com.laravelshao.common.test.domain.dto.BaseStudentDTO;
import com.laravelshao.common.test.domain.dto.StudentDTO;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * MongoDB 文档转换学生对象转换器
 *
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
public class Doc2StudentTransformer implements Transformer<Document, BaseStudentDTO> {

    private static final String ID = "_id";
    public static final String MAJOR = "major";
    public static final String CREDIT = "credit";
    public static final String AGE = "age";
    public static final String NAME = "name";

    @Override
    public StudentDTO transform(Document source) {
        return new StudentDTO(source.getString(NAME), source.getInteger(AGE),
                source.getDouble(CREDIT), source.getString(MAJOR), ((ObjectId) source.get(ID)).toString());
    }
}
