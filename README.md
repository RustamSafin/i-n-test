### Тестовое задание "Гаишник"

Разработать REST-сервис, выдающий автомобильные номера

### Требования к API

Контекст приложения: `/number`

REST-сервис должен реализовывать два GET-метода: `random` и `next`

Правильные примеры вызовов:

> Запрос: `GET http://localhost:8080/number/random`\
> Ответ: "C399BA 116 RUS"

> Запрос: `GET http://localhost:8080/number/next`\
> Ответ: "C400BA 116 RUS"

### Запуск


1. Изменить application.properties
1. `mvn package`
1. `mvn install`
1. перейти в `target`
1. `java -jar number-generator-0.0.1-SNAPSHOT.jar`
 