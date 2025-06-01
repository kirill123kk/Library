FROM openjdk:17

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR-файл в контейнер
COPY target/Library-0.0.1-SNAPSHOT.jar myapp.jar

# Указываем команду для запуска
CMD ["java", "-jar", "myapp.jar"]