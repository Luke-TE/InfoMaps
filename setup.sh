docker build -t pywiki ./wikipedia
docker run -d -p 8080:8080 pywiki
