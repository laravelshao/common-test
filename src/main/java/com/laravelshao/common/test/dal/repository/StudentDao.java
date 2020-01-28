package com.laravelshao.common.test.dal.repository;

import com.laravelshao.common.test.domain.dto.BaseStudentDTO;
import com.laravelshao.common.test.domain.dto.StudentDTO;
import com.mongodb.rx.client.Success;
import rx.Observable;

/**
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
public interface StudentDao {

    /**
     * 创建学生
     *
     * @param student
     * @return
     */
    Observable<Success> createStudent(BaseStudentDTO student);

    /**
     * 根据名称查询学生
     *
     * @param name
     * @return
     */
    Observable<StudentDTO> findByName(String name);
}
