# ---------- Stage 1: compile the Java sources ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY src ./src
RUN mkdir out \
    && javac -encoding UTF-8 -d out $(find src/main/java -name "*.java") \
    && cp -r src/main/resources/* out/

# ---------- Stage 2: lightweight image that only runs the app ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/out ./out
ENV PORT=8080
EXPOSE 8080
CMD ["java", "-cp", "out", "com.mopamopa.studio.Main"]
