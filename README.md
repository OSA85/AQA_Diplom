В файле README.md должна быть описана процедура запуска авто-тестов (если для запуска необходимо заранее установить, настроить, запустить какое-то ПО - это тоже должно быть описано).
# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документация по проекту
#### [Задание для работы](https://github.com/netology-code/qa-diploma.git)
#### [План работы](https://github.com/OSA85/AQA_Diplom/blob/master/Doc/Plan.md)
#### [Отчёт о проведённом тестировании](https://github.com/OSA85/AQA_Diplom/blob/master/Doc/Report.md)
#### [Отчёт о проведённой автоматизации](https://github.com/OSA85/AQA_Diplom/blob/master/Doc/Summary.md)

## Подготовка к тестированию:
1. Если не утановлен, необходимо скачать и установить с оф.сайта [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows), следуя инструкциям.
2. Если не утановлен, необходимо скачать и установить с оф.сайта [Docker Desktop](https://www.docker.com/products/docker-desktop), следуя инструкциям.
3. Если не утановлен, необходимо скачать и установить с оф.сайта [Git Bash](https://gitforwindows.org/), следуя инструкциям.
4. Склонировать [репозиторий](https://github.com/OSA85/AQA_Diplom.git) к себе.


## Запускаем SUT и теты для каждой базы отдельно:
### Для работы с базой данных MySQL
Проект преднастроен под работу с базой данных MySQL.
#### Способ запуска №1
1. Открыть склонированный проект в Intellij IDEA.
2. Вводим в терминале IDEA команду `docker-compose up -d`, для запуска контейнеров, дожидаемся запуска контейнеров.
3. Проверить запуск контейнеров командой `docker ps` в терминале IDEA:
 ![Проверка запуска контейнеров](https://user-images.githubusercontent.com/91024430/159658426-1e97a8f1-4375-468b-bc63-f7b868577b6b.jpg)
4. Вводим в терминале IDEA команду `java -jar .\artifacts\aqa-shop.jar` для запуска приложения.
5. Для запуска авто-тестов в Terminal Intellij IDEA открыть новую сессию и ввести команду: `./gradlew clean test allureReport -Dheadless=true`
7. Для просмотра отчета Allure в терминале ввести команду: `./gradlew allureServe`.

#### Способ запуска №2
1. Открыть склонированный проект в Intellij IDEA.
2. Вводим в терминале IDEA команду `docker-compose up -d`, для запуска контейнеров, дожидаемся запуска контейнеров.
3. Вводим в терминале IDEA команду `./gradlew clean test allureReport -Dheadless=true java -Dspring.datasource.url=jdbc:mysql://localhost:3306/mysql -jar .\artifacts\aqa-shop.jar`, для запуска БД MySQL и запуска приложения.
4. Для просмотра отчета Allure в терминале ввести команду: `./gradlew allureServe`.

### Для работы с базой данных PostgreSQL
В находящемся в проекте файле `application.properties` закомментировать строку ниже "#для MySQL" и снять комментарий на строке ниже "#для PostgreSQL", выглядеть будет так:
```
  #для MySQL  
  #spring.datasource.url=jdbc:mysql://localhost:3306/mysql  
  #для PostgreSQL  
  spring.datasource.url=jdbc:postgres://localhost:5432/postgres  
  spring.datasource.username=app  
  spring.datasource.password=pass
```
  
#### Способ запуска №1
1. Открыть склонированный проект в Intellij IDEA.
2. Вводим в терминале IDEA команду `docker-compose up -d`, для запуска контейнеров, дожидаемся запуска контейнеров.
3. Проверить запуск контейнеров командой `docker ps` в терминале IDEA:
 ![Проверка запуска контейнеров](https://user-images.githubusercontent.com/91024430/159658426-1e97a8f1-4375-468b-bc63-f7b868577b6b.jpg)
4. Вводим в терминале IDEA команду `java -jar .\artifacts\aqa-shop.jar` для запуска приложения.
5. Для запуска авто-тестов в Terminal Intellij IDEA открыть новую сессию и ввести команду: `./gradlew clean test allureReport -Dheadless=true`
7. Для просмотра отчета Allure в терминале ввести команду: `./gradlew allureServe`.

#### Способ запуска №2
1. Открыть склонированный проект в Intellij IDEA.
2. Вводим в терминале IDEA команду `docker-compose up -d`, для запуска контейнеров, дожидаемся запуска контейнеров.
3. Вводим в терминале IDEA команду `./gradlew clean test allureReport -Dheadless=true java -Dspring.datasource.url=jdbc:postgres://localhost:3306/postgres -jar .\artifacts\aqa-shop.jar`, для запуска БД PostgreSQL и запуска приложения.
4. Для просмотра отчета Allure в терминале ввести команду: `./gradlew allureServe`.


### Приложение можно открыть на [странице](http://localhost:8080).

### Завершения работы Sut
Для завершения работы SUT, необходимо в терминале, где был запущен SUT, ввести команду: 
#### `Ctrl+C`

### Остановка и удаление контейнера
Для остановки работы контейнеров "Docker-Compose", необходимо ввести в терминал следующую команду: 
#### `docker-compose down`
