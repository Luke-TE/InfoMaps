sudo docker build -t pywiki ./wikipedia
sudo docker run -d -p 8080:8080 pywiki
