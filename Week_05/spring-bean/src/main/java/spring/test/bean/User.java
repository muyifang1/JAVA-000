package spring.test.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;

/**
 * User Bean
 *
 * @author YangQi
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements BeanNameAware {

    private int id;

    private String name;

    /**
     * 当前Bean名称
     */
    private transient String beanName;

    @Override
    public void setBeanName(String s) {
        this.beanName = name;
    }
}
