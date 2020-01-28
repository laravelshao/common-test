package com.laravelshao.common.test.domain.dto;

/**
 * 学生DTO
 *
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
public class StudentDTO extends BaseStudentDTO {

    private String id;

    public StudentDTO(String name, int age, double credit, String stream) {
        super(name, age, credit, stream);
    }

    public StudentDTO(String name, int age, double credit, String stream, String id) {
        super(name, age, credit, stream);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
