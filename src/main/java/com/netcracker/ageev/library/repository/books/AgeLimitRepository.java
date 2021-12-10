package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.AgeLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//TODO: - Я не понимаю как работает репозиторий, разберусь как дойду до api и посмотрю на запросы
//      - Оставлю это только в этом интерфейсе, надеюсь разберусь =)
//      - upd:
//TODO: - отключил весь spring security для теста api, потом верну как было
//      - какую то ерунду в виде json уже получил =)
//        upd: 7.12.yyyy
//      - ничего не работает, но я разбираюсь =)
@Repository
public interface AgeLimitRepository extends JpaRepository<AgeLimit,Long> {

    Optional<AgeLimit> findAgeLimitById(Integer id);

    List<AgeLimit> findAllByOrderById();
}
