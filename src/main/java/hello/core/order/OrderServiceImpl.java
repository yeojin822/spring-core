package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//사용 영역
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{


    /* 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드를 고쳐야 한다.
    *  DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존관계를 변경하면 된다. */
    //  private final MemberRepository memberRepository = new MemoryMemberRepository();
    //  private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //공연의 주인공을 자신이 직접 초빙하는 것과 같다. -> 기획자 등장(config)
    //  private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired //생성자가 한개이면 생략가능
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    // ==> @RequiredArgsConstructor



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return  new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
