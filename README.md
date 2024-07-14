# Product cards app
## Задание
Создайте веб Java-приложение, которое позволяет работать с карточками товара в интернет-магазине.  
Создайте БД с информацией о товарах. Заполните начальными тестовыми данными. В базе данных должно содержаться следующая информация о товарах:  
- Название товара  
- Бренд  
- Mодель  
- Количество доступных единиц  
- Вес  
- Рейтинг  
- Категория товара  
- Описание  
- Цвет  
- Цена  
- Название фото  
- Фото  
- Особенности (список)  
## В приложении должно быть:  
CRUD для Продуктов. URl: …/api/products (см. Пример Json запроса)  
При создании либо изменении в БД должно изменяться поле «Описание», оно должно содержать краткое описание, касающееся товара со всех основных полей БД, например «Прекрасные наушники Acme, цвет: черный. Активное шумоподавление, Складной дизайн, Встроенный микрофон.»  
Функционал по обработке 1 фото (выберите фото с фоном на ваш выбор, исходник сохраните, в resources). При загрузке фото должен происходить запрос к API(https://www.remove.bg/api) с исходной фотографией для удаления фона с изображения. Измененное фото cохранить (место на выбор исполнителя задания), добавить обработанное фото в соответствующее поле в карточку товара.  

## Пример Json запроса:  
{"product": {  
    "name": "Прекрасные наушники",  
    "description": "Стерео наушники с шумоподавлением",  
    "price": 49.99,  
    "color": "черный",  
    "brand": "Acme",  
    "category": "Электроника",  
    "availability": true,  
    "rating": 4.5,  
    "image_url": "https://example.com/images/headphones.jpg",  
    "weight": "200 г",  
    "warranty": "2 года",  
    "special_features": [  
      "Активное шумоподавление",  
      "Складной дизайн",  
      "Встроенный микрофон"]}}  
## Технические требования:  
Использованные исходные фото приложите в проект в /resources  
Gradle  
Spring Boot 3.2.5  
Java 17+  
Packaging Jar  
БД H2 или SQLite  
Добавить логи  
## Доп. Задания (необязательно):  
Запрос для получения карточки товара с наивысшим рейтингом.  
Запрос для получения карточки самого дорогого/дешевого товара.   		
Добавить возможность загружать и хранить несколько фотографий для одного товара.  
## Примечания:
Обработанные фото хранятся в директории /resources/static/photo_without_background. В таблице Product_photo фотографии хранятся как названия файлов. Т.к. было требование возможности запуска приложения сразу после pull, было принято решение хранить фото таким образом, а не в minio, например, чтобы клиентам приложения не приходилось запускать контейнерс minio.  
Для удобства получения обработанных фото или добавления фото к товару добавлен file controller.
В корневой директории прилагается коллекция postman для удобства тестирования API. Также swagger документация доступна по адресу http://localhost:8082/swagger-ui/index.html .

