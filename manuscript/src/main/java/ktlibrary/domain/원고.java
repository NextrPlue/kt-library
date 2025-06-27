package ktlibrary.domain;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;



//<<< DDD / Value Object
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class 원고  {

    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String manuscriptTitle;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String manuscriptContent;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long authorId;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String authorName;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String authorIntroduction;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Date createdAt;
    
    
    
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Date updatedAt;

    public void register manuscript() {
    }
    public void edit manuscript() {
    }
    public void requestPublishing() {
    }


}

//>>> DDD / Value Object