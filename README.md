# SeoChecker

Crawl your website using [Crawler4j][crwaler4j] & check somes SEO rules. 

Currently this check:

- Pages
    - has **unique**
        - title
        - meta description
    - has [optimized meta description](https://moz.com/blog/googles-longer-snippets)


## Run

Clone this repository 

    $ git clone https://github.com/madeindjs/SeoChecker.git
    $ cd SeoChecker

Compile using [Maven](http://maven.apache.org/) .

    $  mvn clean compile assembly:single

Now you can run the *jar* file generated

    $ cd target
    $ java -jar SeoChecker-1.0-SNAPSHOT-jar-with-dependencies.jar

## Purpose

Respect all of theses [Google Search Engine Optimization guide][google-guide]
Check all of these SEO rules:

- Website
    - use sitemap file
- Pages
    - are reachable
    - has keywords
    - all `<img>` have `alt` attribute
    - Response time
        - good: under 400 ms
        - not bad: between 400 ms & 800 ms
        - bad: upper to 800 ms

[crwaler4j]: https://github.com/yasserg/crawler4j
[google-guide]: http://static.googleusercontent.com/media/www.google.com/fr//webmasters/docs/search-engine-optimization-starter-guide.pdf