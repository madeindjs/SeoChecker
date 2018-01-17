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

### More things to check from [Devenir un expert en SEO en 30min][medium-seo]

- Toutes vos images doivent être compressées au maximum. Fini les images en 3000*2000px (1000px est largement suffisant, la qualité est tout aussi bonne). Un outil pour compresser vos images.
- Toutes les images de la pages doivent être nommées avec les mots clés de la page. De plus, la balise “alt” de l’image, doit aussi contenir les mots clés
- Le titre de votre page (<h1>) doit contenir votre mot clé
- Le <title> de votre page doit contenir le mot clé. Sous wordpress, c’est fait automatiquement avec votre titre.
- Le <title> doit contenir au maximum 65 caractères
- Les sous titres <h2> <h3> etc. doivent aussi contenir des mots clés, des synonymes ou des termes proches.
- Votre meta description contient votre mot clé.
- Votre mot clé apparait dans l’url. 
- Pour reprendre les exemples monblog.com/sac-main-annees-80 ou monsite.fr/blog/etats-lieux-voiture
- Votre page/article doit contenir au minimum 300 mots. Si vous tenez un blog, préférez des articles riches (+800 mots).
- Votre mot clé doit représenter entre 1 à 3% de votre page. Si votre article fait 1000 mots, vous pouvez utiliser jusque 30 fois le mot “sac à main”. (Ceci est approximatif, mais Google n’accepte plus les pages “trop optimisées” et trop denses en mots clés).
- Le contenu de votre page est unique. Aucun copier coller d’un autre site.
- La page contient des liens vers d’autres articles et vers au moins un site externe.

[crwaler4j]: https://github.com/yasserg/crawler4j
[google-guide]: http://static.googleusercontent.com/media/www.google.com/fr//webmasters/docs/search-engine-optimization-starter-guide.pdf
[medium-seo]: https://medium.com/@barthbamasta/devenir-un-expert-en-seo-en-30min-7cd6d1e23de
