package com.hyeonjin.admin.config.auth;

import com.hyeonjin.admin.config.auth.dto.OAuthAttributes;
import com.hyeonjin.admin.config.auth.dto.SessionUser;
import com.hyeonjin.admin.domain.user.User;
import com.hyeonjin.admin.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // registrationId -> 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 예를 들어 구글 로그인인지 네이버 로그인인지 구별

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        // userNameAttributeName -> OAuth2 로그인 진행 시 키가 되는 필드값을 의미
        // 구글은 코드를 지원,("sub") 네이버 카카오는 지원하지 않음
        // 네이버 로그인과 구글 로그인을 동시 지원할 때 사용됨

        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // OAuth2UserService 로 가져온 OAuth2User의 attribute를 담을 클래스
        // 네이버와 다른 로그인도 이 클래스 사용

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));
        // SessionUser -> 세션에 사용자 정보를 저장하기 위한 DTO 클래스
        // 왜 User 클래스를 사용하지 않고 새로 만들까?
        // => User 클래스를 세션에 저장하려고 하면 User 클래스에 직렬화를 구현하지 않았다는 의미의 에러가 발생
        // => 그렇다고 User에 직렬화를 구현한다? -> User가 Entity이기 때문에 언제 다른 Entity와 관계를 형성할지 모름
        // => @OneToMany, @ManyToMany등 자식 엔티티를 갖고 있으면 직렬화 대상이 자식들까지 포함되어 성능 이슈, 부수 효과 발생 확률 높아짐

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
