package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFine(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        ProtoTypeBean prototypeBean1 = ac.getBean(ProtoTypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        ProtoTypeBean prototypeBean2 = ac.getBean(ProtoTypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, ProtoTypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean{

        @Autowired
        private Provider<ProtoTypeBean> protoTypeBeanProvider;

        public int logic(){
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.get();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }
    }

//    @Scope("singleton")
//    static class ClientBean{
//        private final ProtoTypeBean protoTypeBean; //생성시점에 주입되어있음.
//
//        public ClientBean(ProtoTypeBean protoTypeBean) {
//            this.protoTypeBean = protoTypeBean;
//        }
//
//        public int logic(){
//            protoTypeBean.addCount();
//            return protoTypeBean.getCount();
//        }
//    }

    @Scope("prototype")
    static class ProtoTypeBean{
        private int count = 0;
        public  void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("ProtoTypeBean.init" + this) ;
        }

        @PreDestroy
        public void destroy(){
            System.out.println("ProtoTypeBean.destory");
        }
    }
}
