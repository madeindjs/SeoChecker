# SeoChecker

Crawl your website using [Crawler4j][crwaler4j] & check somes SEO rules. 

Currently this check:

- Pages
    - has **unique**
        - title
        - meta descriptionoptimized meta description
    - has keywords
    - all `<img>` have `alt` attribute
    - has optimized title (under 71 chars)
    - has optimized meta description [(between 230 & 320 chars)](https://moz.com/blog/googles-longer-snippets)


## Run

Clone this repository 

    $ git clone https://github.com/madeindjs/SeoChecker.git
    $ cd SeoChecker

Compile using [Maven](http://maven.apache.org/) .

    $  mvn clean compile assembly:single

Now you can run the *jar* file generated with specified domain

    $ cd target
    $ java -jar SeoChecker-1.0-SNAPSHOT-jar-with-dependencies.jar http://rousseau-alexandre.fr

And the result would be

    http://rousseau-alexandre.fr/portfolio
            - Description should not be lower than 230 chars
            - <h1> tag not found
            - <meta name="keywords"> tag not found

    http://rousseau-alexandre.fr/tutorial/2017/11/28/rust.html
            - Description should not be lower than 230 chars
            - <meta name="keywords"> tag not found

## Purpose

Respect all of theses [Google Search Engine Optimization guide][google-guide]
Check all of these SEO rules:

- Website
    - use sitemap file
- Pages
    - are reachable
    
    - Response time
        - good: under 400 ms
        - not bad: between 400 ms & 800 ms
        - bad: upper to 800 ms

[crwaler4j]: https://github.com/yasserg/crawler4j
[google-guide]: http://static.googleusercontent.com/media/www.google.com/fr//webmasters/docs/search-engine-optimization-starter-guide.pdf