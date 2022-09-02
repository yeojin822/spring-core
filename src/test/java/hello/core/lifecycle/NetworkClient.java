package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


public class NetworkClient{
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작히 호출
    public void connect(){
        System.out.println("connect : " + url);
    }

    public void call(String message){
        System.out.println("call : " + url + " message = " + message);
    }
    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close : " + url);
    }

    @PreDestroy
    public void close() throws Exception {
        disconnect();
    }

    @PostConstruct
    public void init() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }
}

//  인터페이스(InitializingBean, DisposableBean) 잘 사용하지 않음
//public class NetworkClient implements InitializingBean, DisposableBean {
//    private String url;
//
//    public NetworkClient() {
//        System.out.println("생성자 호출, url = " + url);
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    //서비스 시작히 호출
//    public void connect(){
//        System.out.println("connect : " + url);
//    }
//
//    public void call(String message){
//        System.out.println("call : " + url + " message = " + message);
//    }
//    // 서비스 종료시 호출
//    public void disconnect(){
//        System.out.println("close : " + url);
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        disconnect();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("초기화 연결 메세지");
//    }
//}
