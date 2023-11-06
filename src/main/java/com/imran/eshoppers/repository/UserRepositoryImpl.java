package eshoppers.repository;

import com.imran.eshoppers.domain.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UserRepositoryImpl implements UserRepository{

    private final static Set<User> USERS
            = new CopyOnWriteArraySet<>();

    @Override
    public void save(User user) {
        USERS.add(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return USERS
                .stream()
                .filter(
                        user -> Objects.equals(
                                user.getUsername(), username)
                )
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return USERS.
                stream().
                filter(
                        user -> Objects.equals(
                                user.getEmail(), email)
                )
                .findFirst();
    }
}
