package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.CustomerApplication;
import lombok.Data;

@Entity
@Table(name = "Customer_table")
@Data
//<<< DDD / Aggregate Root
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean isKtUser;

    private Date createdAt;

    private Date updatedAt;
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Subsciption subscription;


    public static CustomerRepository repository() {
        CustomerRepository customerRepository = CustomerApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerUser(RegisterUserCommand registerUserCommand) {
        //implement business logic here:

        // 입력 값 검증
        if (registerUserCommand.getEmail() == null || registerUserCommand.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        if (registerUserCommand.getName() == null || registerUserCommand.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (registerUserCommand.getPassword() == null || registerUserCommand.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        // 이메일 중복 확인
        if (repository().findByEmail(registerUserCommand.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        this.name = registerUserCommand.getName();
        this.email = registerUserCommand.getEmail();
        this.password = registerUserCommand.getPassword();
        this.isKtUser = registerUserCommand.getIsKtUser() != null ? registerUserCommand.getIsKtUser() : false;
        
        CustomerRegistered customerRegistered = new CustomerRegistered(this);
        customerRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    
    //<<< Clean Arch / Port Method
    public boolean validatePassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
    
    public static LoginResponse login(LoginCommand loginCommand, CustomerRepository repository) {
        // 이메일로 사용자 찾기
        java.util.Optional<Customer> customerOpt = repository.findByEmail(loginCommand.getEmail());
        
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("등록되지 않은 이메일입니다.");
        }
        
        Customer customer = customerOpt.get();
        
        // 비밀번호 검증
        if (!customer.validatePassword(loginCommand.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        // 임시 토큰 생성 (실제로는 JWT 등을 사용)
        String token = "temp_token_" + customer.getId() + "_" + System.currentTimeMillis();
        
        String message = "고객 로그인 성공";
        
        return new LoginResponse(customer, token, message);
    }
    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestBook(RequestBookCommand requestBookCommand) {
        //implement business logic here:

        BookRequested bookRequested = new BookRequested(this);
        bookRequested.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
