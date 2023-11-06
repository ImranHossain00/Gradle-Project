package eshoppers.service;

import com.imran.eshoppers.domain.Cart;
import com.imran.eshoppers.domain.CartItem;
import com.imran.eshoppers.domain.Product;
import com.imran.eshoppers.domain.User;
import com.imran.eshoppers.exception.CartItemNotFoundException;
import com.imran.eshoppers.exception.ProductNotFoundException;
import com.imran.eshoppers.repository.CartItemRepository;
import com.imran.eshoppers.repository.CartRepository;
import com.imran.eshoppers.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCartByUser(User curUser) {
        Optional<Cart> optionalCart
                = cartRepository.findByUser(curUser);

        return optionalCart.orElseGet(() -> createNewCart(curUser));
    }

    @Override
    public void addProductToCart(String productId,
                                 Cart cart)
            throws ProductNotFoundException {

        Product product = findProduct(productId);
        addProductToCart(product, cart);
        updateCart(cart);
    }

    private void addProductToCart(Product product, Cart cart) {
        var cartItemOptional
                = findSimilarProductInCart(cart, product);

        var cartItem
                = cartItemOptional
                .map(this::increaseQuantityByOne)
                .orElseGet(() -> createNewShoppingCartItem(product));
        cart.getCartItems().add(cartItem);
    }

    private CartItem createNewShoppingCartItem(Product product) {
        var cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getPrice());

        return cartItemRepository.save(cartItem);
    }

    private CartItem increaseQuantityByOne(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        BigDecimal totalPrice
                = cartItem
                .getProduct()
                .getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        cartItem.setPrice(totalPrice);

        return cartItemRepository.update(cartItem);
    }

    @Override
    public void removeProductToCart(String productId,
                                    Cart cart)
            throws ProductNotFoundException, CartItemNotFoundException {

        Product product = findProduct(productId);

        removeProductToCart(product, cart);
        updateCart(cart);
    }

    private Product findProduct(String productId)
            throws ProductNotFoundException {

        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Long id = parseProductId(productId);

        return productRepository.findById(id).
                orElseThrow(
                        () -> new ProductNotFoundException(
                                "product not found by id: " + id)
                );
    }

    private void updateCart(Cart cart) {
        Integer totalItem = getTotalItem(cart);
        BigDecimal totalPrice = calculateTotalPrice(cart);

        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);

        cartRepository.update(cart);
    }

    private void removeProductToCart(Product productToRemove,
                                     Cart cart)
            throws CartItemNotFoundException {

        var itemOptional
                = cart.getCartItems()
                .stream()
                .filter(cartItem ->
                        cartItem.getProduct().equals(productToRemove))
                .findAny();

        var cartItem
                = itemOptional
                .orElseThrow(() ->
                 new CartItemNotFoundException(
                         "Cart not found by product: " + productToRemove)
                );

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getPrice().subtract(
                    productToRemove.getPrice())
            );
            cart.getCartItems().add(cartItem);
            cartItemRepository.update(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.remove(cartItem);
        }
    }
    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer getTotalItem(Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(CartItem::getQuantity)
                .reduce(0, Integer::sum);
    }



    private Optional<CartItem> findSimilarProductInCart(Cart cart, Product product) {
        return cart.
                getCartItems()
                .stream()
                .filter(cartItem -> cartItem
                        .getProduct()
                        .equals(product))
                .findFirst();
    }

    private Long parseProductId(String productId) {
        try {
            return Long.parseLong(productId);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Product id must be a number", ex);
        }
    }

    private Cart createNewCart(User curUser) {
        Cart cart = new Cart();
        cart.setUser(curUser);

        return cartRepository.save(cart);
    }

}
