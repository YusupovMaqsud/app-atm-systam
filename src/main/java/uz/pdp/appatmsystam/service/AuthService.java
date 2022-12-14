package uz.pdp.appatmsystam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appatmsystam.entity.User;
import uz.pdp.appatmsystam.enums.RoleEnum;
import uz.pdp.appatmsystam.payload.ApiResponse;
import uz.pdp.appatmsystam.payload.LoginDto;
import uz.pdp.appatmsystam.payload.RegisterDto;
import uz.pdp.appatmsystam.repository.RoleRepository;
import uz.pdp.appatmsystam.repository.UserRepository;
import uz.pdp.appatmsystam.security.JwtProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResponse registerUser(RegisterDto registerDto) {

        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail) {
            return new ApiResponse("Bunday email allaqachon mavjud", false);
        }
        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER));//faqat user role kerak
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);

        //EMAILGA YUBORISH METHODINI CHAQIRYAPMIZ
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Muffaqiyatli ro'yxatdan o'tdingiz,Accaountni aktivlashtirish uchun emailingizni tasdiqlang", true);

    }





    public boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com"); //kimdan kelganligi
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Accauntni tasdiqlash"); //tekst
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode="
                    + emailCode + "+&email=" + sendingEmail + "'>Tasdiqlang</a>");

            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, Integer.valueOf(emailCode));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Accaount tasdiqlangan", true);

        }
        return new ApiResponse("Accaount allaqachon tasdiqlangan", false);
    }





    public ApiResponse login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                    loginDto.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), Collections.singleton(user.getRole()));
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Parol yoki login xato", false);

        }

    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(username + "topilmadi");
    }


}
