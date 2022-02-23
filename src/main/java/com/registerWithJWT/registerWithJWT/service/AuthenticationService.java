package com.registerWithJWT.registerWithJWT.service;

import com.registerWithJWT.registerWithJWT.entity.User;
import com.registerWithJWT.registerWithJWT.entity.UserAuthToken;
import com.registerWithJWT.registerWithJWT.exception.ApplicationException;
import com.registerWithJWT.registerWithJWT.exception.AuthenticationFailedException;
import com.registerWithJWT.registerWithJWT.exception.UserErrorCode;
import com.registerWithJWT.registerWithJWT.model.AuthorizedUser;
import com.registerWithJWT.registerWithJWT.provider.PasswordCryptographyProvider;
import com.registerWithJWT.registerWithJWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private UserRepository userRepository;


    @Transactional(propagation = Propagation.REQUIRED)
    public AuthorizedUser authenticate(final String email, final String password) throws ApplicationException {

        User user = userRepository.findByEmailId(email);
        if (user == null) throw new AuthenticationFailedException(UserErrorCode.USR_002);

        final String encryptedPassword = passwordCryptographyProvider.encrypt(password, user.getSalt());
        if (!user.getPassword().equals(encryptedPassword)) {

            throw new AuthenticationFailedException(UserErrorCode.USR_003);
        }
        UserAuthToken userAuthToken = authTokenService.issueToken(user);
        return authorizedUser(user, userAuthToken);


    }

    private AuthorizedUser authorizedUser(final User user, final UserAuthToken userAuthToken) {
        final AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setId(user.getEmailId());
        authorizedUser.setFirstName(user.getFirstName());
        authorizedUser.setLastName(user.getLastName());
        authorizedUser.setEmailAddress(user.getEmailId());
        authorizedUser.setMobilePhoneNumber(user.getMobile());
        authorizedUser.setAccessToken(userAuthToken.getAccessToken());
        return authorizedUser;
    }
}
