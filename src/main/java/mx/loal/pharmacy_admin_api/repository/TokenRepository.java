package mx.loal.pharmacy_admin_api.repository;

import mx.loal.pharmacy_admin_api.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
        SELECT t
        FROM Token t INNER JOIN user u
        ON t.user.id = u.id
        WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
        """)
    List<Token> findAllValidTokenByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

}
