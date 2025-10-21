package com.example.rentalapp.repository;

import com.example.rentalapp.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
/* Наследуется от JpaRepository, где уже реализованы
1) save() - вставка/обновление
2) findAll() - чтение всех записей
3) findById() - поиск по ID
4) deleteById() - удаление по ID
 */
// Spring Boot автоматически создаёт реализацию этого интерфейса.
// Когда контроллер вызывает rentalRepository.findAll(), под капотом выполняется SQL-запрос SELECT * FROM rentals.