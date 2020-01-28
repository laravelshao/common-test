package com.laravelshao.common.test.dal.repository;

import com.laravelshao.common.test.domain.dto.BaseStudentDTO;
import com.laravelshao.common.test.domain.dto.StudentDTO;
import com.laravelshao.common.test.transformer.Doc2StudentTransformer;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import com.mongodb.rx.client.Success;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;


/**
 * @author qinghua.shao
 * @date 2020/1/26
 * @since 1.0.0
 */
@Slf4j
@Repository
public class StudentDaoImpl implements StudentDao {

    private MongoCollection<Document> collection;

    @Autowired
    public StudentDaoImpl(@Value("${spring.data.mongodb.uri}") String connectionUrl,
                          @Value("${spring.data.mongodb.database}") String dbName) {
        MongoClient mongoClient = MongoClients.create(connectionUrl);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection("student");
    }

    @Override
    public Observable<Success> createStudent(BaseStudentDTO student) {
        return collection.insertOne(createStudentDocument(student))
                .doOnNext(s -> log.debug("Student was created successfully."))
                .doOnError(e -> log.error("An ERROR occurred while creating a new Student", e));
    }

    private Document createStudentDocument(BaseStudentDTO student) {
        return new Document(Doc2StudentTransformer.NAME, student.getName())
                .append(Doc2StudentTransformer.AGE, student.getAge())
                .append(Doc2StudentTransformer.CREDIT, student.getCredit())
                .append(Doc2StudentTransformer.MAJOR, student.getMajor());
    }

    @Override
    public Observable<StudentDTO> findByName(String name) {

        log.debug("Fetching the student with name: " + name);
        return collection.find(eq(Doc2StudentTransformer.NAME, name))
                .toObservable()
                .map(document -> new Doc2StudentTransformer().transform(document))
                .doOnNext(s -> log.debug("Student with the given name was retrieved."))
                .doOnError(e -> log.error("An ERROR occurred while fetching the student", e));
    }
}
