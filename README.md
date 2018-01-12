# SeoChecker

Crawl your website using [Crawler4j][crwaler4j] & check somes SEO rules. 

![Screenshot](https://raw.githubusercontent.com/madeindjs/SeoChecker/master/screenshot.png)

Currently this check:

- Pages
    - are reachable
    - has **unique**
        - title
        - meta descriptionoptimized meta description
    - has keywords
    - all `<img>` have `alt` attribute
    - has optimized title (under 71 chars)
    - has optimized meta description [(between 230 & 320 chars)](https://moz.com/blog/googles-longer-snippets)

At the end of the Crawl, you'll be able to view result or export to _.txt_ or _*.html_.

## Run

Clone this repository 

    $ git clone https://github.com/madeindjs/SeoChecker.git
    $ cd SeoChecker

Compile using [Maven](http://maven.apache.org/) .

    $  mvn clean compile assembly:single

Now you can run the *jar* file generated with specified domain

    $ cd target
    $ java -jar SeoChecker-1.0-SNAPSHOT-jar-with-dependencies.jar

## Purpose

Respect all of theses [Google Search Engine Optimization guide][google-guide]

## Todo

- [ ] Check if website use a Sitemap
- [ ] Check response time _(good = <400ms, correct = 400~800ms, bad = >800 ms)_
- [ ] Add more specifcation to new crawler (polite time delay, limit deepth, limit pages, filters, etc..)

All merge request are welcomes!

[crwaler4j]: https://github.com/yasserg/crawler4j
[google-guide]: http://static.googleusercontent.com/media/www.google.com/fr//webmasters/docs/search-engine-optimization-starter-guide.pdf
