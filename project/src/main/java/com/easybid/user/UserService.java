package com.easybid.user;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }
    
    public Page<User> getUserList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User target_user = null;
        
        try {
            target_user = userRepository.findById(id).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user != null) {
            if (userDetails.getUserPw() != null && userDetails.getUserPw() != "") {
                target_user.setUserPw(userDetails.getUserPw());
            }
            if (userDetails.getUserEmail() != null && userDetails.getUserEmail() != "") {
                target_user.setUserEmail(userDetails.getUserEmail());
            }
            if (userDetails.getUserName() != null && userDetails.getUserName() != "") {
                target_user.setUserName(userDetails.getUserName());
            }
            if (userDetails.getUserPhone() != null && userDetails.getUserPhone() != "") {
                target_user.setUserPhone(userDetails.getUserPhone());
            }

            userRepository.save(target_user);
        }

        return target_user;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean updateUserProfileImage(Long id, String string) {
        boolean result = false;
        User target_user = null;
        
        try {
            target_user = userRepository.findById(id).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user != null) {
            target_user.setUserImage(string);
            userRepository.save(target_user);
            result = true;
        }

        return result;
    }

    /**
     * 로그인할 때 이메일과 비밀번호가 일치하는지 확인
     * @param user_entity
     * @return 성공/실패(boolean)
     */
    public boolean login_check(User login_user) {
        boolean result = false;

        User user = null;

        try {
            user = userRepository.findByUserEmail(login_user.getUserEmail()).orElseThrow(null);
        } catch (Exception e) {
            //e.printStackTrace(); //NullPointerException
        }

        if(user != null) {
            if(login_user.getUserEmail().equals(user.getUserEmail())) {
                if (login_user.getUserPw().equals(user.getUserPw())) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * 이메일 중복 체크
     * @param userEmail
     * @return
     */
    public boolean email_check(String userEmail) {
        boolean result = false;

        if(userRepository.existsByUserEmail(userEmail)) {
            result = true;
        }

        return result;
    }

    /**
     * 회원가입 메소드
     * @param user_entity
     * @return
     */
    public boolean register(User user) {
        boolean result = false;
        User target_user = null;
        
        try {
            target_user = userRepository.findByUserEmail(user.getUserEmail()).orElseThrow(null);
        } catch (Exception e) {
            // null
        }

        if(target_user == null) {
            target_user = new User();
            target_user.setUserEmail(user.getUserEmail());
            target_user.setUserPw(user.getUserPw());
            target_user.setUserName(user.getUserName());
            target_user.setUserPhone(user.getUserPhone());

            userRepository.save(target_user);

            result = true;
        }

        return result;
    }
    
    /**
     * 사용자 이름으로 이메일 검색
     * @param userName
     * @return userEmail
     */
    public String find_email(String userName) {
        User user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + userName));
    
        return user.getUserEmail();
    }

    /**
     * 사용자 이름과 이메일로 비밀번호 검색
     * @param userName,userEmail
     * @return userPw
     */
    public String find_pw(String userName, String userEmail) {
        User user = userRepository.findByUserNameAndUserEmail(userName, userEmail).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + userName));

        return user.getUserPw();
    }
}
