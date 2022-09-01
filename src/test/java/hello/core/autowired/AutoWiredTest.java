package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void AutowiredOption(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        @Autowired(required = false) // 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        public void setNoBean1(Member setNoBean1) {
            System.out.println("setNoBean1 = " + setNoBean1);
        }


        @Autowired //자동 주입할 대상이 없으면 null이 입력된다.
        public void setNoBean2(@Nullable Member setNoBean2) {
            System.out.println("setNoBean2 = " + setNoBean2);
        }


        @Autowired //자동 주입할 대상이 없으면 Optional.empty 가 입력된다.
        public void setNoBean3(Optional<Member> setNoBean3) {
            System.out.println("setNoBean3 = " + setNoBean3);
        }

    }
}
