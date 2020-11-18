package spring.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Class
 *
 * @author YangQi
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Klass {

    List<Student> students;
}
