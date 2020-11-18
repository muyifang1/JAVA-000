package spring.test.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Student
 *
 * @author YangQi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private int id;

    private String name;
}
