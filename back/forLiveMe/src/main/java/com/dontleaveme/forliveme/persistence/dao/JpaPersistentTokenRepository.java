package com.dontleaveme.forliveme.persistence.dao;

import com.dontleaveme.forliveme.persistence.model.PersistentLogin;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

// PersistentTokenRepository에서 요구하는 네가지 메서드를 재정의(Override)하도록 합니다.
@Repository
public class JpaPersistentTokenRepository implements PersistentTokenRepository {

    // 스프링 팀에서 권장하는 생성자 DI를 이용합니다
    private final PersistentLoginRepository repository;

    public JpaPersistentTokenRepository(final PersistentLoginRepository repository) {
        this.repository = repository;
    }

    // 새로운 remember-me 쿠키를 발급할 때 담을 토큰을 생성하기 위한 메서드입니다.
    @Override
    public void createNewToken(final PersistentRememberMeToken token) {
        repository.save(PersistentLogin.from(token));
    }

    // 토큰을 변경할때 호출될 메서드입니다.
    @Override
    public void updateToken(final String series, final String tokenValue, final Date lastUsed) {
        repository.findBySeries(series)
                .ifPresent(persistentLogin -> {
                    persistentLogin.updateToken(tokenValue, lastUsed);
                    repository.save(persistentLogin);
                });
    }

    // 사용자에게서 remember-me 쿠키를 이용한 인증 요청이 들어올 경우 호출될 메서드입니다.
    // 사용자가 보내온 쿠키에 담긴 시리즈로 데이터베이스를 검색해 토큰을 찾습니다.
    @Override
    public PersistentRememberMeToken getTokenForSeries(final String seriesId) {
        return repository.findBySeries(seriesId)
                .map(persistentLogin ->
                        new PersistentRememberMeToken(
                                persistentLogin.getUsername(),
                                persistentLogin.getSeries(),
                                persistentLogin.getToken(),
                                persistentLogin.getLastUsed()
                        ))
                .orElseThrow(IllegalArgumentException::new);
    }

    // 세션이 종료될 경우 데이터베이스에서 영구 토큰을 제거합니다.
    @Override
    public void removeUserTokens(final String username) {
        repository.deleteAllInBatch(repository.findByUsername(username));
    }
}
