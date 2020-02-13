## InfoMaps
Automated geographical data extractor and infographic generator.

Originally an entry for IC Hack 2020.
Warning: This is not currently in a usable state.

### What it does
* Gets geographical data from multiple online sources of data.
* Displays the data on an interactive map UI.

### How we built it
We separated the team into frontend and backend members. Two of our members worked on frontend where they used React and the DataMap package to produce a beautiful UI for web application.
The other three members worked on scraping data from the internet to then convey to the frontend. We created three separate nodes for scraping the web: A Python node for scraping wikipedia, a Kotlin node for scraping the CIA World Factbook and a Java node for using natural language processing on general websites. This lead to a microservice-like design.

### Challenges I ran into
* We tried moving from a microservice design to a monolithic design. However, a large amount of code ended up conflicting.
* We began the frontend by using Java. However, this was clunky and was not fully compatible with DataMaps package.

### Accomplishments that I'm proud of
* The UI looks beautiful.
* This project has potential to be useful outside of winning a hackathon.

### What I learned
* Using microservices with Docker.
* Node can be faster to programme and more reliable than Java, depending on your purposes.

### Built With

* Java, Kotlin and Python - Backend and web scraping
* [Docker](https://www.docker.com/) - Container deployment
* [React](https://www.docker.com/) - User interface
* [Datamaps](https://github.com/markmarkoh/datamaps) - Infographic generation
* [Sanic](https://github.com/huge-success/sanic) - Asynchronous request handling
* [Gradle](https://gradle.org/) - Building Java and Kotlin

### Authors

* **Luke Texon** - *Initial work* - [Luke-TE](https://github.com/Luke-TE)
* **Bianca Teodora Catea** - *Initial work* - [BiancaTC](https://github.com/BiancaTC)
* **Dylan Hillier** - *Initial work* - [Dylan Hillier](https://github.com/DylanHiller)
* **Lazar Lukic** - *Initial work* - [ll8618](https://github.com/ll8618)
* **David Valaczkai** - *Initial work* - [OerllydSaethwr](https://github.com/OerllydSaethwr)

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

### Acknowledgments

* GitHub User PurpleBooth for the README template
