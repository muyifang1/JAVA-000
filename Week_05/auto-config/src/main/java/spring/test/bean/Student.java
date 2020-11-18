package spring.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Student
 *
 * @author YangQi
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private int id;

    private String name;
}
