package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dto.UserDto;
import org.example.backend.model.User;
import org.example.backend.model.UserDetailsImpl;
import org.example.backend.model.UserMapper;
import org.example.backend.repository.FriendRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", email)
        ));

        return UserDetailsImpl.build(user);
    }


    public List<UserDto> getFriendsList(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return friendRepository.findFriendsByUserId(user.getId())
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDto> getUsersList(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userRepository.findAllUsersExceptCurrent(user.getId())
                .stream()
                .map(userMapper::toDto)
                .map(userDto -> userDto
                        .setIsFriend(friendRepository.areFriends(user.getId(), userDto.getId())))
                .toList();
    }

    public void addFriend(String name, Long friendId) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        friendRepository.addFriend(user.getId(), friendId);
    }
}
