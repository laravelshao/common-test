package com.laravelshao.common.test.controller;

import com.laravelshao.common.test.dal.repository.StudentDao;
import com.laravelshao.common.test.domain.dto.BaseStudentDTO;
import com.laravelshao.common.test.domain.dto.StudentDTO;
import com.mongodb.rx.client.Success;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

/**
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
@Slf4j
@RestController
public class StudentController {

    private final StudentDao studentDao;

    @Autowired
    public StudentController(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @ApiOperation(value = "创建学生")
    @ApiImplicitParam(name = "student", value = "学生详细实体student", required = true, dataType = "BaseStudentDTO")
    @PostMapping(value = "/api/students/createStudent", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Observable<Success> createStudent(@RequestBody BaseStudentDTO student) {
        log.debug("创建学生");
        return studentDao.createStudent(student);
    }

    @ApiOperation(value = "查询学生")
    @ApiImplicitParam(name = "姓名", value = "学生姓名", required = true, dataType = "String")
    @GetMapping(value = "/api/students/getStudentByName", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Observable<StudentDTO> getStudentByName(@RequestParam String name) {
        log.debug("查询学生 => name:{} ", name);
        return studentDao.findByName(name);
    }
}