package spring.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * School
 *
 * @author YangQi
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class School {

    @Autowired
    Klass class1;

    @Resource(name = "student001")
    Student student100;
}
