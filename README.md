В файле README.md должна быть описана процедура запуска авто-тестов (если для запуска необходимо заранее установить, настроить, запустить какое-то ПО - это тоже должно быть описано).

Подготовка к тестированию:
Если не утановлен, необходимо скачать и установить с оф.сайта [Docker Desktop](https://www.docker.com/products/docker-desktop), следуя инструкциям.

Запускаем SUT:
1. Вводим в терминале IDEA команду `docker-compose up -d`, для запуска контейнеров, дожидаемся запуска контейнеров.
2. Вводим в терминале IDEA команду `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/mysql -jar .\artifacts\aqa-shop.jar`, для запуска БД MySQL и запуска приложения.
3. Вводим в терминале IDEA команду `java -Dspring.datasource.url=jdbc:postgres://localhost:3306/postgres -jar .\artifacts\aqa-shop.jar`, для запуска БД PostgreSQL и запуска приложения.
4. Приложение можно открыть на [странице](http://localhost:8080).

