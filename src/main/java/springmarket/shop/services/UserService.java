package springmarket.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springmarket.shop.entities.*;
import springmarket.shop.repositories.RoleRepository;
import springmarket.shop.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByPhone(String phone) {
        return userRepository.findOneByPhone(phone);
    }

    public User findByToken(String token) {return userRepository.findByToken(token);}

    public User save(User user) {return userRepository.save(user);}

    public boolean addUser(User user) {
        if (!userRepository.existsByPhone(user.getPhone())) {
            if (user.getPassword() == null) {
                userRepository.save(user);
                return true;
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return true;
            }
        } else if (userRepository.existsByPhone(user.getPhone())) {
            User updateUser = userRepository.findOneByPhone(user.getPhone());
            if (updateUser.getPassword() == null) {
                userRepository.delete(user);
                updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
                updateUser.setFirstName(user.getFirstName());
                updateUser.setLastName(user.getLastName());
                updateUser.setEmail(user.getEmail());
                updateUser.setRoles(user.getRoles());
                userRepository.save(updateUser);
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByPhone(userName);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean isUserExist(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
