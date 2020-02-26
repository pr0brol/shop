package springmarket.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ShopApplication {


	// План на курс:
	// 1. Добавить платежную систему (PayPal)
	// 2. Авторизация через соцсети
	// 4. Отправка уведомлений пользователю, на сайте, или на почту
	// 5. Промокоды
	// 6. Логирование
	// 7. Профиль/редактирование профиля
	// 8. Отдельная админка
	// 9. Картинки для товаров
	// 10. HTTPS
	// 11. История просмотров товара (куки)
	// 12. Статистика для владельца
	// 13. История действий на сайте
	// 14. Смс сервис
	// 15. Восстановление пароля
	// 16. Формирование PDF для заказа

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
