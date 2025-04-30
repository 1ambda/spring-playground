package com.github.lambda.opsplatform.domain;

import com.github.lambda.opsplatform.domain.user.UserAuthorityRepository;
import com.github.lambda.opsplatform.domain.user.UserEntity;
import com.github.lambda.opsplatform.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserAuthorityRepository userAuthorityRepository;

  public UserEntity findByEmailOrThrow(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public void syncOidcAdditional(UserAggregate userAggregate, String email) {
    UserEntity user = userAggregate.getUser();
    user.sync(email);
    userRepository.save(user);
  }

  public UserAggregate findAggregateByEmailOrThrow(String email) {
    return userRepository.findAggregateByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
