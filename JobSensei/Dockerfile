# Используем maven:3.8.1-openjdk-17 для сборки и запуска приложения
FROM maven:3.8.1-openjdk-17
WORKDIR /app
# Сначала копируем только pom.xml и загружаем зависимости
COPY pom.xml /app
RUN mvn dependency:resolve
# Теперь копируем остальной код и собираем приложение
COPY . /app
CMD ["mvn", "spring-boot:run"]
