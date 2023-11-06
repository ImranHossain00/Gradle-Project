package eshoppers.repository;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.User;

import java.util.Optional;

public interface CartRepository {

    Optional<Cart> findByUser(User curUser);

    Cart save(Cart cart);

    Cart update(Cart cart);
}
